package com.yourorg.classroom.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set the title of the main window
        primaryStage.setTitle("Classroom Simulation");

        // Create a simple label to display in the center of the window
        Label label = new Label("Welcome to the Classroom Simulation!");

        // Set up the layout and add the label
        StackPane root = new StackPane();
        root.getChildren().add(label);

        // Create a scene with the specified layout and dimensions
        Scene scene = new Scene(root, 800, 600);

        // Set the scene for the primary stage (main window)
        primaryStage.setScene(scene);

        // Display the window
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
