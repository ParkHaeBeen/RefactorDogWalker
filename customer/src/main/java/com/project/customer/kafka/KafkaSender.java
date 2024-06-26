package com.project.customer.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.customer.kafka.dto.ReserveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendReserve(final Topic topic, final ReserveDto dto) throws JsonProcessingException {
        kafkaTemplate.send(topic.name(), objectMapper.writeValueAsString(dto));
    }
}
