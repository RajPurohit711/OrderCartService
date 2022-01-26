package com.example.OrderCartService.controller;

import com.example.OrderCartService.dto.*;
import com.example.OrderCartService.entity.Order;
import com.example.OrderCartService.service.CartService;
import com.example.OrderCartService.service.OrderService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

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
        MerchantHistoryDto merchantHistoryDto= new MerchantHistoryDto();
        OrderProductDto orderProductDto = new OrderProductDto();

        List<OrderItemDto> orderItemDtos = orderDto.getOrderItems();
        for(OrderItemDto orderItemDto : orderItemDtos) {
            String url = "http://localhost:8082/product/getProduct/"+orderItemDto.getProductId()+"/"+orderItemDto.getMerchantId();
            ProductDto productDto = new RestTemplate().getForObject(url, ProductDto.class);
            orderItemDto.setPrice(productDto.getPrice());
            orderItemDto.setName(productDto.getName());

            BeanUtils.copyProperties(orderItemDto,merchantHistoryDto);
            rabbitTemplate.convertAndSend(exchange.getName(),"routing.MerchantOrder",merchantHistoryDto);

            BeanUtils.copyProperties(orderItemDto,orderProductDto);
            rabbitTemplate.convertAndSend(exchange.getName(),"routing.OrderProduct",orderProductDto);

            cartService.delete(order.getUserId());

        }

        String url = "http://localhost:8080/user/" + orderDto.getUserId();
        UserDto userDto = new RestTemplate().getForObject(url,UserDto.class);
        rabbitTemplate.convertAndSend(exchange.getName(),"routing.OrderEmail",userDto);

        BeanUtils.copyProperties(orderDto,order);
        orderService.addOrder(order);

    }

    @DeleteMapping(value = "/deleteOrder")
    void deleteOrder(@PathVariable String id){
        orderService.deleteOrder(id);
    }



}
