package lk.ijse.gdse72.layerdovinplus.controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse72.layerdovinplus.bo.BOFactory;
import lk.ijse.gdse72.layerdovinplus.bo.custom.UserBO;
import lk.ijse.gdse72.layerdovinplus.dto.UserDTO;
import lk.ijse.gdse72.layerdovinplus.dto.tm.UserTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SingUpController {

    @FXML
    private Button btnGetLogin;

    @FXML
    private Button btnSingup;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtCreatePassword;

    @FXML
    private TextField txtEmailAddress;

    @FXML
    private Label lblUserId;

    @FXML
    private TextField txtUserName;

    @FXML
    private AnchorPane singupPane;

    @FXML
    private Label lblMassage;


    UserBO userBO = (UserBO) BOFactory.getInstance().getBo(BOFactory.BOType.USER);

    private ObservableList<UserTM> userTMS;
    private List<UserDTO> userList = new ArrayList<>();

    private void removeError(TextField textfield) {
        textfield.setStyle("-fx-border-bottom-color: black; -fx-border-bottom-width: 1;");
    }

    private void addError(TextField textfield) {
        textfield.setStyle("--fx-border-bottom-color: red; -fx-border-bottom-width: 1");
    }

    @FXML
    void confirmPasswordOnReleseAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^^[A-Za-z\\d]{6}$");
        if (!idPattern.matcher(txtConfirmPassword.getText()).matches()) {
            addError(txtConfirmPassword);

        }else{
            removeError(txtConfirmPassword);
        }

    }

    @FXML
    void emailOnReleseAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("(^[a-zA-Z0-9_.]+[@]{1}[a-z0-9]+[\\.][a-z]+$)");
        if (!idPattern.matcher(txtEmailAddress.getText()).matches()) {
            addError( txtEmailAddress);

        }else{
            removeError( txtEmailAddress);
        }


    }
    @FXML
    void passwordOnReleseAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^^[A-Za-z\\d]{6}$");
        if (!idPattern.matcher(txtCreatePassword.getText()).matches()) {
            addError(txtCreatePassword);

        }else{
            removeError(txtCreatePassword);
        }

    }

    @FXML
    void useNameOnreleseAction(KeyEvent event) {
        Pattern idPattern = Pattern.compile("^[a-zA-Z ]*$");
        if (!idPattern.matcher(txtUserName.getText()).matches()) {
            addError(txtUserName);

        } else {
            removeError(txtUserName);
        }
    }
    @FXML
    void SingupOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        // Get the next user ID from the model
        String nextUserId = userBO.getNextId();
        // Set the ID on the label to display it to the user
        lblUserId.setText(nextUserId);
        String id = lblUserId.getText();
        String name = txtUserName.getText();
        String email = txtEmailAddress.getText();
        String password = txtCreatePassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        UserDTO userDTO = new UserDTO(id, name, password,confirmPassword,email);
        boolean isAdded = userBO.save(userDTO);
        if (isAdded) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            userList.add(userDTO);
            Stage stage1 = (Stage)btnGetLogin.getScene().getWindow();
            stage1.close();
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/LoginView.fxml"));

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setTitle("Sign up Form");

            stage.show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }



    @FXML
    void loginOmAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));

        Parent login = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        singupPane.getChildren().clear();
        singupPane.getChildren().add(login);

    }


}
