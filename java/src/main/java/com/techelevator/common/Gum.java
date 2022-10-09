package com.techelevator.common;

import java.math.BigDecimal;

public class Gum extends Item {
    private BigDecimal price;
    private String name;
    private final String SOUND = "Chew, Chew, Yum!";

    public Gum(BigDecimal price, String name) {
        super(price, name);
    }

    @Override
    public String getSound() {
        return SOUND;
    }
}
