package com.example.Training.service;

import com.example.Training.Repo.FirstRepo;
import com.example.Training.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FirstService {

    @Autowired
    private FirstRepo firstRepo;

    @Autowired
    Asyncservice Asyncservice;

    public Optional<Person> getPerson(Long id){
        System.out.println(firstRepo.findByFirstNameContains("ra"));
        return firstRepo.findById(id);
    }

    @Retryable(value= Exception.class, maxAttempts = 3, backoff = @Backoff(200))
    public Object getMethod() throws Exception {
        log.info("firstservice.getMethod");
        throw new Exception();
    }

    @Recover
    public Object recoverMethod(Exception e){
        log.info("Recover method");
        return "hai ramesh";
    }
}
