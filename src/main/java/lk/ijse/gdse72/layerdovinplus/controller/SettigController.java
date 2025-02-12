package lk.ijse.gdse72.layerdovinplus.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;


public class SettigController {

    @FXML
    private ToggleButton btnDrakMode;
    private boolean isDarkMode = false;

    @FXML
    void DarkmodeOnAction(ActionEvent event) {
        // Get the current scene
        Scene scene = btnDrakMode.getScene();

        // Check if the dark mode is selected
        if (btnDrakMode.isSelected()) {
            // Apply the dark theme CSS file
            scene.getStylesheets().add(getClass().getResource("/style/dash.css").toExternalForm());
            isDarkMode = true;  // Update the state
            btnDrakMode.setText("Dark Mode: ON");
        } else {
            // Remove the dark theme CSS file
            scene.getStylesheets().remove(getClass().getResource("/style/darmode.css").toExternalForm());
            isDarkMode = false;  // Update the state
            btnDrakMode.setText("Dark Mode: OFF");
        }
    }
}

