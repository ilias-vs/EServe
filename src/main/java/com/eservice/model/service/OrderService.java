package com.eservice.model.service;

import com.eservice.model.*;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class OrderService {

    public static List<IOrders> getOrderByStore(String owner_id,Firestore db) throws ExecutionException, InterruptedException {
        List<IOrders> ordersList=new ArrayList<>();
        String ownerValue = owner_id;
        // Create a query that filters documents based on the "owner" field
        Query query = db.collection("orders")
                .whereEqualTo("store", ownerValue);
        // Execute the query
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        // Retrieve documents that match the query
        for (QueryDocumentSnapshot document : querySnapshot.get().getDocuments()) {
            IOrders order = Factory.createOrders();
            List<IMenuItem> menuItemList=new ArrayList<>();
            order.setMenuItems(menuItemList);
            order.setConcluded(document.getBoolean("concluded"));
            order.setCost((Number) document.get("cost"));
            order.setOrder_id((Number) document.get("order_id"));
            order.setStore_id(document.getString("store"));
            order.setTable_id((Number) document.get("table_id"));
            order.setUser_id(document.getString("user"));

            List<Map<String, Object>> orderList = (List<Map<String, Object>>) document.get("order");
            if (orderList != null) {
                for (Map<String, Object> orders : orderList) {
                    IMenuItem menuItem=Factory.createMenuItem();
                    menuItem.setName((String) orders.get("name"));
                    menuItem.setCost((String) orders.get("cost"));
                    menuItemList.add(menuItem);
                }
            }

            ordersList.add(order);
        }
        return ordersList;
    }

    public static String getClientName(String userID,Firestore db) throws ExecutionException, InterruptedException {
        String collectionName = "users"; // Change to your collection name
        String displayName = null;
        // Build the reference to the document with the specified user ID
        DocumentReference docRef = db.collection(collectionName).document(userID);

        // Retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            // Document exists, you can access its data
            Map<String, Object> userData = document.getData();
            displayName=userData.get("displayName").toString();
        } else {
            System.out.println("Document with userID " + userID + " does not exist.");
        }
        return displayName;
    }

    public static String getStoreName(){

        return null;
    }
}
