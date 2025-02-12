package lk.ijse.gdse72.layerdovinplus.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse72.layerdovinplus.bo.BOFactory;
import lk.ijse.gdse72.layerdovinplus.bo.custom.UserBO;
import lk.ijse.gdse72.layerdovinplus.db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private AnchorPane loginpage;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSingIn;

    @FXML
    private Label lblForgotPassword;

    @FXML
    private ImageView paneView;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;
    @FXML
    private Label lblSingUp;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBo(BOFactory.BOType.USER);

    @FXML
    void clickBack(ActionEvent event) {

    }
    private void loadNewScene(String fxmlPath, String title) {
        try {
            // Load the new FXML file
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));

            // Create a new scene with the loaded FXML content
            Scene scene = new Scene(root);

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) this.loginpage.getScene().getWindow();  // Assuming loginpage is a reference to your current scene's node
            stage.setScene(scene);

            // Center the stage on the screen
            stage.centerOnScreen();

            // Set the window title
            stage.setTitle(title);

            // Show the new stage (if it's not already visible)
            stage.show();
        } catch (IOException e) {
            // Handle the exception properly
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the form. Please try again.").show();
        }
    }

    @FXML
    void clickForgotPassword(MouseEvent event) {
        loadNewScene("/view/forgotPassword_form.fxml", "Get OTP Form");


    }
    @FXML
    void clickSingUp(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SingUpView.fxml"));

        Parent singUp = fxmlLoader.load();
        SingUpController singUpController = fxmlLoader.getController();
        loginpage.getChildren().clear();
        loginpage.getChildren().add(singUp);



    }
    private void navigateToTheDashboard() throws IOException {
        try {
            String username = txtUserName.getText();

            // Initialize FXMLLoader
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/DashBoard.fxml"));

            // Load the FXML file
            AnchorPane rootNode = fxmlLoader.load();

            // Get the controller and pass the data
            DashBoardController controller = fxmlLoader.getController();
            controller.initData(username);

            // Create the new scene
            Scene scene = new Scene(rootNode);

            // Get the current stage and set the scene
            Stage stage = (Stage) this.loginpage.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Dashboard Form");
        } catch (IOException e) {
            e.printStackTrace(); // Log the error or handle it as needed
        }
    }


    @FXML
    void singInOnAction(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if (userName.trim().isEmpty() || password.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "User ID, Password can't be empty").show();
            return;
        }
        try {
            checkCredential(userName, password);
          //  UserModel.userId = userName;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkCredential(String userId, String pw) throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT userId, userpassword FROM User WHERE userName = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString(2);

            if(dbPw.equals(pw)) {
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "user id not found!").show();
        }
    }
}


