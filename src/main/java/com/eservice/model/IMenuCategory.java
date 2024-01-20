package com.eservice.model;

import java.util.List;

public interface IMenuCategory {
    String getCategoryName();

    void setCategoryName(String categoryName);

    List<IMenuItem> getMenuItems();

    void setMenuItems(List<IMenuItem> menuItems);
}
