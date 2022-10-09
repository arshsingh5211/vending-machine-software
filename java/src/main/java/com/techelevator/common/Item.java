package com.techelevator.common;

import java.math.BigDecimal;

abstract class Item {
    private BigDecimal price;
    private String name;
    private String sound = "";

    public Item(BigDecimal price, String name) {
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public String getSound() {
        return sound;
    }
}
