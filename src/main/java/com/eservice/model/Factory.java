package com.eservice.model;

public class Factory {
    private IStore store;
    private IMenu menu;
    private IMenuCategory menuCategory;
    private IMenuItem menuItem;
    private ITables tables;

    public static IUser createUser(){
        return new User();
    }
    public static IStore createStore(){
        return new Store();
    }

    public static ITables createTable(){
        return new Tables();
    }
    public static IMenuItem createMenuItem(){
        return new MenuItem();
    }
    public static IMenuCategory createMenuCategory(){
        return new MenuCategory();
    }

    public static IMenu createMenu(){
        return new Menu();
    }
    public static IOrders createOrders(){
        return new Orders();
    }
}
