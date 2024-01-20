package com.eservice.model.service;

import com.eservice.model.IUser;
import com.eservice.model.repository.UserSingleton;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FireBaseService {

    public static void init() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("eserve-hmu-firebase-adminsdk-3gup6-5cb7912973.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
    }


    public static String loginClient(String email, String password) throws Exception {
            // URL for the POST request
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBFqDfbD1-XMV_RzS-mlAS6o1BoLWL510s");

            // Create a HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set up the request
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Create the JSON payload using Gson
            JsonObject payload = new JsonObject();
            payload.addProperty("email", email);
            payload.addProperty("password", password);
            payload.addProperty("returnSecureToken", true);

            // Write the payload to the request
            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.writeBytes(payload.toString());
                outputStream.flush();
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            JsonObject jsonResponse = null;
            if (responseCode == 200) {
                // Read the response
                StringBuilder response = new StringBuilder();
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                }

                // Parse the JSON response using Gson's JsonParser
                JsonParser jsonParser = new JsonParser();
                jsonResponse = jsonParser.parse(response.toString()).getAsJsonObject();

                // Handle the JSON response as needed
                System.out.println("Response JSON: " + jsonResponse.toString());

            }
            // Close the connection
            connection.disconnect();
            return jsonResponse.get("localId").getAsString();
    }


    public static String registerClient(String email, String password, String firstName, String lastName, String storeId) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            FirebaseAuth auth = FirebaseAuth.getInstance();

            // Create a new client user
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password);

            UserRecord userRecord = auth.createUser(request);

            // Add user data to Firestore
            DocumentReference clientRef = db.collection("clients").document(userRecord.getUid());

            Map<String, Object> clientData = new HashMap<>();
            clientData.put("username", email);
            clientData.put("name", firstName);
            clientData.put("last_name", lastName);
            clientData.put("store_id", storeId);

            clientRef.set(clientData).get();
            System.out.println("Successfully registered client: " + userRecord.getUid());
            return userRecord.getUid();
        } catch (ExecutionException | FirebaseAuthException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void getUser(String userID) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        IUser user= UserSingleton.getInstance().getUser();
        String collectionName = "clients"; // Change to your collection name
        String name = null;
        String lastName=null;
        String username=null;
        String storeID=null;
        // Build the reference to the document with the specified user ID
        DocumentReference docRef = db.collection(collectionName).document(userID);

        // Retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            // Document exists, you can access its data
            Map<String, Object> userData = document.getData();
            name=userData.get("name").toString();
            lastName=userData.get("last_name").toString();
            storeID=userData.get("store_id").toString();
            username=userData.get("username").toString();

            user.setId(userID);
            user.setName(name);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setStoreID(storeID);
        } else {
            System.out.println("Document with userID " + userID + " does not exist.");
        }
    }

    public static void updateUser(String userName,String userLast) throws ExecutionException, InterruptedException {
        // Initialize Firestore
        Firestore db = FirestoreClient.getFirestore();

        // Define the collection where the user documents are stored
        String collectionName = "clients"; // Change to your collection name

        // Define the user ID of the document you want to update
        String userIDToUpdate = UserSingleton.getInstance().getUser().getId(); // Change to the desired user ID

        // Retrieve the user document by ID
        DocumentReference userDocRef = db.collection(collectionName).document(userIDToUpdate);
        DocumentSnapshot userDocument = userDocRef.get().get();

        // Check if the user document exists
        if (userDocument.exists()) {
            // Create a map with the updated field values
            Map<String, Object> updates = new HashMap<>();
            updates.put("name", userName); // Change to the new name value
            updates.put("last_name", userLast); // Change to the new last name value

            // Update the Firestore document with the modified data
            ApiFuture<WriteResult> future = userDocRef.update(updates);
            WriteResult writeResult = future.get();
            System.out.println("User document updated at: " + writeResult.getUpdateTime());
            UserSingleton.getInstance().getUser().setName(userName);
            UserSingleton.getInstance().getUser().setLastName(userLast);
        } else {
            System.out.println("User document with ID " + userIDToUpdate + " does not exist.");
        }
    }
}
