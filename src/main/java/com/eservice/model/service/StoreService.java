package com.eservice.model.service;

import com.eservice.model.*;
import com.eservice.model.repository.UserSingleton;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class StoreService {
    public static IStore getStoresByOwner(Firestore db, String ownerID) throws ExecutionException, InterruptedException {
        IStore store = Factory.createStore();
        String ownerValue = ownerID;
        // Create a query that filters documents based on the "owner" field
        Query query = db.collection("stores")
                .whereEqualTo("owner", ownerValue);
        // Execute the query
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        // Retrieve documents that match the query
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            System.out.println(document);
            String id = document.getId();
            String name = document.getString("name");
            Boolean online = document.getBoolean("online");
            String address = document.getString("address");
            String owner = document.getString("owner");

            store.setImageData(document.getString("imageData"));
            store.setId(id);
            store.setName(name);
            store.setOwner(owner);
            store.setOnline(online);
            store.setAddress(address);

            System.out.println("Store Data ......");
            System.out.println("Store ID: " + id);
            System.out.println("Store Name: " + name);
            System.out.println("Store online: " + online);
            System.out.println("Store address: " + address);
            System.out.println("Store owner: " + owner);
            System.out.println();

            // Access the "menu" field
            IMenu menu1 = Factory.createMenu();

            // Access the "tables" field
            List<Map<String, Object>> tables = (List<Map<String, Object>>) document.get("tables");
            if (tables != null) {
                List<ITables> tablesList = new ArrayList<>();
                for (Map<String, Object> table : tables) {
                    ITables table1 = Factory.createTable();
                    table1.setName((String) table.get("name"));
                    table1.setIs_reserved((boolean) table.get("is_reserved"));
                    tablesList.add(table1);
                    Number tableId = (Number) table.get("table_id");
                    String tableName = (String) table.get("name");
                    boolean isReserved = (boolean) table.get("is_reserved");
                    System.out.println("Table ID: " + tableId);
                    System.out.println("Table Name: " + tableName);
                    System.out.println("Is Reserved: " + isReserved);
                    System.out.println();
                }
                store.setTablesList(tablesList);
            }

            List<IMenuCategory> menuCategoryList = new ArrayList<>();
            menu1.setCategories(menuCategoryList);

            List<Map<String, Object>> menu = (List<Map<String, Object>>) document.get("menu");
            if (menu != null) {
                for (Map<String, Object> menuCategory : menu) {
                    IMenuCategory menuCategory1 = Factory.createMenuCategory();
                    menuCategory1.setCategoryName((String) menuCategory.get("categoryName"));
                    String categoryName = (String) menuCategory.get("categoryName");
                    List<IMenuItem> menuItemList = new ArrayList<>();
                    System.out.println("Category Name: " + categoryName);
                    menuCategory1.setMenuItems(menuItemList);
                    List<Map<String, Object>> menuItems = (List<Map<String, Object>>) menuCategory.get("menuItems");
                    if (menuItems != null) {
                        for (Map<String, Object> item : menuItems) {
                            IMenuItem menuItem = Factory.createMenuItem();
                            menuItem.setName((String) item.get("name"));
                            menuItem.setCost((String) item.get("cost"));
                            String itemName = (String) item.get("name");
                            String cost = (String) item.get("cost");
                            System.out.println("Item Name: " + itemName + " " + cost + " $");
                            menuItemList.add(menuItem);
                        }
                    }
                    menuCategoryList.add(menuCategory1);
                }
            }
            menu1.setCategories(menuCategoryList);
            store.setMenu(menu1);
        }
        return store;
    }

    public static void updateStore(String name,String address,String img) throws ExecutionException, InterruptedException {
        // Initialize Firestore
        Firestore db = FirestoreClient.getFirestore();

        // Define the collection where the store documents are stored
        String collectionName = "stores"; // Change to your collection name

        // Define the store ID of the document you want to update
        String storeIDToUpdate = UserSingleton.getInstance().getUser().getStoreID(); // Change to the desired store ID

        // Retrieve the store document by ID
        DocumentReference storeDocRef = db.collection(collectionName).document(storeIDToUpdate);
        DocumentSnapshot storeDocument = storeDocRef.get().get();

        // Check if the store document exists
        if (storeDocument.exists()) {
            // Create a map with the updated field values
            Map<String, Object> updates = new HashMap<>();
            updates.put("imageData",img); // Change to the new image URL
            updates.put("name", name); // Change to the new store name
            updates.put("address", address); // Change to the new store address

            // Update the Firestore document with the modified data
            ApiFuture<WriteResult> future = storeDocRef.update(updates);
            WriteResult writeResult = future.get();
            System.out.println("Store document updated at: " + writeResult.getUpdateTime());
        } else {
            System.out.println("Store document with ID " + storeIDToUpdate + " does not exist.");
        }
    }


    public static String createStore(String name ,String imageData,String address) throws ExecutionException, InterruptedException {
        // Initialize Firestore
        Firestore db = FirestoreClient.getFirestore();

        // Define the collection where store documents are stored
        String collectionName = "stores"; // Change to your collection name

        // Create a map representing the new store's data (without specifying the 'id' field)
        Map<String, Object> newStoreData = new HashMap<>();
        newStoreData.put("id",generateUniqueStoreId(db,collectionName) );
        newStoreData.put("name", name); // Change to the desired store name
        newStoreData.put("online", false);
        newStoreData.put("owner", ""); // Owner field initially empty
        newStoreData.put("imageData", imageData); // Change to the image data URL
        newStoreData.put("address", address); // Change to the store address
        newStoreData.put("menu", new ArrayList<>()); // Initialize empty menu list
        newStoreData.put("tables", new ArrayList<>()); // Initialize empty tables list

        // Define the collection reference for the stores
        CollectionReference storesCollectionRef = db.collection(collectionName);

        // Add the new store document to Firestore (Firestore will generate a unique document ID)
        ApiFuture<DocumentReference> future = storesCollectionRef.add(newStoreData);

        // Retrieve the document ID generated by Firestore
        DocumentReference newStoreDocRef = future.get();
        String newStoreDocumentId = newStoreDocRef.getId();

        // Print the document ID generated by Firestore
        System.out.println("New store document created with ID: " + newStoreDocumentId);
        return newStoreDocumentId;
    }

    private static String generateUniqueStoreId(Firestore db, String collectionName) {
        // Query Firestore to find the maximum store ID in the collection
        CollectionReference storesRef = db.collection(collectionName);
        Query query = storesRef.orderBy("id", Query.Direction.DESCENDING).limit(1);
        try {
            QuerySnapshot querySnapshot = query.get().get();
            if (!querySnapshot.isEmpty()) {
                DocumentSnapshot lastDocument = querySnapshot.getDocuments().get(0);
                String lastStoreId = (String) lastDocument.get("id");
                // Increment the last ID as a string (e.g., convert to integer, increment, and convert back to string)
                int incrementedId = Integer.parseInt(lastStoreId) + 1;
                return String.valueOf(incrementedId);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return "1"; // Return "1" if no documents are found (first store)
    }


    public static void updateStoreOwner(String ownerID,String storeID) throws ExecutionException, InterruptedException {
        // Initialize Firestore
        Firestore db = FirestoreClient.getFirestore();

        // Define the collection where the store documents are stored
        String collectionName = "stores"; // Change to your collection name

        // Retrieve the store document by ID
        DocumentReference storeDocRef = db.collection(collectionName).document(storeID);
        DocumentSnapshot storeDocument = storeDocRef.get().get();

        // Check if the store document exists
        if (storeDocument.exists()) {
            // Create a map with the updated field values
            Map<String, Object> updates = new HashMap<>();
            updates.put("owner",ownerID); // Change owner Document ID

            // Update the Firestore document with the modified data
            ApiFuture<WriteResult> future = storeDocRef.update(updates);
            WriteResult writeResult = future.get();
            System.out.println("Store document updated at: " + writeResult.getUpdateTime());
        } else {
            System.out.println("Store document with ID " + storeID + " does not exist.");
        }
    }
}
