package com.example.sales_management.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sales_management.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByRole(String role);
}
