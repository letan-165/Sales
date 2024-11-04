    package com.example.sales_management.Services;

    import java.util.List;

    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import com.example.sales_management.Models.User;
    import com.example.sales_management.Repository.UserRepository;

    import lombok.AccessLevel;
    import lombok.RequiredArgsConstructor;
    import lombok.experimental.FieldDefaults;

    @Service
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public class UserService {
        UserRepository userRepository;
        PasswordEncoder passwordEncoder;

        //@PreAuthorize("hasAnyRole('ADMIN','MANAGER)")
        public List<User> findAll() {
            return userRepository.findAll();
        }

        public User findById(String userID) {
            return userRepository.findById(userID).orElse(null);
        }

        public User getMyInfo(){
            var context = SecurityContextHolder.getContext();
            String name = context.getAuthentication().getName();

            return userRepository.findById(name).orElseThrow(null);
        }
        
        public boolean save(User user) {
            try {
                user.setPassWord(passwordEncoder.encode(user.getPassWord()));
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
        
        

    }
