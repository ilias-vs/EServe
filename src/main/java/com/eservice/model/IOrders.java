package com.eservice.model;

import java.util.List;

public interface IOrders {
    String getStore_id();

    void setStore_id(String store_id);

    String getUser_id();

    void setUser_id(String user_id);

    Boolean getConcluded();

    void setConcluded(Boolean concluded);

    Number getCost();

    void setCost(Number cost);

    Number getOrder_id();

    void setOrder_id(Number order_id);

    Number getTable_id();

    void setTable_id(Number table_id);

    List<IMenuItem> getMenuItems();

    void setMenuItems(List<IMenuItem> menuItems);
}
