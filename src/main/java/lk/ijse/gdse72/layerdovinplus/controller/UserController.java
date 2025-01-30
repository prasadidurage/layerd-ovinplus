package lk.ijse.gdse72.layerdovinplus.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.layerdovinplus.dto.UserDTO;
import lk.ijse.gdse72.layerdovinplus.dto.tm.UserTM;
import lk.ijse.gdse72.layerdovinplus.model.UserModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UserController implements Initializable {
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<UserTM, String> colConfirmPassword;

    @FXML
    private TableColumn<UserTM, String> colEmail;

    @FXML
    private TableColumn<UserTM, String> colUserId;

    @FXML
    private TableColumn<UserTM, String> colUserName;

    @FXML
    private TableColumn<UserTM, String> colpassword;

    @FXML
    private Label lblUserId;

    @FXML
    private AnchorPane paneUser;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TextField txtUserName;


    @FXML
    private TextField txtComfirmPassword;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtemail;

    private Button previousButton = null;

    UserModel userModel = new UserModel();
    private ObservableList<UserTM> userTMS;

    @FXML
    void changeButtonColor(ActionEvent event) {
        Button clickedButton = (Button) event.getSource(); // Get the button that was clicked

        // Reset color for all buttons to default
        resetButtonStyles();

        // Set new style for the clicked button
        clickedButton.setStyle("-fx-background-color:#ADD8E6; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 50px; -fx-background-radius: 50px;");

        // Keep track of the currently clicked button
        previousButton = clickedButton;

    }

    // Method to reset all buttons to their default styles
    private void resetButtonStyles() {
        // Reset the previous button if it's not null
        if (previousButton != null) {
            previousButton.setStyle("-fx-background-color:  #b2bec3; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 50px; -fx-background-radius: 50px;"); // Default color
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colConfirmPassword.setCellValueFactory(new PropertyValueFactory<>("confirmPassword"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));


        try {
            refreshPage();
//            String nextCustomerID = customerModel.getNextCustomerID();
//            System.out.println(nextCustomerID);
//            lblCustomerId.setText(nextCustomerID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupBatchSearch();
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextUserId = userModel.getNextUserId();
        lblUserId.setText(nextUserId);

        txtUserName.setText("");
        txtPassword.setText("");
        txtComfirmPassword.setText("");
        txtemail.setText("");
        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<UserDTO> userDTOS = userModel.getAllUser();
        ObservableList<UserTM> userTMS = FXCollections.observableArrayList();


        for (UserDTO userDTO:userDTOS){
            UserTM userTM = new UserTM(
                    userDTO.getUserId(),
                    userDTO.getUserName(),
                    userDTO.getPassword(),
                    userDTO.getConfirmPassword(),
                    userDTO.getEmail()

            );
            userTMS.add(userTM);
        }
        tblUser.setItems(userTMS);
    }

    private void setupBatchSearch() {
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                searchBatchDetails(newValue);
            } else {
                try {
                    refreshTable(); // Show all batches if search is cleared
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void searchBatchDetails(String searchTerm) {
        try {
            ArrayList<UserDTO> searchResults = userModel.searchUser(searchTerm);
            userTMS = FXCollections.observableArrayList();

            for (UserDTO user : searchResults) {
                userTMS.add(new UserTM(user.getUserId(), user.getUserName(), user.getPassword(),user.getConfirmPassword(),user.getEmail()));
            }
            tblUser.setItems(userTMS);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error searching for batch details!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String userId = lblUserId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this User?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES){

            boolean isDeleted = userModel.deleteUser(userId);

            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"User deleted...!").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail to delete User...!").show();
            }
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        changeButtonColor(event);
        refreshPage();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String id = lblUserId.getText();
        String name = txtUserName.getText();
        String pwd = txtPassword.getText();
        String confirmPwd = txtComfirmPassword.getText();
        String email = txtemail.getText();

// Validate that password and confirm password match
        if (!pwd.equals(confirmPwd)) {
            addError(txtComfirmPassword);
            txtComfirmPassword.clear();
            txtComfirmPassword.setPromptText("Passwords do not match!");
            txtComfirmPassword.setStyle("-fx-prompt-text-fill: red;");
            return; // Exit the method to prevent updating the user
        }

// Create UserDTO object
        UserDTO userDTO = new UserDTO(id, name, pwd, confirmPwd, email);

// Update the user
        boolean isUpdate = userModel.updateUser(userDTO);

        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "User updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update user!").show();
        }

    }

    @FXML
    void onClickTable(MouseEvent event) {
        UserTM selectedItem = tblUser.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            lblUserId.setText(selectedItem.getUserId());
            txtUserName.setText(selectedItem.getUserName());
            txtPassword.setText(selectedItem.getPassword());
            txtComfirmPassword.setText(selectedItem.getConfirmPassword());
            txtemail.setText(selectedItem.getEmail());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);

        }


    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException {
        String id = lblUserId.getText();
        String name = txtUserName.getText();
        String pwd = txtPassword.getText();
        String confirmPwd = txtComfirmPassword.getText();
        String email = txtemail.getText();

// Validate that password and confirm password match
        if (!pwd.equals(confirmPwd)) {
            addError(txtComfirmPassword);
            txtComfirmPassword.clear();
            txtComfirmPassword.setPromptText("Passwords do not match!");
            txtComfirmPassword.setStyle("-fx-prompt-text-fill: red;");
            return;
        }

// Create UserDTO object
        UserDTO userDTO = new UserDTO(id, name, pwd, confirmPwd, email);

// Save the user
        boolean isSaved = userModel.saveUser(userDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "User saved successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save user!").show();
        }
    }


    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color:black; -fx-border-width: 1");
    }

    private void addError(TextField textField) {
    //txtBatchName.getStyle()+";-fx-border-color: red;"
        textField.setStyle("-fx-border-color: red; -fx-border-width: 1");
    }


    @FXML
    void txtAdminConfirmpasswordOnreleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^^[A-Za-z\\d]{6}$");
        if (!idPattern.matcher(txtComfirmPassword.getText()).matches()) {
            addError(txtComfirmPassword);

        }else{
            removeError(txtComfirmPassword);
        }
    }

    @FXML
    void txtAdminpasswordOnreleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^^[A-Za-z\\d]{6}$");
        if (!idPattern.matcher(txtPassword.getText()).matches()) {
            addError(txtPassword);

        }else{
            removeError(txtPassword);
        }

    }

    @FXML
    void txtUsernameOnreleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[A-z|\\\\s]{3,}$");
        if (!idPattern.matcher(txtUserName.getText()).matches()) {
            addError(txtUserName);

        }else{
            removeError(txtUserName);
        }

    }

    @FXML
    void txtemailOnreleasedOnAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("(^[a-zA-Z0-9_.]+[@]{1}[a-z0-9]+[\\.][a-z]+$)");
        if (!idPattern.matcher(txtemail.getText()).matches()) {
            addError(txtemail);

        }else{
            removeError(txtemail);
        }

    }


}
