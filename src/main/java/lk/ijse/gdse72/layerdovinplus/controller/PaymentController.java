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
import lk.ijse.gdse72.layerdovinplus.bo.custom.BatchBO;
import lk.ijse.gdse72.layerdovinplus.bo.custom.PaymentBO;
import lk.ijse.gdse72.layerdovinplus.dto.DelivaryDTO;
import lk.ijse.gdse72.layerdovinplus.dto.PaymentDTO;
import lk.ijse.gdse72.layerdovinplus.dto.tm.PaymrntTM;
//import lk.ijse.gdse72.ovinplus.model.DeliveryModel;
//import lk.ijse.gdse72.ovinplus.model.PaymentModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbDeliveryId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDeliveryId;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblPaymentId;

    @FXML
    private TableView<PaymrntTM> tblBatch;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtSearch;

   // PaymentModel paymentModel = new PaymentModel();
    private ObservableList<PaymrntTM> paymrntTMS;
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBo(BOFactory.BOType.PAYMENT);

    //  private final DeliveryModel deliveryModel = new DeliveryModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colDeliveryId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));


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
        setupBatchSearch();

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
            ArrayList<PaymentDTO> searchResults = paymentBO.searchPayment(searchTerm);
            paymrntTMS = FXCollections.observableArrayList();

            for (PaymentDTO paymentDTO : searchResults) {
                paymrntTMS.add(new PaymrntTM(paymentDTO.getPaymentId(),
                        paymentDTO.getDeliveryId(),
                        paymentDTO.getPaymentDate(),
                        paymentDTO.getAmount())
                );
            }
            tblBatch.setItems(paymrntTMS);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error searching for Payment details!").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextPaymentId = paymentBO.getNextPaymentId();
        lblPaymentId.setText(nextPaymentId);

        txtAmount.setText("");
        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        loradDeliveryId();
    }

    private void refreshTable() throws SQLException {
        ArrayList<PaymentDTO> paymentDTOS = paymentBO.getAllPayment();
        ObservableList<PaymrntTM> paymrntTMS = FXCollections.observableArrayList();


        for (PaymentDTO paymentDTO:paymentDTOS){
            PaymrntTM paymrntTM = new PaymrntTM(
                    paymentDTO.getPaymentId(),
                    paymentDTO.getDeliveryId(),
                    paymentDTO.getPaymentDate(),
                    paymentDTO.getAmount()
            );
            paymrntTMS.add(paymrntTM);
        }
        tblBatch.setItems(paymrntTMS);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String paymentId = lblPaymentId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Payment Detail?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = paymentBO.deletePayment(paymentId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Batch deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Student...!").show();
            }
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblPaymentId.getText();
        String deliveryId = String.valueOf(cmbDeliveryId.getValue());
        Date date = Date.valueOf(datePicker.getValue());
        double amount ;
        try {
            amount = Double.parseDouble(txtAmount.getText()); // Parse amount
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Invalid amount entered! Please enter a numeric value.").show();
            return;
        }


            PaymentDTO paymentDTO = new PaymentDTO(id, deliveryId,date,amount);

            boolean isSaved = paymentBO.savePayment(paymentDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Payment detail saved...!").show();
                 refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Payment detail...!").show();
            }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblPaymentId.getText();
        String deliveryId = String.valueOf(cmbDeliveryId.getValue());
        Date date = Date.valueOf(datePicker.getValue());
        double amount = Double.parseDouble(txtAmount.getText());



            PaymentDTO paymentDTO= new PaymentDTO(id,deliveryId,date,amount);

            boolean isUpdate = paymentBO.updatePayment(paymentDTO);




            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Payment detail updated...!").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Payment detail...!").show();
            }



    }

    @FXML
    void datePickerOnAction(ActionEvent event) {
      
    }

    @FXML
    void onClickTable(MouseEvent event) {
        PaymrntTM selectedItem = tblBatch.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblPaymentId.setText(selectedItem.getPaymentId());
            cmbDeliveryId.setValue(selectedItem.getDeliveryId());
            datePicker.setValue(selectedItem.getPaymentDate().toLocalDate()); // Use setValue() for DatePicker
            txtAmount.setText(String.valueOf(selectedItem.getAmount()));
            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
    private void loradDeliveryId() throws SQLException {
        ArrayList<String> deliveryIds = paymentBO.getAllDeliveryIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(deliveryIds);
        cmbDeliveryId.setItems(observableList);
    }

    @FXML
    void selectDeliveryIdOnAction(ActionEvent event) throws SQLException {
        String selecteId = cmbDeliveryId.getSelectionModel().getSelectedItem();
        DelivaryDTO paymentDTO= paymentBO.findById(selecteId);




    }
}
