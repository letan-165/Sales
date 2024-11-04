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
@Table(name = "permissions")
@Data
@AllArgsConstructor
@NoArgsConstructor 
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Permission {
    @Id
    String permissionID;
    String description;

    @ManyToMany(mappedBy = "permissions",fetch = FetchType.EAGER)
    @Builder.Default
    List<User> users = new ArrayList<>();

    @Override
    public String toString() {
        return "Permission{" +
                "permissionID='" + permissionID + '\'' +
                ", description='" + description + '\'' +
                ", userCount=" + (users != null ? users.size() : 0) +
                '}';
    }
}
