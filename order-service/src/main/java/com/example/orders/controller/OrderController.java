package com.example.orders.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @GetMapping("/orders")
    public List<String> getOrders() {
        return List.of("Order-101", "Order-102", "Order-103");
    }
}