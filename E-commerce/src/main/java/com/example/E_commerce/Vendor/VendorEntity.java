//package com.example.E_commerce.Vendor;
//
//import com.example.E_commerce.User.UserEntity;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name="vendor")
//@Data
//@NoArgsConstructor
//public class VendorEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    private String imageUrl;
//    private String address;
//    private String gender;
//    @ManyToOne
//    @JoinColumn(name="user_id")
//    private UserEntity user;
//    private int gstNo;
//
//}
