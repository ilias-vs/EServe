package com.eservice.model.repository;

import com.eservice.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StoreRepository {
    private IStore store;

    public IStore getStore(){
        return store;
    }


    public void setStore(){

        // Create Tables instances
        ITables table1 = Factory.createTable();
        table1.setId_of_reservation("123");
        table1.setName("Table 1");
        table1.setUser_id("user2");
        table1.setOrder_id(1);
        table1.setTable_id(1);
        table1.setIs_reserved(true);

        ITables table2 = Factory.createTable();
        table2.setId_of_reservation("456");
        table2.setName("Table 2");
        table2.setUser_id( "user5");
        table2.setOrder_id(2);
        table2.setTable_id(2);
        table2.setIs_reserved(true);


        List<ITables> tablesList = new ArrayList<>();
        tablesList.add(table1);
        tablesList.add(table2);







        List<IMenuItem> menuItemList = new ArrayList<>();
        List<IMenuCategory> menuCategoryList = new ArrayList<>();

        List<IMenuItem> menuItemList2 = new ArrayList<>();


        IMenuItem menuItem = Factory.createMenuItem();
        menuItem.setName("Freddo Espresso");
        menuItem.setCost("2.1");
        IMenuItem menuItem2 = Factory.createMenuItem();
        menuItem2.setName("Freddo Cappuccino");
        menuItem2.setCost("2.4");
        IMenuItem menuItem3 = Factory.createMenuItem();
        menuItem3.setName("Espresso");
        menuItem3.setCost("1.9");
        IMenuItem menuItem4 = Factory.createMenuItem();
        menuItem4.setName("Espresso Double");
        menuItem4.setCost("2.3");
        menuItemList.add(menuItem);
        menuItemList.add(menuItem2);
        menuItemList.add(menuItem3);
        menuItemList.add(menuItem4);


        IMenuItem menuItem5 = Factory.createMenuItem();
        menuItem5.setName("Souvlaki Xirino");
        menuItem5.setCost("3.2");
        IMenuItem menuItem6 = Factory.createMenuItem();
        menuItem6.setName("Souvlaki kotopoulo");
        menuItem6.setCost("3.4");
        IMenuItem menuItem7 = Factory.createMenuItem();
        menuItem7.setName("Gyros Xirinos");
        menuItem7.setCost("4.2");
        IMenuItem menuItem8 = Factory.createMenuItem();
        menuItem8.setName("Gyros Kotopoulo");
        menuItem8.setCost("4.5");
        menuItemList2.add(menuItem5);
        menuItemList2.add(menuItem6);
        menuItemList2.add(menuItem7);
        menuItemList2.add(menuItem8);

        IMenuCategory menuCategory = Factory.createMenuCategory();
        menuCategory.setCategoryName("Coffee");
        menuCategory.setMenuItems(menuItemList);
        menuCategoryList.add(menuCategory);
        IMenuCategory menuCategory2 = Factory.createMenuCategory();
        menuCategory2.setCategoryName("Wraps");
        menuCategory2.setMenuItems(menuItemList2);
        menuCategoryList.add(menuCategory2);

        IMenu menu = Factory.createMenu();


        // Create Store instance
        IStore store1 = Factory.createStore();
        store1.setName("Coffee Haven");
        store1.setOwner("John Doe");
        store1.setId("001");
        store1.setOnline(false);
        store1.setTablesList(tablesList);
        store1.setMenu(menu);


        // Define the type of the Menu object
        Type menuType = new TypeToken<IStore>() {}.getType();

        // Create an instance of Gson
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        // Convert the Menu object to JSON representation
        String jsonMenu = gson.toJson(store1);
        System.out.println(jsonMenu);
        // Create an instance of Gson
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        // Deserialize JSON into Store object
        store = gson.fromJson(jsonMenu, Store.class);
    }

    public List<ITables> getAllTables(){
        return store.getTablesList();
    }
    public List<IMenuCategory> getAllCategories(){
        return store.getMenu().getCategories();
    }

}
