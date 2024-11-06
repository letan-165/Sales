package com.example.sales_management.Services;

import java.text.ParseException;
import java.util.Date;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.sales_management.Models.User;
import com.example.sales_management.Repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public boolean introspect(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return verified && expiryTime.after(new Date());
    }

    public String authenticate( String username,String password){
        var user = userRepository.findById(username)
                .orElse(null);

        boolean authenticated = passwordEncoder.matches(password,
                user.getPassWord());
        if (authenticated){
            var token = generateToken(user);
            return token;
        }
        return "TokenFalse";

    }
    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserID())
                .issuer("letan.com")
                //.issueTime(new Date())
                // .expirationTime(new Date(
                //         Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                // ))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add(user.getRole());
        if (!CollectionUtils.isEmpty(user.getPermissions()))
            user.getPermissions().forEach(permission -> stringJoiner.add(permission.getPermissionID()));

        return stringJoiner.toString();
    }
}

