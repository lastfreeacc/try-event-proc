package ru.lastfreeacc.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Runner implements CommandLineRunner {
    private final LogProducer logProducer;

    @Autowired
    public Runner(LogProducer logProducer) {
        this.logProducer = logProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logProducer.produce();
    }
}
