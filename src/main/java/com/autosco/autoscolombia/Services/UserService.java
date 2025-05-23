package com.autosco.autoscolombia.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.autosco.autoscolombia.Model.User;

@Service
public interface UserService {

    List<User> getAllUsers();
    void save(User user);
    void delete(Long id);
    void update(User user);
    User getUserById(Long id);
    User findByDocument(String document);
    User findByEmail(String email);
    
}
