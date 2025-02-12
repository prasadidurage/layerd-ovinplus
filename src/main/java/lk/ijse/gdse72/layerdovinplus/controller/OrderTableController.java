package lk.ijse.gdse72.layerdovinplus.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.layerdovinplus.bo.BOFactory;
import lk.ijse.gdse72.layerdovinplus.bo.custom.OrderBO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDTO;
import lk.ijse.gdse72.layerdovinplus.dto.tm.OrderTM;
//import lk.ijse.gdse72.ovinplus.model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class OrderTableController implements Initializable {



    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<OrderTM> tblOrders;

    @FXML
    private TableColumn<OrderTM, String> colOrderId;

    @FXML
    private TableColumn<OrderTM, Date> colOrderDate;

    @FXML
    private TableColumn<OrderTM, String> colStudentId;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnBack;
    OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBo(BOFactory.BOType.ORDER);

    private ObservableList<OrderTM> orderTMS;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up table columns
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
    System.out.println("xx");
        // Load orders into the table
        try {
            loadTableData();
        } catch (SQLException e) {
            showError("Failed to load data", e.getMessage());
        }

        // Add a listener to search orders
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                searchOrders(newValue);
            } catch (SQLException e) {
                showError("Search Error", e.getMessage());
            }
        });
    }

    private void loadTableData() throws SQLException {
        ArrayList<OrderDTO> orderDTOS = orderBO.getOrders();
        orderTMS = FXCollections.observableArrayList();

        for (OrderDTO orderDTO : orderDTOS) {
            OrderTM orderTM = new OrderTM(
                    orderDTO.getOrderId(),
                    orderDTO.getOrderDate(),
                    orderDTO.getStudentId()
            );
            orderTMS.add(orderTM);
        }
        tblOrders.setItems(orderTMS);
    }

    private void searchOrders(String searchText) throws SQLException {
        if (searchText.isEmpty()) {
            loadTableData();
            return;
        }

        // Fetch matching orders
        ArrayList<OrderDTO> matchingOrders = orderBO.searchOrders(searchText);
        ObservableList<OrderTM> filteredOrderTMs = FXCollections.observableArrayList();

        for (OrderDTO orderDTO : matchingOrders) {
            OrderTM orderTM = new OrderTM(
                    orderDTO.getOrderId(),
                    orderDTO.getOrderDate(),
                    orderDTO.getStudentId()
            );
            filteredOrderTMs.add(orderTM);
        }
        tblOrders.setItems(filteredOrderTMs);
    }

    @FXML
    void onClickTable(MouseEvent event) {
        OrderTM selectedItem = tblOrders.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            System.out.println("Selected Order ID: " + selectedItem.getOrderId());
        }
    }

    @FXML
    void onBackAction(ActionEvent event) {
        System.out.println("Back button clicked.");
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void BackOnAction(ActionEvent actionEvent) {

        try {
            pane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/OrderView.fxml"));


            load.prefWidthProperty().bind(pane.widthProperty());
            load.prefHeightProperty().bind(pane.heightProperty());

            pane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }
}
