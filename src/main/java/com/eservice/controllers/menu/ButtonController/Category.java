package com.eservice.controllers.menu.ButtonController;

import com.eservice.model.repository.UserSingleton;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Category {

    public static void add(Firestore db, String value1) throws ExecutionException, InterruptedException {
        // Define the document path you want to update
        String documentPath = "stores/"+ UserSingleton.getInstance().getUser().getStoreID(); // Change to your document path

        // Define the new category name
        String newCategoryName = value1; // Change to the desired category name

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
                // Create a new category object with the specified name and an empty menuItems array
                Map<String, Object> newCategory = new HashMap<>();
                newCategory.put("categoryName", newCategoryName);
                newCategory.put("menuItems", new ArrayList<>()); // Create an empty menuItems array

                // Add the new category to the end of the "menu" array
                menu.add(newCategory);

                // Update the Firestore document with the modified "menu" array
                ApiFuture<WriteResult> future = docRef.set(existingData, SetOptions.merge());
                WriteResult writeResult = future.get();
                System.out.println("Document updated at: " + writeResult.getUpdateTime());
            } else {
                System.out.println("The 'menu' array does not exist in the document.");
            }
        } else {
            System.out.println("Document does not exist: " + documentPath);
        }
    }

    public static void remove(Firestore db,int position) throws ExecutionException, InterruptedException{
        // Define the document path you want to update
        String documentPath = "stores/"+ UserSingleton.getInstance().getUser().getStoreID(); // Change to your document path

        // Define the position of the category you want to remove (0-based)
        int categoryPositionToRemove = position; // Change to the position of the category you want to remove

        // Retrieve the existing data from Firestore
        DocumentReference docRef = db.document(documentPath);
        DocumentSnapshot document = docRef.get().get();

        // Check if the document exists
        if (document.exists()) {
            // Extract the existing data as a map
            Map<String, Object> existingData = document.getData();

            // Find the "menu" array
            List<Map<String, Object>> menu = (List<Map<String, Object>>) existingData.get("menu");
            if (menu != null && categoryPositionToRemove >= 0 && categoryPositionToRemove < menu.size()) {
                // Remove the category at the specified position
                menu.remove(categoryPositionToRemove);

                // Update the Firestore document with the modified "menu" array
                ApiFuture<WriteResult> future = docRef.set(existingData, SetOptions.merge());
                WriteResult writeResult = future.get();
                System.out.println("Document updated at: " + writeResult.getUpdateTime());
            } else {
                System.out.println("Invalid category position or menu array is empty.");
            }
        } else {
            System.out.println("Document does not exist: " + documentPath);
        }
    }

    public static void edit(int position,Firestore db,String name) throws ExecutionException, InterruptedException {
        // Define the document path you want to update
        String documentPath = "stores/"+ UserSingleton.getInstance().getUser().getStoreID(); // Change to your document path

        // Define the category position you want to update (0-based)
        int categoryPosition = position; // Change to the position of the category you want to update

        // Define the updated category name
        String updatedCategoryName = name;

        // Retrieve the existing data from Firestore
        DocumentReference docRef = db.document(documentPath);
        DocumentSnapshot document = docRef.get().get();

        // Check if the document exists
        if (document.exists()) {
            // Extract the existing data as a map
            Map<String, Object> existingData = document.getData();

            // Find the "menu" array
            List<Map<String, Object>> menu = (List<Map<String, Object>>) existingData.get("menu");
            if (menu != null && categoryPosition >= 0 && categoryPosition < menu.size()) {
                // Update the category name
                menu.get(categoryPosition).put("categoryName", updatedCategoryName);

                // Update the Firestore document with the modified "menu" array
                ApiFuture<WriteResult> future = docRef.set(existingData, SetOptions.merge());
                WriteResult writeResult = future.get();
                System.out.println("Document updated at: " + writeResult.getUpdateTime());
            } else {
                System.out.println("Invalid category position or menu array is empty.");
            }
        } else {
            System.out.println("Document does not exist: " + documentPath);
        }
    }
}
