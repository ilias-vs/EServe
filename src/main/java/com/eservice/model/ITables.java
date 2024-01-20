package com.eservice.model;public interface ITables {
    String getId_of_reservation();
    void setId_of_reservation(String id_of_reservation);
    String getName();
    void setName(String name);
    String getUser_id();
    void setUser_id(String user_id);
    int getOrder_id();
    void setOrder_id(int order_id);
    int getTable_id();
    void setTable_id(int table_id);
    Boolean getIs_reserved();
    void setIs_reserved(Boolean is_reserved);
}