package com.example.sales_management.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sales_management.Models.User;
import com.example.sales_management.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String userID) {
        return userRepository.findById(userID).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(String userID) {
        userRepository.deleteById(userID);
    }

    public void updateUser(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
