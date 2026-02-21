package com.example.reactiveprogramming.timeout;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

import java.time.Duration;

public class TimeoutSample {
    private static final String BASE_URL = "http://localhost:7070";

    private final HttpClient httpClient = HttpClient.create().runOn(LoopResources.create("vins", 1, true)).baseUrl(BASE_URL);

    public void postConstruct() {
        this.getProductName(1).subscribe(System.out::println);
    }

    public Mono<String> getProductName(int productId) {
        var defaultPath = "/demo03/product/" + productId;
        var timeoutPath = "/demo03/timeout-fallback/product/" + productId;
        var emptyPath = "/demo03/empty-fallback/product/" + productId;
        return getProductName(defaultPath)
                .timeout(Duration.ofSeconds(2), getProductName(timeoutPath))
                .switchIfEmpty(getProductName(emptyPath));
    }

    private Mono<String> getProductName(String path) {
        return this.httpClient.get()
                .uri(path)
                .responseContent()
                .asString()
                .next();
    }
}
