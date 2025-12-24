//package com.example.E_commerce.User;
//
//import com.example.E_commerce.User.UserEntity;
//import com.example.E_commerce.Resposes.ApiResponse;
//import com.example.E_commerce.imageGenerator;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//@Controller
//@RequestMapping("/users")
//@RequiredArgsConstructor
//public class UserPageController {
//    private final UserService userService;
//    private final UserRepository userRepo;
//    private final PasswordEncoder passwordEncoder;
//
//    @GetMapping
//    public String usersHome(Authentication authentication) {
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
//    public String dashboard(Model model) {
//        model.addAttribute("page", "dashboard");
//        return "dashboard";
//    }
//
//    @GetMapping("/profile")
//    public String profile() {
//        return "profile";
//    }
//
//    @GetMapping("/list")
//    public String listUsers(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size,
//            Model model) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<UserEntity> usersPage = userService.findAllUsers(pageable);
//
//        model.addAttribute("users", usersPage.getContent());
//        model.addAttribute("totalPages", usersPage.getTotalPages());
//
//        // 👇 THIS is the key
//        model.addAttribute("page", "users");
//
//        return "dashboard";
//    }
//
//
//    @GetMapping("/add")
//    public String showAddUserPage() {
//        return "adduser";
//    }
//
//    @PostMapping("/add")
//    public String add(
//            UserEntity payload,
//            @RequestParam(value = "imageFile", required = false) MultipartFile image
//    ) throws IOException {
//
//        if (image != null && !image.isEmpty()) {
//
//            String uploadDir = "D:/user_image/";
//            File dir = new File(uploadDir);
//            if (!dir.exists()) dir.mkdirs();
//
//            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
//            image.transferTo(new File(uploadDir + fileName));
//
//            payload.setImageFile(fileName);
//        }
//
//        userService.addUser(payload);
//        return "redirect:/users/list";
//    }
//    @GetMapping("/delete")
//    public String showDeletePage(Model model) {
//        model.addAttribute("page", "delete");
//        return "dashboard";
//    }
//
//    @PostMapping("/delete")
//    public String deleteUser(UserEntity user) {
//        userService.deleteUser(user.getId());
//        return "redirect:/users/list";
//    }
//
//    @GetMapping("/update/{id}")
//    public String showUpdateUserPage(@PathVariable int id, Model model) {
//        UserEntity user = userRepo.findById(id).orElse(null);
//        if (user == null) return "redirect:/users/list";
//
//        model.addAttribute("user", user);
//        return "updateuser";
//    }
//
//    @PostMapping("/update")
//    public String updateUser(
//            UserEntity payload,
//            @RequestParam(value = "imageFile", required = false) MultipartFile image
//    ) throws IOException {
//
//        UserEntity user = userRepo.findById(payload.getId()).orElse(null);
//        if (user == null) return "redirect:/users/list";
//
//        // normal fields
//        if (payload.getName() != null) user.setName(payload.getName());
//        if (payload.getEmail() != null) user.setEmail(payload.getEmail());
//        if (payload.getPhoneNo() != null) user.setPhoneNo(payload.getPhoneNo());
//        if (payload.getUserType() != null) user.setUserType(payload.getUserType());
//        if (payload.getAddress() != null) user.setAddress(payload.getAddress());
//        if (payload.getGender() != null) user.setGender(payload.getGender());
//        if (payload.getGstNo() != null) user.setGstNo(payload.getGstNo());
//
//        if (payload.getPassword() != null && !payload.getPassword().isEmpty()) {
//            user.setPassword(passwordEncoder.encode(payload.getPassword()));
//        }
//
//        // image ONLY if provided
//        if (image != null && !image.isEmpty()) {
//
//            String uploadDir = "D:/user_image/";
//            File dir = new File(uploadDir);
//            if (!dir.exists()) dir.mkdirs();
//
//            if (user.getImageFile() != null) {
//                File old = new File(uploadDir + user.getImageFile());
//                if (old.exists()) old.delete();
//            }
//
//            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
//            image.transferTo(new File(uploadDir + fileName));
//            user.setImageFile(fileName);
//        }
//
//        userRepo.save(user);
//        return "redirect:/users/list";
//    }
//
//
//    @GetMapping("/search")
//    public String searchUsers(
//            @RequestParam(required = false) String keyword,
//            Model model
//    ) {
//
//        List<UserEntity> users;
//        users = userService.searchUsers(keyword);
//
//
//        model.addAttribute("users", users);
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("page", "users");
//
//        return "dashboard";
//    }
//
//
//}
//
