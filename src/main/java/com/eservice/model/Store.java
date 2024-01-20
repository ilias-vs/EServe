package com.eservice.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Store implements IStore {
    @Expose
    private String name, owner, id,address,imageData;
    @Expose
    private Boolean online;
    @Expose
    private List<ITables> tablesList;
    @Expose
    private IMenu menu;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Boolean getOnline() {
        return online;
    }

    @Override
    public void setOnline(Boolean online) {
        this.online = online;
    }

    @Override
    public List<ITables> getTablesList() {
        return tablesList;
    }

    @Override
    public void setTablesList(List<ITables> tablesList) {
        this.tablesList = tablesList;
    }

    @Override
    public IMenu getMenu() {
        return menu;
    }

    @Override
    public void setMenu(IMenu menu) {
        this.menu = menu;
    }

    @Override
    public void setAddress(String address) {
        this.address=address;
    }

    @Override
    public String getAdress() {
        return this.address;
    }
    @Override
    public String getAddress() {
        return address;
    }
    @Override
    public String getImageData() {
        return imageData;
    }
    @Override
    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
