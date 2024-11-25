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

import com.example.sales_management.Models.Permission;
import com.example.sales_management.Repository.PermissionRepository;
import com.example.sales_management.Repository.UserRepository;

class PermissionServiceTest {

    @Mock
    PermissionRepository permissionRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    PermissionService permissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Permission permission1 = new Permission();
        permission1.setPermissionID("P001");
        Permission permission2 = new Permission();
        permission2.setPermissionID("P002");
        when(permissionRepository.findAll()).thenReturn(Arrays.asList(permission1, permission2));
        List<Permission> permissions = permissionService.findAll();
        assertNotNull(permissions);
        assertEquals(2, permissions.size());
    }

    @Test
    void findById() {
        String permissionID = "P001";
        Permission permission = new Permission();
        permission.setPermissionID(permissionID);
        when(permissionRepository.findById(permissionID)).thenReturn(Optional.of(permission));
        Permission result = permissionService.findById(permissionID);
        assertNotNull(result);
        assertEquals(permissionID, result.getPermissionID());
    }

    @Test
    void save() {
        Permission permission = new Permission();
        permission.setPermissionID("P001");
        when(permissionRepository.save(permission)).thenReturn(permission);
        Permission result = permissionService.save(permission);
        assertNotNull(result);
        assertEquals("P001", result.getPermissionID());
    }

    @Test
    void deleteById() {
        String permissionID = "P001";
        when(permissionRepository.existsById(permissionID)).thenReturn(true);
        doNothing().when(permissionRepository).deleteById(permissionID);
        boolean result = permissionService.deleteById(permissionID);
        assertTrue(result);
        verify(permissionRepository, times(1)).deleteById(permissionID);
    }

    @Test
    void findAllById() {
        List<String> permissionIds = Arrays.asList("P001", "P002");
        Permission permission1 = new Permission();
        permission1.setPermissionID("P001");
        Permission permission2 = new Permission();
        permission2.setPermissionID("P002");
        when(permissionRepository.findAllById(permissionIds)).thenReturn(Arrays.asList(permission1, permission2));
        List<Permission> permissions = permissionService.findAllById(permissionIds);
        assertNotNull(permissions);
        assertEquals(2, permissions.size());
    }
}
