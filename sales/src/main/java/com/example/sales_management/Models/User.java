package com.example.sales_management.Models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
 
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_permissions",
        joinColumns = @JoinColumn(name = "userID"),
        inverseJoinColumns = @JoinColumn(name = "permissionID")
    )
    @Builder.Default
    List<Permission> permissions = new ArrayList<>();
}







