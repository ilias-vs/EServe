open module com.eservice {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires com.google.gson;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires google.cloud.firestore;
    requires com.google.api.apicommon;
    requires com.google.api.client;
    requires google.cloud.storage;
    requires google.cloud.core;

    exports com.eservice;
    exports com.eservice.model;
    exports com.eservice.controllers.home;
    exports com.eservice.controllers.menu;
    exports com.eservice.controllers.orders;
    exports com.eservice.controllers.tables;
    exports com.eservice.controllers.menu.ButtonController;
}