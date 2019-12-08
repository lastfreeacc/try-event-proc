package ru.lastfreeacc.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Runner implements CommandLineRunner {
    private final LogConsumer logConsumer;

    @Autowired
    public Runner(LogConsumer logConsumer) {
        this.logConsumer = logConsumer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logConsumer.consume();
    }
}
