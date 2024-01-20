package com.eservice.model;

import java.util.List;

public interface IStore {
    String getName();

    void setName(String name);

    String getOwner();

    void setOwner(String owner);

    String getId();

    void setId(String id);

    Boolean getOnline();

    void setOnline(Boolean online);

    List<ITables> getTablesList();

    void setTablesList(List<ITables> tablesList);

    IMenu getMenu();

    void setMenu(IMenu menu);

    void setAddress(String address);
    String getAdress();

    String getAddress();

    String getImageData();

    void setImageData(String imageData);
}
