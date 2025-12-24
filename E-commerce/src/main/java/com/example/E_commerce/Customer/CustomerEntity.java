//package com.example.E_commerce.Customer;
//
//import com.example.E_commerce.User.UserEntity;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name="customer")
//@Data
//@NoArgsConstructor
//public class CustomerEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    private String imageUrl;
//    private String address;
//    private String gender;
//    @ManyToOne
//    @JoinColumn(name="user_id")
//    private UserEntity user;
//}
