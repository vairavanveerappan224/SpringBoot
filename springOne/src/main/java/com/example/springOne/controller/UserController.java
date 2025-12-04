package com.example.springOne.controller;

import com.example.springOne.entity.UserEntity;
import com.example.springOne.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Custom load all users
    @GetMapping
    public List<UserEntity> getUsers() {
        return userRepository.loadAllUsers();
    }

    // Add user (still same)
    @PostMapping
    public UserEntity addUser(@RequestBody UserEntity user) {
        return userRepository.save(user);
    }

    // Get by name (CUSTOM)
    @GetMapping("/name/{name}")
    public List<UserEntity> getByName(@PathVariable String name) {
        return userRepository.findByUserName(name);
    }

    // Get by course (CUSTOM)
    @GetMapping("/course/{course}")
    public List<UserEntity> getByCourse(@PathVariable String course) {
        return userRepository.findByCourse(course);
    }

    // Search name containing text
    @GetMapping("/search/{text}")
    public List<UserEntity> searchUsers(@PathVariable String text) {
        return userRepository.searchByName(text);
    }

    // Native SQL query
    @GetMapping("/native/course/{course}")
    public List<UserEntity> nativeByCourse(@PathVariable String course) {
        return userRepository.getUsersByCourseNative(course);
    }

    // Custom GET by ID
    @GetMapping("/get/{id}")
    public Object getUserCustom(@PathVariable Integer id) {
        UserEntity user = userRepository.getUserByIdCustom(id);
        return (user != null) ? user : "User not found!";
    }

    // Custom UPDATE by ID
    @PutMapping("/update/{id}")
    public String updateUserCustom(@PathVariable Integer id, @RequestBody UserEntity newData) {

        int updated = userRepository.updateNameAndCourseById(
                id,
                newData.getName(),
                newData.getCourse()
        );

        return (updated == 1) ? "User updated successfully!" : "User not found!";
    }

    // Custom DELETE by ID
    @DeleteMapping("/delete/{id}")
    public String deleteUserCustom(@PathVariable Integer id) {

        int deleted = userRepository.deleteUserByIdCustom(id);

        return (deleted == 1) ? "User deleted successfully!" : "User not found!";
    }
}
