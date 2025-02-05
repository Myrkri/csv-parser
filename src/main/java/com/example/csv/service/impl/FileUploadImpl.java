package com.example.csv.service.impl;

import com.example.csv.model.CsvRecord;
import com.example.csv.service.FileUpload;
import com.github.mygreen.supercsv.io.LazyCsvAnnotationBeanReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class FileUploadImpl implements FileUpload {

    private final KafkaTemplate<String, CsvRecord> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topic;

    @Override
    public List<CsvRecord> upload(final MultipartFile file) {
        List<CsvRecord> result = new ArrayList<>();

        try (LazyCsvAnnotationBeanReader<CsvRecord> csvReader = new LazyCsvAnnotationBeanReader<>(
                CsvRecord.class,
                new InputStreamReader(file.getInputStream()), CsvPreference.STANDARD_PREFERENCE)) {
            result = csvReader.readAll();

            if (!CollectionUtils.isEmpty(result)) {
                for (CsvRecord csvRecord : result) {
                    kafkaTemplate.send(topic, csvRecord);
                }
            }
        } catch (IOException e) {
            log.error("Failed to read file", e);
        }
        return result;
    }
}
