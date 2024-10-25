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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "imports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class Import {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer importID;
    private String status;
    private LocalDateTime time;
    private String supplier ;


    @OneToMany(mappedBy = "imports")
    private List<ImportProduct> importProducts;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    
}
