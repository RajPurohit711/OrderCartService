package com.example.OrderCartService.service;


import com.example.OrderCartService.entity.Order;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

    Order getOrder(String order);
    List<Order> getOrdersByUserId(Long userId);
    void addOrder(Order order);
    void deleteOrder(String id);
}
