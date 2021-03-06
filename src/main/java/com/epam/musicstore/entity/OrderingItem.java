package com.epam.musicstore.entity;

import org.joda.money.Money;

public class OrderingItem extends BaseEntity {
    private Order order;
    private Product product;
    private int amount;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + amount;
        return result;
    }

    @Override
    public String toString() {
        return "OrderingItem{" +
                "order=" + order +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }

    public Money getPrice() {
        return product.getPrice().multipliedBy(amount);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
