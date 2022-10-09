package com.techelevator.common;

import com.techelevator.Item;

import java.math.BigDecimal;

public class Chips extends Item {
    private BigDecimal price;
    private String name;
    private final String SOUND = "Crunch, Crunch, Yum!";

    public Chips(BigDecimal price, String name) {
        super(price, name);
    }

    @Override
    public String getSound() {
        return SOUND;
    }
}
