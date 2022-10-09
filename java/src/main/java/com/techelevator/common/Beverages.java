package com.techelevator.common;

import com.techelevator.Item;

import java.math.BigDecimal;

public class Beverages extends Item {
    private BigDecimal price;
    private String name;
    private final String SOUND = "Glug, Glug, Yum!";


    public Beverages(BigDecimal price, String name) {
        super(price, name);
    }

    @Override
    public String getSound() {
        return SOUND;
    }
}
