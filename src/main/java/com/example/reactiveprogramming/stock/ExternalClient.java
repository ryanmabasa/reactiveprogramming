package com.example.reactiveprogramming.stock;

import reactor.core.publisher.Flux;

public class ExternalClient {

    public static Flux<Integer> getPriceChanges() {
        return Flux.just(91,111,100,10);
    }
}
