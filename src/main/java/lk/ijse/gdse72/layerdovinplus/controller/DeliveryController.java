package lk.ijse.gdse72.layerdovinplus.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse72.layerdovinplus.bo.BOFactory;
import lk.ijse.gdse72.layerdovinplus.bo.custom.ConatctBO;
import lk.ijse.gdse72.layerdovinplus.bo.custom.DeliveryBO;
import lk.ijse.gdse72.layerdovinplus.dto.DelivaryDTO;
import lk.ijse.gdse72.layerdovinplus.dto.tm.DeliveryTM;
//import lk.ijse.gdse72.layerdovinplus.model.DeliveryModel;
//import lk.ijse.gdse72.layerdovinplus.model.OrderModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeliveryController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbOrderId;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn<DeliveryTM, Date> colDeliveryDate;

    @FXML
    private TableColumn<DeliveryTM, String> colDeliveyId;

    @FXML
    private TableColumn<DeliveryTM, String> colOrdeId;

    @FXML
    private TableColumn<DeliveryTM, String> colStatus;

    @FXML
    private Label lblDeliveyId;

    @FXML
    private TableView<DeliveryTM> tblDelivery;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtSearch;
    private ObservableList<DeliveryTM> deliveryTMS;
    //private final OrderModel orderModel = new OrderModel();

    DeliveryBO deliveryBO = (DeliveryBO) BOFactory.getInstance().getBo(BOFactory.BOType.DELIVARY);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colDeliveyId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOrdeId.setCellValueFactory(new PropertyValueFactory<>("orderId"));

       // cmbStatus.getItems().addAll("Pending", "In Transit", "Out for Delivery", "Delivered", "Failed", "Cancelled");

        cmbStatus.getItems().addAll(
                "Pending",
                "In Transit",
                "Out for Delivery",
                "Delivered",
                "Failed",
                "Cancelled"
        );
        try {
            refreshPage();
//            String nextCustomerID = customerModel.getNextCustomerID();
//            System.out.println(nextCustomerID);
//            lblCustomerId.setText(nextCustomerID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setupDeliverySearch();




    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextDeliveryId = deliveryBO.getNextDeliveryId();
        lblDeliveyId.setText(nextDeliveryId);

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = currentDate.format(formatter);

        txtDate.setText(formattedDate);

        cmbStatus.setValue(null);
        cmbOrderId.setValue(null);
        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        loradOrdeId();
    }

    private void refreshTable() throws SQLException {
        ArrayList<DelivaryDTO> delivaryDTOS = deliveryBO.getAllDelivery();
        ObservableList<DeliveryTM> deliveryTMS = FXCollections.observableArrayList();


        for (DelivaryDTO delivaryDTO:delivaryDTOS){
            DeliveryTM deliveryTM = new DeliveryTM(
                    delivaryDTO.getDeliveryId(),
                    delivaryDTO.getDeliveryDate(),
                    delivaryDTO.getStatus(),
                    delivaryDTO.getOrderId()

            );
            deliveryTMS.add(deliveryTM);
        }
        tblDelivery.setItems(deliveryTMS);
    }

    private void setupDeliverySearch() {
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
            ArrayList<DelivaryDTO> searchResults = deliveryBO.searchDelivery(searchTerm);
            deliveryTMS = FXCollections.observableArrayList();

            for (DelivaryDTO delivary : searchResults) {
                deliveryTMS.add(new DeliveryTM(delivary.getDeliveryId(), delivary.getDeliveryDate(), delivary.getStatus(),delivary.getOrderId()));
            }
            tblDelivery.setItems(deliveryTMS);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error searching for Delivery details!").show();
        }

    }
    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblDeliveyId.getText();
        String dateStr = txtDate.getText();
        Date date ;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate localDate = LocalDate.parse(dateStr, formatter);
            date = Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            txtDate.setStyle("-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Invalid date format. Please use yyyy/MM/dd.").show();
            return;
        }
        String status = String.valueOf (cmbStatus.getValue());
        String orderId = String.valueOf(cmbOrderId.getValue());


            DelivaryDTO delivaryDTO = new DelivaryDTO(id, date,status,orderId);

            boolean isSaved = deliveryBO.saveDelivery(delivaryDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "DeliveryDetail saved...!").show();
                // refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save DeliveryDetail...!").show();

        }
        refreshPage();


    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String deliveryId = lblDeliveyId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this DeliveryDetail?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES){

            boolean isDeleted = deliveryBO.deleteDelivery(deliveryId);

            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"DeliveryDetail deleted...!").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail to delete DeliveryDetail...!").show();
            }
        }


    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblDeliveyId.getText();
        Date date = Date.valueOf(txtDate.getText());
        String status = cmbStatus.getValue();
        String orId = cmbOrderId.getValue();



            DelivaryDTO delivaryDTO = new DelivaryDTO(id,date,status,orId);

            boolean isUpdate = deliveryBO.updateDelivery(delivaryDTO);

            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Delivery Detail updated...!").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Delivery Detail...!").show();
            }
    }

    private void loradOrdeId() throws SQLException {
        ArrayList<String> deliveryIds = deliveryBO.getAllOrderIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(deliveryIds);
        cmbOrderId.setItems(observableList);
    }


    @FXML
    void cmbOrderIdOnAction(ActionEvent event) throws SQLException {
        String selecteDeliveryId = cmbOrderId.getSelectionModel().getSelectedItem();
        DelivaryDTO delivaryDTO = deliveryBO.findById(selecteDeliveryId);

        if (delivaryDTO != null) {

        }

    }

    @FXML
    void cmbStatusOnAction(ActionEvent event) {
        String selectedStatus = cmbStatus.getSelectionModel().getSelectedItem();
        if (selectedStatus != null) {
        }

    }

    @FXML
    void onClickTable(MouseEvent event) {
        DeliveryTM selectedDelivery = tblDelivery.getSelectionModel().getSelectedItem();
        if (selectedDelivery != null){
            lblDeliveyId.setText(selectedDelivery.getDeliveryId());
            txtDate.setText(String.valueOf(selectedDelivery.getDeliveryDate()));
            //cmbStatus.setValue(String.valueOf(selectedDelivery.getStatus()));
            cmbOrderId.setValue(String.valueOf(selectedDelivery.getOrderId()));
            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);

        }

    }




}

