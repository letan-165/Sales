package com.example.sales_management.Services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.User;
import com.example.sales_management.Repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JavaMailSender mailSender;
    String adminEmail; 

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender, @Value("${spring.mail.username}") String adminEmail) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.adminEmail = adminEmail;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String userID) {
        return userRepository.findById(userID).orElse(null);
    }

    public User getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        return userRepository.findById(name).orElseThrow(null);
    }

    public boolean save(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteById(String userID) {
        try {
            userRepository.deleteById(userID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }

    public List<User> findAllById(List<String> listID) {
        return userRepository.findAllById(listID);
    }
    public String sendResetCode(String email) {
        List<User> users = userRepository.findByEmail(email);
        if (users.isEmpty()) {
            System.out.println("Không tìm thấy người dùng với email: " + email); 
            return null;
        }
    
        User user = users.get(0);
        String resetCode = generateResetCode();
        user.setResetCode(resetCode);
        userRepository.save(user);
    
        System.out.println("Reset Code cho email " + email + ": " + resetCode);
    
        try {
            sendEmail(email, resetCode);
        } catch (MessagingException e) {
            System.out.println("Lỗi khi gửi email cho: " + email); 
            e.printStackTrace(); 
            return null;
        }
    
        return resetCode;
    }
    

    private String generateResetCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private void sendEmail(String recipientEmail, String resetCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipientEmail);
        helper.setSubject("Mã đặt lại mật khẩu");
        helper.setText("Mã xác thực của bạn: " + resetCode, true);
        helper.setFrom(adminEmail);
        mailSender.send(message);
    }

    public boolean verifyResetCode(String email, String resetCode) {
        List<User> users = userRepository.findByEmail(email); 
        if (users.isEmpty()) {
            return false; 
        }
        User user = users.get(0);
        
        String storedResetCode = user.getResetCode();
        if (storedResetCode == null) {
            return false; 
        }
        boolean isMatch = resetCode.trim().equals(storedResetCode.trim());
        return isMatch;
    }
    
    public boolean updatePassword(String email, String newPassword) {
        List<User> users = userRepository.findByEmail(email);
        if (users.isEmpty()) return false;

        User user = users.get(0);
        user.setPassWord(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }
}
