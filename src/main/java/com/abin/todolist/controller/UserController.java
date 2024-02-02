package com.abin.todolist.controller;

import com.abin.todolist.exception.UserNotFoundException;
import com.abin.todolist.model.User;
import com.abin.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }
    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return userRepository.findById(id)
                .map(user -> {
                    user.setTitle(newUser.getTitle());
                    user.setDescription(newUser.getDescription());
                    return userRepository.save(user);
                }).orElseThrow();
    }
    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
        return "user with id "+id+ " has been deleted";
    }
}
