package com.example.reactiveprogramming.stock;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockObserverTest {


    @Test
    public void testObserver() {
        ExternalClient.getPriceChanges()
                .subscribe(new StockObserver());
    }
}