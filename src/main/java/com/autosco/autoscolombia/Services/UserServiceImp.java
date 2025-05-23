package com.autosco.autoscolombia.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.autosco.autoscolombia.Model.User;
import com.autosco.autoscolombia.Repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

    UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override  
    public User findByDocument (String document) {
        return userRepository.findByDocument(document);
    }

    @Override
    public User findByEmail (String email) {
        return userRepository.findByEmail(email);
    }

}   
