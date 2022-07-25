package com.example.Training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@EnableRetry
public class TrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingApplication.class, args);
	}

	@Bean
	public Executor getThreadPoolExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(4);
		executor.setQueueCapacity(500);
        executor.initialize();
		return executor;
	}

	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder builder){
		return builder
				.setReadTimeout(Duration.ofMillis(1000))
				.build();
	}

}
