package com.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private float price;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String address;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Employee employee;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Service service;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private User user;
}