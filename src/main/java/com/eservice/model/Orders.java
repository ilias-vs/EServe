package com.eservice.model;

import java.util.List;

public class Orders implements IOrders {
    private String store_id,user_id;
    private Boolean  concluded;
    private Number cost,order_id,table_id;
    private List<IMenuItem> menuItems;

    @Override
    public String getStore_id() {
        return store_id;
    }

    @Override
    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    @Override
    public String getUser_id() {
        return user_id;
    }

    @Override
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public Boolean getConcluded() {
        return concluded;
    }

    @Override
    public void setConcluded(Boolean concluded) {
        this.concluded = concluded;
    }

    @Override
    public Number getCost() {
        return cost;
    }

    @Override
    public void setCost(Number cost) {
        this.cost = cost;
    }

    @Override
    public Number getOrder_id() {
        return order_id;
    }

    @Override
    public void setOrder_id(Number order_id) {
        this.order_id = order_id;
    }

    @Override
    public Number getTable_id() {
        return table_id;
    }

    @Override
    public void setTable_id(Number table_id) {
        this.table_id = table_id;
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
