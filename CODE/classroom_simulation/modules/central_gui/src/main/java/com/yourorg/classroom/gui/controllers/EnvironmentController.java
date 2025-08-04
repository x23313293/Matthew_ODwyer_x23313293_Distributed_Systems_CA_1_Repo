package com.yourorg.classroom.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EnvironmentController {

    @FXML
    private TextField temperatureField;

    @FXML
    private TextField humidityField;

    private final EnvironmentControlClient environmentControlClient;

    public EnvironmentController() {
        this.environmentControlClient = new EnvironmentControlClient();
    }

    @FXML
    private void initialize() {
        // Initialize any necessary data or settings
    }

    @FXML
    private void handleSaveButtonAction() {
        try {
            String temperature = temperatureField.getText();
            String humidity = humidityField.getText();

            // Validate input
            if (temperature.isEmpty() || humidity.isEmpty()) {
                showErrorDialog("Invalid Input", "Please enter valid values for temperature and humidity.");
                return;
            }

            // Update environment settings
            environmentControlClient.updateEnvironmentSettings(temperature, humidity);

            // Show success message
            showConfirmationDialog("Settings Updated", "The environment settings have been successfully updated.");

        } catch (Exception e) {
            showErrorDialog("Error", "An error occurred while updating the environment settings.");
        }
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
