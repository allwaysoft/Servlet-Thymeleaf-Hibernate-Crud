package com.example.crudweb.service;

import com.example.crudweb.entity.User;

import com.example.crudweb.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    public User getById(Long id) {
        try {
            return userRepository.getById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public void create(String firstName, String lastName, String specialty) {
        User user = getNewUser(firstName, lastName, specialty);
        userRepository.add(user);
    }

    public void update(Long id, String firstName, String lastName, String specialty) {
        User user = getNewUser(firstName, lastName, specialty);
        user.setId(id);
        userRepository.update(user);
    }

    private User getNewUser(String firstName, String lastName, String specialty) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setSpecialty(specialty);
        return user;
    }
}
