package com.example.sales_management.Models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class User {
    @Id
    private String userID;

    private String userName;
    private String passWord;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "user") 
    private List<Report> reports = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "userID"),
        inverseJoinColumns = @JoinColumn(name = "roleID")
    )
    private List<Role> roles = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "orderID")
    private Order order;

}







