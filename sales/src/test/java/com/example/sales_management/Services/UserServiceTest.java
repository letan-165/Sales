package com.example.sales_management.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.sales_management.Models.User;
import com.example.sales_management.Repository.UserRepository;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        User user1 = new User();
        user1.setUserID("U001");
        User user2 = new User();
        user2.setUserID("U002");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        List<User> users = userService.findAll();
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void findById() {
        String userID = "U001";
        User user = new User();
        user.setUserID(userID);
        when(userRepository.findById(userID)).thenReturn(Optional.of(user));
        User result = userService.findById(userID);
        assertNotNull(result);
        assertEquals(userID, result.getUserID());
    }

    @Test
    void save() {
        User user = new User();
        user.setUserID("U001");
        user.setUserName("John");
        when(userRepository.save(user)).thenReturn(user);
        boolean result = userService.save(user);
        assertTrue(result);
    }

    @Test
    void deleteById() {
        String userID = "U001";
        doNothing().when(userRepository).deleteById(userID);
        boolean result = userService.deleteById(userID);
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(userID);
    }

    @Test
    void findByRole() {
        String role = "ADMIN";
        User user1 = new User();
        user1.setRole(role);
        User user2 = new User();
        user2.setRole(role);
        when(userRepository.findByRole(role)).thenReturn(Arrays.asList(user1, user2));
        List<User> users = userService.findByRole(role);
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void findAllById() {
        List<String> ids = Arrays.asList("U001", "U002");
        User user1 = new User();
        user1.setUserID("U001");
        User user2 = new User();
        user2.setUserID("U002");
        when(userRepository.findAllById(ids)).thenReturn(Arrays.asList(user1, user2));
        List<User> users = userService.findAllById(ids);
        assertNotNull(users);
        assertEquals(2, users.size());
    }
}
