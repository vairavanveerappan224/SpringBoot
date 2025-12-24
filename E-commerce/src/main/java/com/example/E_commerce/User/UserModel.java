package com.example.E_commerce.User;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;


@JsonPropertyOrder({
        "id",
        "name",
        "email",
        "phoneNo",
        "userType",
        "imageUrl",
        "address",
        "gender",
        "gstNo"
})
@Data
@AllArgsConstructor
public class UserModel {

    private String name;
    private String email;
    @Size(min = 6, max = 12)
    private String password;
    private String phoneNo;
    private Integer userType;
    private String imageUrl;
    private String address;
    private String gender;
    private Integer gstNo;

}



