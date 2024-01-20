package com.eservice.controllers.menu.ButtonController;

import com.eservice.model.repository.UserSingleton;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MenuItem {
    public static void add(Firestore db, String categoryName, String itemName, String cost) throws ExecutionException, InterruptedException {
        // Define the document path you want to update
        String documentPath = "stores/"+ UserSingleton.getInstance().getUser().getStoreID(); // Change to your document path


        // Retrieve the existing data from Firestore
        DocumentReference docRef = db.document(documentPath);
        DocumentSnapshot document = docRef.get().get();

        // Check if the document exists
        if (document.exists()) {
            // Extract the existing data as a map
            Map<String, Object> existingData = document.getData();

            // Modify the menu category (e.g., "coffee") to add a new item
            Map<String, Object> newMenuItem = new HashMap<>();
            newMenuItem.put("name", itemName);
            newMenuItem.put("cost", cost);
            newMenuItem.put("id", 0); // You can set the ID as needed

            // Update the menu category
            List<Map<String, Object>> menu = (List<Map<String, Object>>) existingData.get("menu");
            if (menu != null) {
                for (Map<String, Object> category : menu) {
                    if (category.get("categoryName").equals(categoryName)) {
                        List<Map<String, Object>> menuItems = (List<Map<String, Object>>) category.get("menuItems");
                        if (menuItems != null) {
                            menuItems.add(newMenuItem);
                        }
                        break;
                    }
                }
            }

            // Update the Firestore document with the modified data
            ApiFuture<WriteResult> future = docRef.set(existingData, SetOptions.merge());
            WriteResult writeResult = future.get();
            System.out.println("Document updated at: " + writeResult.getUpdateTime());
        } else {
            System.out.println("Document does not exist: " + documentPath);
        }
    }

    public static void remove(Firestore db, String categoryName, int itemPosition) throws ExecutionException, InterruptedException {
        // Define the document path you want to update
        String documentPath = "stores/"+ UserSingleton.getInstance().getUser().getStoreID(); // Change to your document path


        // Retrieve the existing data from Firestore
        DocumentReference docRef = db.document(documentPath);
        DocumentSnapshot document = docRef.get().get();

        // Check if the document exists
        if (document.exists()) {
            // Extract the existing data as a map
            Map<String, Object> existingData = document.getData();

            // Find the "menu" array
            List<Map<String, Object>> menu = (List<Map<String, Object>>) existingData.get("menu");
            if (menu != null) {
                // Search for the category by name
                for (Map<String, Object> category : menu) {
                    if (category.get("categoryName").equals(categoryName)) {
                        // Find the "menuItems" array in the category
                        List<Map<String, Object>> menuItems = (List<Map<String, Object>>) category.get("menuItems");
                        if (menuItems != null && itemPosition >= 0 && itemPosition < menuItems.size()) {
                            // Remove the item at the specified position
                            menuItems.remove(itemPosition);

                            // Update the Firestore document with the modified data
                            ApiFuture<WriteResult> future = docRef.set(existingData, SetOptions.merge());
                            WriteResult writeResult = future.get();
                            System.out.println("Document updated at: " + writeResult.getUpdateTime());
                            return; // Exit the function once the item is found and removed
                        } else {
                            System.out.println("Invalid item position or 'menuItems' array is empty.");
                        }
                    }
                }
                System.out.println("Category not found: " + categoryName);
            } else {
                System.out.println("The 'menu' array is empty.");
            }
        } else {
            System.out.println("Document does not exist: " + documentPath);
        }
    }

    public static void edit(int position, Firestore db, String categoryName, String name, String cost) throws ExecutionException, InterruptedException {

        // Define the document path you want to update
        String documentPath = "stores/"+ UserSingleton.getInstance().getUser().getStoreID(); // Change to your document path

        // Define the category and item position you want to update
        int itemPosition = position; // Change to the position of the item you want to update (0-based)

        // Define the updated name and cost
        String updatedName = name;
        String updatedCost = cost;

        // Retrieve the existing data from Firestore
        DocumentReference docRef = db.document(documentPath);
        DocumentSnapshot document = docRef.get().get();

        // Check if the document exists
        if (document.exists()) {
            // Extract the existing data as a map
            Map<String, Object> existingData = document.getData();

            // Find the category in the menu
            List<Map<String, Object>> menu = (List<Map<String, Object>>) existingData.get("menu");
            if (menu != null) {
                for (Map<String, Object> category : menu) {
                    if (category.get("categoryName").equals(categoryName)) {
                        List<Map<String, Object>> menuItems = (List<Map<String, Object>>) category.get("menuItems");
                        if (menuItems != null && itemPosition >= 0 && itemPosition < menuItems.size()) {
                            Map<String, Object> itemToUpdate = menuItems.get(itemPosition);

                            // Update the name and cost fields
                            itemToUpdate.put("name", updatedName);
                            itemToUpdate.put("cost", updatedCost);

                            // Update the Firestore document with the modified data
                            ApiFuture<WriteResult> future = docRef.set(existingData, SetOptions.merge());
                            WriteResult writeResult = future.get();
                            System.out.println("Document updated at: " + writeResult.getUpdateTime());
                            break; // Exit the loop once the item is found and updated
                        }
                    }
                }
            }
        } else {
            System.out.println("Document does not exist: " + documentPath);
        }
    }
}
