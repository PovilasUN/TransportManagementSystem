package com.tms.transportmanagementsystem.utils;

import javafx.scene.control.Alert;

public class FXUtils {
    public static void generateAlert(Alert.AlertType alertType, String header, String contentText){
        Alert alert = new Alert(alertType);
        alert.setTitle("Transport Management System");
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
