package com.example.csv.service.impl;

import com.example.csv.model.CsvRecord;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CsvRecordConsumer {

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "csv-readers")
    public void consumeRecord(CsvRecord csvRecord) {
        log.info("Received CSV record from Kafka: {}", csvRecord.toString());
    }
}
