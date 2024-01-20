package com.eservice.model;

import java.util.List;

public interface IMenu {
    List<IMenuCategory> getCategories();

    void setCategories(List<IMenuCategory> categories);
}
