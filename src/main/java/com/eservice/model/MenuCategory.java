package com.eservice.model;
import com.google.gson.annotations.Expose;

import java.util.List;

public class MenuCategory implements IMenuCategory {
    @Expose
    private String categoryName;
    @Expose
    private List<IMenuItem> menuItems;


    @Override
    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public List<IMenuItem> getMenuItems() {
        return menuItems;
    }

    @Override
    public void setMenuItems(List<IMenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
