package com.eservice;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThreadTesting extends Application {

    private MyBackgroundService service;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        Button startButton = new Button("Start Background Task");
        Button stopButton = new Button("Stop Background Task");

        service = new MyBackgroundService();
        service.setOnSucceeded(event -> {
            // Handle task completion if needed
        });

        startButton.setOnAction(event -> service.start());
        stopButton.setOnAction(event -> service.stopTask());

        root.getChildren().addAll(startButton, stopButton);

        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class MyBackgroundService extends Service<Void> {
        private volatile boolean isRunning = true;

        public void stopTask() {
            isRunning = false;
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    while (isRunning) {
                        // Simulate some background work
                        Platform.runLater(() -> {
                            // Update UI components here
                        });
                    }
                    return null; // Not used
                }
            };
        }
    }
}