package com.eservice.model.repository;

import javafx.scene.layout.BorderPane;

public class MainFrame {
    private static MainFrame uniqInstance;

    // Private constructor to prevent direct instantiation

    private BorderPane mainFrame;
    public static MainFrame getInstance() {
        if (uniqInstance == null)
            uniqInstance = new MainFrame();
        return uniqInstance;
    }

    public BorderPane getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(BorderPane mainFrame) {
        this.mainFrame = mainFrame;
    }
}
