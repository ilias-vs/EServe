package com.eservice.model.repository;

public class StoreSingleton {
    private String storeName;
    private String location;
    private String ownerID;
    private String StoreID;
    private static StoreSingleton uniqInstance;

    // Private constructor to prevent direct instantiation
    private StoreRepository storeRepo;

    private StoreSingleton() {
        storeRepo = new StoreRepository();
    }

    public static StoreSingleton getInstance() {
        if (uniqInstance == null)
            uniqInstance = new StoreSingleton();
        return uniqInstance;
    }

    public StoreRepository getStoreRepo() {
        return storeRepo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }
}
