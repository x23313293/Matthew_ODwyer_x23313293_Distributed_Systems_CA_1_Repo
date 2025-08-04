package com.yourorg.classroom.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class SmartBoardController {

    @FXML
    private TextArea boardDisplayArea;

    private final SmartBoardClient smartBoardClient;

    public SmartBoardController() {
        this.smartBoardClient = new SmartBoardClient();
    }

    @FXML
    private void initialize() {
        // Initialize any necessary data or settings
        fetchBoardData();
    }

    @FXML
    private void handleUpdateButtonAction() {
        try {
            String newContent = boardDisplayArea.getText();

            // Validate input
            if (newContent.isEmpty()) {
                showErrorDialog("Invalid Input", "Please enter valid content for the smartboard.");
                return;
            }

            // Update smartboard content
            smartBoardClient.updateBoardContent(newContent);

            // Show success message
            showConfirmationDialog("Content Updated", "The smartboard content has been successfully updated.");

        } catch (Exception e) {
            showErrorDialog("Error", "An error occurred while updating the smartboard content.");
        }
    }

    private void fetchBoardData() {
        try {
            String currentContent = smartBoardClient.fetchBoardContent();
            boardDisplayArea.setText(currentContent);
        } catch (Exception e) {
            showErrorDialog("Error", "An error occurred while fetching the smartboard content.");
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
