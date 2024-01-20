package com.eservice.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Menu implements IMenu {
    @Expose
    private List<IMenuCategory> categories;

    @Override
    public List<IMenuCategory> getCategories() {
        return categories;
    }

    @Override
    public void setCategories(List<IMenuCategory> categories) {
        this.categories = categories;
    }
}
