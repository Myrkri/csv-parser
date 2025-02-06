package com.example.csv.service.impl;

import com.example.csv.model.enums.ProcessingStatus;
import com.example.csv.repository.StatusStorageRepository;
import com.example.csv.repository.entity.StatusStorageEntity;
import com.example.csv.service.StatusStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class StatusStorageServiceImpl implements StatusStorageService {

    private final StatusStorageRepository statusStorageRepository;

    @Override
    @Transactional
    public ProcessingStatus getProcessingStatus(final String id) {
        final Optional<StatusStorageEntity> entity = statusStorageRepository.findByProcessId(id);
        log.info("Looking up status for {}", id);
        return entity.map(StatusStorageEntity::getStatus).orElse(null);
    }

    @Override
    public void incrementProcessingStatus(final String id) {
        final Optional<StatusStorageEntity> entity = statusStorageRepository.findByProcessId(id);
        entity.ifPresent(statusStorageEntity -> statusStorageEntity.setProcessedRecords(statusStorageEntity.getProcessedRecords() + 1));
        entity.ifPresent(statusStorageRepository::saveAndFlush);
    }

}
