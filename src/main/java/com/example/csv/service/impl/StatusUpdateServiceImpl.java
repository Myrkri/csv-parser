package com.example.csv.service.impl;

import com.example.csv.model.CsvRecord;
import com.example.csv.service.StatusUpdateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j2
public class StatusUpdateServiceImpl implements StatusUpdateService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Override
    public SseEmitter subscribe(String uuid) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(uuid, emitter);
        emitter.onCompletion(() -> emitters.remove(uuid));
        emitter.onTimeout(() -> emitters.remove(uuid));
        return emitter;
    }

    @Override
    public void sendUpdate(String uuid, CsvRecord record, boolean isEndOfFile) {
        SseEmitter emitter = emitters.get(uuid);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("record-update")
                        .data(record));

                if (isEndOfFile) {
                    emitter.complete();
                    emitters.remove(uuid);
                }
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(uuid);
            }
        }
    }
}
