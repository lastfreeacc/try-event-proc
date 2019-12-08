package ru.lastfreeacc.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class LogProducer {
    @Value("${inner.topic}")
    private String topic;
    @Value("${inner.messages.num}")
    private Integer messagesNum;
    @Value("${inner.rate}")
    private Integer rate;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static int num = 0;

    @Autowired
    public LogProducer(
            KafkaTemplate<String, String> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce() {
        Runnable generator = () -> {
            for (int i = 0; i < messagesNum; i++) {
                kafkaTemplate.send(topic, "Message-" + ++num);
            }
        };
        scheduler.scheduleAtFixedRate(generator, 0, rate, TimeUnit.SECONDS);
    }
}
