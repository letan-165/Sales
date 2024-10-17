package com.example.sales_management.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.sales_management.Models.User;
import com.example.sales_management.Repository.UserRepository;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserID("user1");
        user.setUserName("John Doe");
        user.setEmail("john.doe@example.com");
    }

    @Test
    public void testFindAll() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.findAll();

        assertEquals(1, users.size());
        assertEquals(user.getUserID(), users.get(0).getUserID());
    }

    @Test
    public void testFindById() {
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));

        User foundUser = userService.findById("user1");

        assertNotNull(foundUser);
        assertEquals(user.getUserID(), foundUser.getUserID());
    }

    @Test
    public void testSave() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        assertEquals(user.getUserID(), savedUser.getUserID());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(userRepository).deleteById("user1");

        userService.deleteById("user1");

        verify(userRepository, times(1)).deleteById("user1");
    }
}

