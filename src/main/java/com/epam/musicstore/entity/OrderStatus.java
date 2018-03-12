package com.epam.musicstore.entity;

public class OrderStatus extends LocaleName {
    public OrderStatus(Integer id){
        setId(id);
    }

    public OrderStatus() {

    }

    @Override
    public String toString() {
        return "OrderStatus{}" + super.toString();
    }
}
