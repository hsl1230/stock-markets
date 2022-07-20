package com.example.stockmarkets;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.stockmarkets.rest.controller.DowJonesIndexController;

@SpringBootTest
class StockMarketsApplicationTests {
    @Autowired
    private DowJonesIndexController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }
}
