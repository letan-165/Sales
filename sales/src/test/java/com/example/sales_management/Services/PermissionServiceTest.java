package com.example.sales_management.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.sales_management.Models.Permission;
import com.example.sales_management.Repository.PermissionRepository;

public class PermissionServiceTest {

    @InjectMocks
    private PermissionService PermissionService;

    @Mock
    private PermissionRepository PermissionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPermissions() {
        List<Permission> Permissions = new ArrayList<>();
        Permissions.add(new Permission(1l, "ADMIN",null));
        Permissions.add(new Permission(2l, "USER",null));

        when(PermissionRepository.findAll()).thenReturn(Permissions);

        List<Permission> foundPermissions = PermissionService.findAll();
        assertEquals(2, foundPermissions.size());
        verify(PermissionRepository).findAll();
    }

    @Test
    public void testGetPermissionById() {
        Permission Permission = new Permission(1l, "ADMIN",null);

        when(PermissionRepository.findById(1l)).thenReturn(Optional.of(Permission));

        Permission foundPermission = PermissionService.findById(1l);
        assertNotNull(foundPermission);
        assertEquals("ADMIN", foundPermission.getPermissionName());
        verify(PermissionRepository).findById(1l);
    }

    @Test
    public void testCreatePermission() {
        Permission Permission = new Permission(1l, "ADMIN",null);

        when(PermissionRepository.save(Permission)).thenReturn(Permission);
        Permission createdPermission = PermissionService.save(Permission);

        assertNotNull(createdPermission);
        assertEquals("ADMIN", createdPermission.getPermissionName());
        verify(PermissionRepository).save(Permission);
    }

    @Test
    public void testDeletePermission() {
        doNothing().when(PermissionRepository).deleteById(1l);
        PermissionService.deleteById(1l);
        verify(PermissionRepository).deleteById(1l);
    }
}
