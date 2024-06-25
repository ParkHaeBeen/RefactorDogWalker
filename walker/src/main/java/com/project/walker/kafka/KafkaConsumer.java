package com.project.walker.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.walker.kafka.dto.ReserveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final ObjectMapper objectMapper;

    @KafkaListener(groupId = "dogWalker_reserve", topics = {"RESERVE"})
    public void reserve(ConsumerRecord<String, String> record) throws JsonProcessingException {
        final ReserveDto response = objectMapper.readValue(record.value(), ReserveDto.class);

        //TODO : 알림
        log.info("Reserved data: {}", response);
    }
}
