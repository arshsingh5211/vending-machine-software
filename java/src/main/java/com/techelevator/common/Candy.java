package com.techelevator.common;

import com.techelevator.Item;

import java.math.BigDecimal;

public class Candy extends Item {
    private BigDecimal price;
    private String name;
    private final String SOUND = "Munch, Munch, Yum!";

    public Candy(BigDecimal price, String name) {
        super(price, name);
    }

    @Override
    public String getSound() {
        return SOUND;
    }
}
