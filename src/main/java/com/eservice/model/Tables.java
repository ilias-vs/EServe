package com.eservice.model;

import com.google.gson.annotations.Expose;

public class Tables implements ITables {
    @Expose
    private String id_of_reservation, name,user_id;
    @Expose

    private int order_id,table_id;
    @Expose
    private Boolean is_reserved;


    @Override
    public String getId_of_reservation() {
        return id_of_reservation;
    }

    @Override
    public void setId_of_reservation(String id_of_reservation) {
        this.id_of_reservation = id_of_reservation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
    public int getOrder_id() {
        return order_id;
    }

    @Override
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @Override
    public int getTable_id() {
        return table_id;
    }

    @Override
    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    @Override
    public Boolean getIs_reserved() {
        return is_reserved;
    }

    @Override
    public void setIs_reserved(Boolean is_reserved) {
        this.is_reserved = is_reserved;
    }
}
