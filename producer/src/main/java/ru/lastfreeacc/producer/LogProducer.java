package ru.lastfreeacc.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogProducer {
    @Value("${inner.topic}")
    private String topic;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public LogProducer(
            KafkaTemplate<String, String> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce() {
        for (int i = 0; i < 20; i++) {
            kafkaTemplate.send(topic, "Message-" + i);
        }
    }
}
