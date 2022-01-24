package com.example.OrderCartService.controller;

import com.example.OrderCartService.dto.OrderDto;
import com.example.OrderCartService.dto.OrderEmailDto;
import com.example.OrderCartService.entity.Order;
import com.example.OrderCartService.service.OrderService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange exchange;

    OrderEmailDto orderEmailDto=new OrderEmailDto();

    @GetMapping("/{id}")
    OrderDto getOrderById(String id) {
        Order order = orderService.getOrder(id);
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(order, orderDto);
        return orderDto;
    }

    @GetMapping("/getByUserId/{id}")
    List<OrderDto> getOrderByUserid(Long id) {

        List<OrderDto> orderDtos = new ArrayList<>();

        List<Order> orders = orderService.getOrdersByUserId(id);
        for (Order order : orders) {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    @RequestMapping(value = "/addOrder", method = {RequestMethod.POST, RequestMethod.PUT})
    void addOrder(@RequestBody OrderDto orderDto) {
        Order order = new Order();
        orderEmailDto.setEmail("purohitraj4553@gmail.com");
        orderEmailDto.setName("Raj Purohit");
        rabbitTemplate.convertAndSend(exchange.getName(),"routing.OrderEmail",orderEmailDto);

        BeanUtils.copyProperties(orderDto,order);
        orderService.addOrder(order);
    }

    @DeleteMapping(value = "/deleteOrder")
    void deleteOrder(@PathVariable String id){
        orderService.deleteOrder(id);
    }



}
