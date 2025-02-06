package com.example.csv.repository;

import com.example.csv.repository.entity.StatusStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusStorageRepository extends JpaRepository<StatusStorageEntity, Long> {

    Optional<StatusStorageEntity> findByProcessId(String processId);

}
