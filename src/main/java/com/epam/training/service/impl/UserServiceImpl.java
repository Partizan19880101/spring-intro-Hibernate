package com.epam.training.service.impl;

import com.epam.training.model.UserEntity;
import com.epam.training.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.epam.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type User service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepo userRepo;

    @Override
    public List<UserEntity> getAllUsers() {
        return (List<UserEntity>) userRepo.findAll();
    }

    @Override
    public UserEntity getUserById(long userId) {
        log.info("Attempting to get user with id {}", userId);
        return userRepo.findById(userId).orElse(null);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        log.info("Attempting to find user with email {}", email);
        return userRepo.findByEmail(email);
    }

    @Override
    public List<UserEntity> getUsersByName(String name, int pageSize, int pageNum) {
        log.info("Attempting to get users with name {}", name);
        return userRepo.findByName(name);
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        log.info("Attempting to create new user");
        return userRepo.save(user);
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        log.info("Attempting to update user with id {}", user.getId());
        userRepo.delete(user);
        return userRepo.save(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        log.info("Attempting to delete user with id {}", userId);
        userRepo.deleteById(userId);
        return true;
    }
}
