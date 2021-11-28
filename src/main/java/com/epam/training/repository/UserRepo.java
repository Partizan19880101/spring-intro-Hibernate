package com.epam.training.repository;

import com.epam.training.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    List<UserEntity> findByName(String name);
}
