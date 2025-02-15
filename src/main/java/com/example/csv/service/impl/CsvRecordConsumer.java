package com.example.csv.service.impl;

import com.example.csv.model.CsvRecord;
import com.example.csv.model.enums.ProcessingStatus;
import com.example.csv.repository.entity.StatusStorageEntity;
import com.example.csv.service.StatusStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class CsvRecordConsumer {

    private final StatusStorageService statusStorageService;
    private final KafkaTemplate<String, CsvRecord> kafkaTemplate;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "csv-readers")
    public void consumeRecord(ConsumerRecord<String, CsvRecord> csvRecord) {
        log.info("Started upload");
        final String uuid = getUUId(csvRecord);
        if (Objects.isNull(statusStorageService.getProcessingStatus(uuid))) {
            final StatusStorageEntity entity = new StatusStorageEntity();
            entity.setStatus(ProcessingStatus.STARTED);
            entity.setProcessId(uuid);
            entity.setProcessedRecords(0);
            statusStorageService.create(entity);
        }
        MessageBuilder<CsvRecord> messageBuilder = MessageBuilder
                .withPayload(csvRecord.value())
                .setHeader(KafkaHeaders.TOPIC, "csv-updates")
                .setHeader("uuid", uuid);

        if (csvRecord.headers().lastHeader("file-end") != null) {
            messageBuilder.setHeader("file-end",
                    new String(csvRecord.headers().lastHeader("file-end").value(), StandardCharsets.UTF_8)
            );
        }
        kafkaTemplate.send(messageBuilder.build());
    }

    @KafkaListener(topics = "csv-updates", groupId = "csv-updaters")
    public void updateRecord(ConsumerRecord<String, CsvRecord> csvRecord) {
        log.info("Update in progress...");
        final String uuid = getUUId(csvRecord);
        final String fileEndHeader = Objects.isNull(csvRecord.headers().lastHeader("file-end")) ?
                ""
                :
                new String(csvRecord.headers().lastHeader("file-end").value(), StandardCharsets.UTF_8);
        final boolean isEndOfFile = Objects.equals(fileEndHeader, "true");
        statusStorageService.incrementProcessingStatus(uuid, isEndOfFile);
    }

    private static String getUUId(ConsumerRecord<String, CsvRecord> csvRecord) {
        final Header header = csvRecord.headers().lastHeader("uuid");
        if (Objects.isNull(header)) {
            log.error("No header found");
        }
        final String uuid = new String(header.value(), StandardCharsets.UTF_8);
        log.info("Received CSV record from Kafka: '{}' for id: '{}'", csvRecord.value(), uuid);
        return uuid;
    }
}
