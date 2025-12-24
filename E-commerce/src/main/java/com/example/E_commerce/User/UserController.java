package com.example.E_commerce.User;

//import com.example.E_commerce.Customer.CustomerEntity;
//import com.example.E_commerce.Customer.CustomerService;

import com.example.E_commerce.Resposes.ApiResponse;
import com.example.E_commerce.Pagination.PaginationInfo;
//import com.example.E_commerce.Vendor.VendorEntity;
//import com.example.E_commerce.Vendor.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;
//    private final VendorService vendorService;
//    private final CustomerService customerService;

    private final UserRepository userRepo;

    @GetMapping
    public ApiResponse<UserPageResponse> findAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "6") int size,
                                                 @RequestParam(defaultValue = "id") String sortBy,
                                                 @RequestParam(defaultValue = "false") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> list = userService.findAllUsers(pageable);
        PaginationInfo pagination = new PaginationInfo(list.getNumber(), list.getSize(), list.getTotalElements(), list.getTotalPages());
        UserPageResponse response = new UserPageResponse(list.getContent(), pagination);

        return new ApiResponse<>(200, true, "User Details", response);

    }

    @GetMapping("/{id}")
    public ApiResponse<UserEntity> findById(@PathVariable int id) {
        UserEntity user = userService.findUserById(id);
        if (user == null) {
            return new ApiResponse<>(404, false, "User not Found", null);
        }
        return new ApiResponse<>(200, true, "User Details", user);
    }

    @PostMapping
    public ApiResponse<List<UserEntity>> add(@Valid @RequestBody UserEntity payload, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        UserEntity user = new UserEntity();
        user.setName(payload.getName());
        user.setEmail(payload.getEmail());
        user.setPassword(payload.getPassword());
        user.setPhoneNo(payload.getPhoneNo());
        user.setUserType(payload.getUserType());
        String uploadDir = "D:/user_image/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = System.currentTimeMillis() + "_" +
                imageFile.getOriginalFilename();

        imageFile.transferTo(new File(uploadDir + fileName)); // real upload

        payload.setImageFile(fileName);
        user.setAddress(payload.getAddress());
        user.setGender(payload.getGender());
        user.setGstNo(payload.getGstNo());
        userService.addUser(user);
//    if(payload.getUserType()==1)
//    {
//        VendorEntity vendor=new VendorEntity();
//        vendor.setimageFile(payload.getimageFile());
//        vendor.setAddress(payload.getAddress());
//        vendor.setGender(payload.getGender());
//        vendor.setGstNo(payload.getGstNo());
//        vendor.setUser(user);
//        vendorService.addVendor(vendor);
//    }
//    if(payload.getUserType()==2)
//    {
//        CustomerEntity customer=new CustomerEntity();
//        customer.setimageFile(payload.getimageFile());
//        customer.setAddress(payload.getAddress());
//        customer.setGender(payload.getGender());
//        customer.setUser(user);
//        customerService.addCustomer(customer);
//    }
        return new ApiResponse<>(200, true, "User", List.of(user));
    }

    @PutMapping("/{id}")
    @Modifying
    public String update(@PathVariable int id, @RequestBody UserEntity payload) {
        return userService.updateUser(id, payload);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        String result = userService.deleteUser(id);
        if (result == "null") {
            return new ApiResponse<>(404, false, "User not Found", null);
        }
        return new ApiResponse<>(200, true, "User Deleted", result);
    }

    //@GetMapping("/vendor")
//    public VendorEntity findUserVendor() {
//     return userService.findVendor();
    @GetMapping("/search")
    public ApiResponse<List<UserEntity>> search(@RequestParam String name) {
        List<UserEntity> list = userRepo.findByNameContainingIgnoreCase(name);
        if (list == null) {
            return new ApiResponse<>(404, false, "Matches Not Found", null);
        }
        return new ApiResponse<>(200, true, "User Details", list);


    }

    @GetMapping("/filter")
    public ApiResponse<List<UserEntity>> filter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String phoneNo,
            @RequestParam(required = false) String address
    ) {

        List<UserEntity> users =
                userService.filterUsers(name, email, gender, phoneNo, address);

        return new ApiResponse<>(200, true, "Filtered users", users);
    }

    @GetMapping("/user-type/{userType}")
    public ApiResponse<List<UserView>> findUserType(@PathVariable int userType) {
        List<UserView> list = userService.findByType(userType);
        if (list == null) {
            return new ApiResponse<>(404, false, "User not Found", null);
        }
        return new ApiResponse<>(200, true, "User Details", list);
    }
    @PostMapping("/excel/upload")
    public String uploadUsers(@RequestParam("file") MultipartFile file) {
        userService.importUsers(file);
        return "Users Excel uploaded successfully";
    }
    // ----------------for web--------------------------
//    @GetMapping("/")
//    public String home(Authentication authentication) {
//
//        boolean isVendor = authentication.getAuthorities()
//                .stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_1"));
//
//        if (isVendor) {
//            return "redirect:/users/dashboard";
//        }
//        return "redirect:/users/profile";
//    }
//
//    @GetMapping("/dashboard")
//    public String dashboard() {
//        return "dashboard"; // view name
//    }
//
//    @GetMapping("/profile")
//    public String profile() {
//        return "profile"; // view name
//    }
}

