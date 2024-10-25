package com.example.sales_management.Models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor 
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class User {
    @Id
    String userID;

    String userName;
    String passWord;
    String email;
    String phone;
    String role;

    @OneToMany(mappedBy = "user") 
    @Builder.Default
    List<Report> reports = new ArrayList<>();
    @OneToMany(mappedBy = "user") 
    @Builder.Default
    List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "user") 
    @Builder.Default
    List<Discount> discounts = new ArrayList<>();
    @OneToMany(mappedBy = "user") 
    @Builder.Default
    List<Import> imports = new ArrayList<>();
 
    @ManyToMany
    @JoinTable(
        name = "user_permissions",
        joinColumns = @JoinColumn(name = "userID"),
        inverseJoinColumns = @JoinColumn(name = "permissionID")
    )
    @Builder.Default
    List<Permission> permissions = new ArrayList<>();



}







