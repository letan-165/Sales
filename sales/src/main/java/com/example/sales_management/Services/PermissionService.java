package com.example.sales_management.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.sales_management.Models.Permission;
import com.example.sales_management.Models.User;
import com.example.sales_management.Repository.PermissionRepository;
import com.example.sales_management.Repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepository;
    UserRepository userRepository;

    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    public Permission findById(String PermissionId) {
        return permissionRepository.findById(PermissionId).orElse(null);
    }
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    public boolean deleteById(String permissionId) {
        if (permissionRepository.existsById(permissionId)) {
            permissionRepository.deleteById(permissionId);
            return true;
        }
        return false;
    }

    public List<Permission> findAllById(List<String> list) {
        return permissionRepository.findAllById(list);
    }
    // Lấy đầu danh sách
    public Permission getFirst() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.isEmpty() ? null : permissions.get(0);
    }
    // Lấy UserID từ permisison
    public List<String> getUserIdsByPermission(Permission permission) {
        return (permission != null) 
            ? permission.getUsers().stream()
                .map(User::getUserID)
                .collect(Collectors.toList())
            : new ArrayList<>();
    }
    // Cập nhật permission
    public void updatePermission(Permission permission, List<String> listID) {
        List<User> updatedUsers = userRepository.findAllById(listID != null ? listID : new ArrayList<>());
        Permission existingPermission = findById(permission.getPermissionID());

        if (existingPermission != null) {
            for (User user : new ArrayList<>(existingPermission.getUsers())) {
                user.getPermissions().remove(existingPermission);
                userRepository.save(user);
            }
            existingPermission.getUsers().clear();
            existingPermission.getUsers().addAll(updatedUsers);
        } else {
            permission.getUsers().addAll(updatedUsers);
        }

        for (User user : updatedUsers) {
            user.getPermissions().add(permission);
            userRepository.save(user);
        }
        permissionRepository.save(permission);
    }

}
