package lk.ijse.gdse72.layerdovinplus.controller;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashBoardController {
    @FXML
    private Button btnTute;

    @FXML
    private VBox btnPayment;

    @FXML
    private Button btnBatch;

    @FXML
    private Button btnContact;

    @FXML
    private Button btnDelivery;

    @FXML
    private Button btnEmployee;

    @FXML
    private Button btnOrder;

    @FXML
    private Button btnSetting;

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnUser;

    @FXML
    private Label lblDatenTime;

    @FXML
    private Label lblName;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane panelContact;

    @FXML
    private ImageView imageLogin;

    @FXML
    private ImageView imagepane;

    private Button previousButton = null;// To keep track of the previously clicked button
    private ObservableList<Button> buttons;

    // Method to reset button color and apply new color to clicked button
    @FXML
    void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), button);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(15);
            glow.setHeight(15);
            glow.setRadius(15);
            button.setEffect(glow);
        }

    }

    @FXML
    void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), button);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            button.setEffect(null);
        }

    }
    @FXML
    void changeButtonColor(ActionEvent event) {
        Button clickedButton = (Button) event.getSource(); // Get the button that was clicked

        // Reset color for all buttons to default
        resetButtonStyles();

        // Set new style for the clicked button
        clickedButton.setStyle("-fx-background-color:#70a1ff; -fx-border-color: #95afc0; -fx-border-width: 3px; -fx-border-radius: 50px; -fx-background-radius: 50px;");

        // Keep track of the currently clicked button
        previousButton = clickedButton;

    }

    // Method to reset all buttons to their default styles
    private void resetButtonStyles() {
        // Reset the previous button if it's not null
        if (previousButton != null) {
            previousButton.setStyle("-fx-background-color: #FFFFF; -fx-border-color: #95afc0; -fx-border-width: 3px; -fx-border-radius: 50px; -fx-background-radius: 50px;"); // Default color
        }
    }

    @FXML
    void BatchOnAction(ActionEvent event) {
        changeButtonColor(event);
        navigateTo("/view/Batch.fxml");

    }

    @FXML
    void contactOnaction(ActionEvent event) {
        changeButtonColor(event);
        navigateTo("/view/ContactView.fxml");
    }

    @FXML
    void deliveryOnAction(ActionEvent event) {
        changeButtonColor(event);
        navigateTo("/view/DelivaryView.fxml");


    }

    @FXML
    void employeeOnAction(ActionEvent event) {
        changeButtonColor(event);
        navigateTo("/view/Employee.fxml");

    }

    @FXML
    void orderOnAction(ActionEvent event) {
        changeButtonColor(event);
        navigateTo("/view/OrderView.fxml");


    }

    @FXML
    void settingOnAction(ActionEvent event) {
        changeButtonColor(event);
        navigateTo("/view/Setting.fxml");

    }
    @FXML
    void TuteOnAction(ActionEvent event) {
        changeButtonColor(event);
        navigateTo("/view/TuteView.fxml");

    }

    @FXML
    void sudentOnAction(ActionEvent event) {
        changeButtonColor(event);
        navigateTo("/view/StudentView.fxml");

    }

    @FXML
    void useOnAction(ActionEvent event) {
        changeButtonColor(event);
        navigateTo("/view/UserView.fxml");

    }
    @FXML
    void paymentOnAction(ActionEvent event) {
        changeButtonColor(event);
        navigateTo("/view/PaymentView.fxml");

    }
    public void navigateTo(String fxmlPath) {

        try {
            panelContact.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));


            load.prefWidthProperty().bind(panelContact.widthProperty());
            load.prefHeightProperty().bind(panelContact.heightProperty());

            panelContact.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    @FXML
    public void initialize() {
        // Set up a Timeline to update the date and time every second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Get the current date and time
            LocalDateTime now = LocalDateTime.now();

            // Format the date and time as desired
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            // Set the formatted date and time to the label
            lblDatenTime.setText(formattedDateTime);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start the timeline
        buttons = FXCollections.observableArrayList(btnBatch, btnContact, btnDelivery, btnEmployee, btnOrder, btnSetting, btnStudent, btnUser);


    }



    private void updateStatus(String message) {

        lblName.setText(message);
    }

    public void initData(String userName) {

        this.lblName.setText(userName);
    }

    @FXML
    void loginOnMouseClick(MouseEvent event) throws IOException {
       // changeButtonColor(event);
        playMouseEnterAnimation(event);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));

        Parent login = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(login);


    }



}
