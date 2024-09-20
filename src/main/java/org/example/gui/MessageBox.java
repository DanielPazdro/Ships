package org.example.gui;

import javafx.scene.control.Alert;

public class MessageBox {
    private final String title;
    private final String message;

    public MessageBox(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public void show() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static MessageBox of(String title, String message) {
        return new MessageBox(title, message);
    }
}
