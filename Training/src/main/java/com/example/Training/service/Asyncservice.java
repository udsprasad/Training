package com.example.Training.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class Asyncservice {

    @Async
    public CompletableFuture<String> getFuture() throws InterruptedException {
        log.info("Asyncservice.getFuture");
        Thread.sleep(1000);
        return CompletableFuture.supplyAsync(() -> " Ram");
    }
}
