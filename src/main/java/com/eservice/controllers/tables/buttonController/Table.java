package com.eservice.controllers.tables.buttonController;

import com.eservice.model.repository.UserSingleton;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Table {
    public static void add(String name) throws ExecutionException, InterruptedException {
        System.out.println("Clicked Add Table");
        Firestore db = FirestoreClient.getFirestore();

        // Define the document path for the store where you want to add the new table
        String storeDocumentPath = "stores/"+ UserSingleton.getInstance().getUser().getStoreID(); // Change to your document path
        // Retrieve the existing store document
        DocumentReference storeDocRef = db.document(storeDocumentPath);
        DocumentSnapshot storeDocument = storeDocRef.get().get();

        // Check if the store document exists
        if (storeDocument.exists()) {
            // Extract the existing data as a map
            Map<String, Object> existingStoreData = storeDocument.getData();

            // Get the "tables" array from the store document or create it if it doesn't exist
            List<Map<String, Object>> tables = (List<Map<String, Object>>) existingStoreData.get("tables");
            if (tables == null) {
                tables = new ArrayList<>();
            }

            // Generate an auto-incremented ID for the new table
            int newTableId = getNextTableId(tables);

            // Create a map representing the new table's data
            Map<String, Object> newTable = new HashMap<>();
            newTable.put("name", name); // Change to the desired table name
            newTable.put("is_reserved",false);
            newTable.put("order_id",-1);
            newTable.put("table_id",newTableId);
            newTable.put("user_id","");
            // Add the new table data to the "tables" array
            tables.add(newTable);

            // Update the Firestore document with the modified data
            existingStoreData.put("tables", tables);
            ApiFuture<WriteResult> future = storeDocRef.set(existingStoreData, SetOptions.merge());
            WriteResult writeResult = future.get();
            System.out.println("New table added to the store. Document updated at: " + writeResult.getUpdateTime());
        } else {
            System.out.println("Store document does not exist: " + storeDocumentPath);
        }
    }

    public static void delete(int index) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        // Define the document path for the store where you want to delete the table
        String documentPath = "stores/"+ UserSingleton.getInstance().getUser().getStoreID(); // Change to your document path


        // Retrieve the existing store document
        DocumentReference storeDocRef = db.document(documentPath);
        DocumentSnapshot storeDocument = storeDocRef.get().get();

        // Check if the store document exists
        if (storeDocument.exists()) {
            // Extract the existing data as a map
            Map<String, Object> existingStoreData = storeDocument.getData();

            // Get the "tables" array from the store document or create it if it doesn't exist
            List<Map<String, Object>> tables = (List<Map<String, Object>>) existingStoreData.get("tables");
            if (tables != null && index >= 0 && index < tables.size()) {
                // Remove the table at the specified index
                tables.remove(index);

                // Update the Firestore document with the modified data
                existingStoreData.put("tables", tables);
                ApiFuture<WriteResult> future = storeDocRef.set(existingStoreData, SetOptions.merge());
                WriteResult writeResult = future.get();
                System.out.println("Table deleted from the store. Document updated at: " + writeResult.getUpdateTime());
            } else {
                System.out.println("Invalid table index or 'tables' array is empty.");
            }
        } else {
            System.out.println("Store document does not exist: " + documentPath);
        }
    }



    private static int getNextTableId(List<Map<String, Object>> tables) {
        // Find the maximum ID currently used in the "tables" array
        int maxId = 0;
        for (Map<String, Object> table : tables) {
            int tableId = ((Long) table.get("table_id")).intValue();
            if (tableId > maxId) {
                maxId = tableId;
            }
        }
        // Increment the maximum ID to generate a new unique ID
        return maxId + 1;
    }
}
