package com.example.Training.service;

import com.example.Training.Repo.UserRepo;
import com.example.Training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         return userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    public User insertUser(User user){
        return userRepo.save(user);
    }

}
