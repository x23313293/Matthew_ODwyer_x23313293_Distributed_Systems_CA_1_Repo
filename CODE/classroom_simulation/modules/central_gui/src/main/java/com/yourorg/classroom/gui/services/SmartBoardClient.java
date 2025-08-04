package com.yourorg.classroom.gui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class SmartBoardClient {

    private static final String BASE_URL = "http://localhost:8080/api/smartboard";
    private final RestTemplate restTemplate;
    private final Stage primaryStage;

    public SmartBoardClient(Stage primaryStage) {
        this.restTemplate = new RestTemplate();
        this.primaryStage = primaryStage;
    }

    public void fetchBoardData() {
        try {
            String url = BASE_URL + "/data";
            String response = restTemplate.getForObject(url, String.class);
            Platform.runLater(() -> {
                // Update the UI with the fetched data
                // For example, display the data in a TextArea or Label
            });
        } catch (Exception e) {
            showErrorDialog("Failed to fetch smartboard data", e.getMessage());
        }
    }

    public void updateBoardSettings(String settings) {
        try {
            String url = BASE_URL + "/settings";
            restTemplate.postForObject(url, settings, String.class);
            Platform.runLater(() -> {
                // Update the UI to reflect the changes
                // For example, show a success message
            });
        } catch (Exception e) {
            showErrorDialog("Failed to update smartboard settings", e.getMessage());
        }
    }

    private void showErrorDialog(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);

            alert.showAndWait();
        });
    }

    public void showConfirmationDialog(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText(message);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Handle the confirmation action
            }
        });
    }
}
