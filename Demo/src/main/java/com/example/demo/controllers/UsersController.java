package com.example.demo.controllers;

import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/allUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(value = "/email/{suffixes}")
    public List<User> getUsersByEmailSuffix(@PathVariable String suffixes) {
        String[] suffixList = suffixes.split(",");
        return userRepository.findAll().stream()
                .filter((user) -> Arrays.stream(suffixList).anyMatch((suffix) -> user.getEmail().endsWith(suffix)))
                .collect(Collectors.toList());
    }
}
