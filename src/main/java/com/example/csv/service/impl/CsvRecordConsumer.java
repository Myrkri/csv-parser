package com.example.csv.service.impl;

import com.example.csv.model.CsvRecord;
import com.example.csv.service.StatusStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class CsvRecordConsumer {

    private final StatusStorageService statusStorageService;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "csv-readers")
    public void consumeRecord(ConsumerRecord<String, CsvRecord> csvRecord) {
        final Header header = csvRecord.headers().lastHeader("uuid");
        if (Objects.isNull(header)) {
            log.error("No header found");
        }
        final String uuid = csvRecord.value().toString();
        log.info("Received CSV record from Kafka: '{}' for id: '{}'", csvRecord.toString(), uuid);
        statusStorageService.incrementProcessingStatus(uuid);

    }
}
