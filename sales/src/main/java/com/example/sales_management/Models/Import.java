package com.example.sales_management.Models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "imports")
@Data
@AllArgsConstructor
@NoArgsConstructor 
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Import {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long importID;
    private String status;
    private LocalDateTime time;
    private String supplier ;


    @OneToMany(mappedBy = "imports")
    private List<ImportProduct> importProducts;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    
}
