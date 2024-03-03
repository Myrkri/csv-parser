package com.example.csv.repository;

import com.example.csv.repository.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDetailsEntity, Long> {

    Optional<UserDetailsEntity> findByUsername(String username);

}
