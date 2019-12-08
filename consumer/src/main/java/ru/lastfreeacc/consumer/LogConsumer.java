package ru.lastfreeacc.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

@Component
@Slf4j
public class LogConsumer {
    @Value("${inner.latency}")
    private Long latency;
    @Value("${inner.batch.interval}")
    private Long batchInterval;

    private final Consumer<String, String> consumer;

    @Autowired
    public LogConsumer(Consumer<String, String> consumer) {
        this.consumer = consumer;
    }

    public void consume() {
        try {
            while (true) {
                processBatch();
            }
        } catch (IOException e) {
            log.warn("problems with consume message", e);
        } finally {
            consumer.close();
        }

    }

    private void processBatch() throws IOException {
        ConsumerRecords<String, String> consumerRecords =
                consumer.poll(Duration.ofMillis(batchInterval));
        if (consumerRecords.isEmpty()) {
            log.debug("nothing to consume");
            return;
        }
        Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();

        for (ConsumerRecord<String, String> record: consumerRecords) {
            processRecord(record);
            appendCommitMap(map, record);
        }

        consumer.commitSync(map);
    }

    private void appendCommitMap(Map<TopicPartition, OffsetAndMetadata> map, ConsumerRecord<String, String> record) {
        TopicPartition tp = new TopicPartition(record.topic(), record.partition());
        OffsetAndMetadata om = new OffsetAndMetadata(record.offset());
        map.put(tp, om);
    }

    private void processRecord(ConsumerRecord<String, String> record) throws IOException {
        doHardWork();
        Path file = Paths.get("./db.txt");
        Files.write(file, Collections.singleton(record.value()), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    private void doHardWork() {
        try {
            sleep(latency);
        } catch (InterruptedException e) {
            log.warn("problems with hard work");
        }
    }
}
