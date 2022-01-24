package com.example.OrderCartService.dto;

import java.util.Date;
import java.util.List;

public class OrderDto {

    private String id;
    private Long total;
    private Date date;
    private Long userId;
    private List<OrderItemDto> orderItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItemDto orderItemDto){
        this.orderItems.add(orderItemDto);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
