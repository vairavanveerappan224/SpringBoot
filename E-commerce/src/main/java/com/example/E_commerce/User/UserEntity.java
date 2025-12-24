package com.example.E_commerce.User;

//import com.example.E_commerce.Customer.CustomerEntity;
//import com.example.E_commerce.Vendor.VendorEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="users")
@Data

@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Cannot be null")
    @NotEmpty(message = "Cannot be Empty")
    private String name;
    @Pattern(
            regexp="^[A-Za-z0-9]+@gmail\\.com$",
            message="email must be gmail.com"
    )
    private String email;
    @Size(min = 6, max = 255)
    private String password;
    private String phoneNo;
    private Integer userType;
    private String imageFile;
    private String address;
    private String gender;
//    @Min(value=3,message = "Gst no must be minimum of 3")
//    @Max(value=6,message = "Gst no must be within 6")
    private Integer gstNo;

//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    private List<VendorEntity> vendor;
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//    private List<CustomerEntity> customer;


}
