package com.techelevator;

import java.math.BigDecimal;

public class Gum implements Vendable{
    private BigDecimal price;
    private String name;
    private final String SOUND = "Chew, Chew, Yum!";

    public Gum(BigDecimal price, String name) {
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
