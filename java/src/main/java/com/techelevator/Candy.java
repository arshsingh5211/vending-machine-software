package com.techelevator;

import java.math.BigDecimal;

public class Candy implements Vendable {
    private BigDecimal price;
    private String name;
    private final String SOUND = "Munch, Munch, Yum!";

    public Candy(BigDecimal price, String name) {
        this.price = price;
        this.name = name;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String getSound() {
        return SOUND;
    }
}
