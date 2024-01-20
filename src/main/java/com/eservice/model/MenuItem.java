package com.eservice.model;

import com.google.gson.annotations.Expose;

public class MenuItem implements IMenuItem {
    @Expose
    private String name;
    @Expose
    private String cost;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCost() {
        return cost;
    }

    @Override
    public void setCost(String cost) {
        this.cost = cost;
    }
}
