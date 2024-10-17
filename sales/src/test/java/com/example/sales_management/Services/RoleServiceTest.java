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

import com.example.sales_management.Models.Role;
import com.example.sales_management.Repository.RoleRepository;

public class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1, "ADMIN"));
        roles.add(new Role(2, "USER"));

        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> foundRoles = roleService.getAllRoles();
        assertEquals(2, foundRoles.size());
        verify(roleRepository).findAll();
    }

    @Test
    public void testGetRoleById() {
        Role role = new Role(1, "ADMIN");

        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        Role foundRole = roleService.getRoleById(1);
        assertNotNull(foundRole);
        assertEquals("ADMIN", foundRole.getRoleName());
        verify(roleRepository).findById(1);
    }

    @Test
    public void testCreateRole() {
        Role role = new Role(1, "ADMIN");

        when(roleRepository.save(role)).thenReturn(role);
        Role createdRole = roleService.createRole(role);

        assertNotNull(createdRole);
        assertEquals("ADMIN", createdRole.getRoleName());
        verify(roleRepository).save(role);
    }

    @Test
    public void testDeleteRole() {
        doNothing().when(roleRepository).deleteById(1);
        roleService.deleteRole(1);
        verify(roleRepository).deleteById(1);
    }
}
