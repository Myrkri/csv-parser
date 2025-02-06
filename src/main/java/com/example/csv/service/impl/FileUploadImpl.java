package com.example.csv.service.impl;

import com.example.csv.model.CsvRecord;
import com.example.csv.model.enums.ProcessingStatus;
import com.example.csv.service.FileUpload;
import com.github.mygreen.supercsv.io.LazyCsvAnnotationBeanReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class FileUploadImpl implements FileUpload {

    private final KafkaTemplate<String, CsvRecord> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topic;

    @Override
    public Map<String, ProcessingStatus> upload(final MultipartFile file) {
        final String uuid = UUID.randomUUID().toString();

        try (LazyCsvAnnotationBeanReader<CsvRecord> csvReader = new LazyCsvAnnotationBeanReader<>(
                CsvRecord.class,
                new InputStreamReader(file.getInputStream()), CsvPreference.STANDARD_PREFERENCE)) {

            for (CsvRecord csvRecord : csvReader.readAll()) {
                kafkaTemplate.send(
                        MessageBuilder
                                .withPayload(csvRecord)
                                .setHeader(KafkaHeaders.TOPIC, topic)
                                .setHeader("uuid", uuid)
                                .build());
            }
        } catch (IOException e) {
            log.error("Failed to read file", e);
        }
        return Map.of(uuid, ProcessingStatus.PROCESSING);
    }
}
