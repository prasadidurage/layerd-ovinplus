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
import lk.ijse.gdse72.layerdovinplus.dto.BatchDTO;
import lk.ijse.gdse72.layerdovinplus.dto.tm.BatchTM;
//import lk.ijse.gdse72.layerdovinplus.model.BatchModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BatchController implements Initializable {
    @FXML
    private TextField txtSearch;
    
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<BatchTM, String> colBatchId;

    @FXML
    private TableColumn<BatchTM, String> colBatchName;

    @FXML
    private TableColumn<BatchTM, Integer> colStudentCount;

    @FXML
    private Label lblBatchId;

    @FXML
    private TableView<BatchTM> tblBatch;

    @FXML
    private TextField txtBatchName;

    @FXML
    private TextField txtStudentCount;

    //    ConatctBO contactBO = (ConatctBO) BOFactory.getInstance().getBo(BOFactory.BOType.CONTACT);
    //BatchModel batchModel = new BatchModel();
    private ObservableList<BatchTM> batchTMS;
    BatchBO batchBO = (BatchBO) BOFactory.getInstance().getBo(BOFactory.BOType.BATCH);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colBatchName.setCellValueFactory(new PropertyValueFactory<>("batchName"));
        colStudentCount.setCellValueFactory(new PropertyValueFactory<>("studentCount"));

        try {
            refreshPage();
//            String nextCustomerID = customerModel.getNextCustomerID();
//            System.out.println(nextCustomerID);
//            lblCustomerId.setText(nextCustomerID);
        } catch (SQLException | ClassNotFoundException e) {
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
            ArrayList<BatchDTO> searchResults = batchBO.search(searchTerm);
            batchTMS = FXCollections.observableArrayList();

            for (BatchDTO batch : searchResults) {
                batchTMS.add(new BatchTM(batch.getBatchID(), batch.getBatchName(), batch.getStudentCount()));
            }
            tblBatch.setItems(batchTMS);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error searching for batch details!").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextBatchId = batchBO.getNextId();
        lblBatchId.setText(nextBatchId);

        txtBatchName.setText("");
        txtStudentCount.setText("");
        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<BatchDTO> batchDTOS = batchBO.getAll();
        ObservableList<BatchTM> batchTMS = FXCollections.observableArrayList();


        for (BatchDTO batchDTO:batchDTOS){
            BatchTM batchTM = new BatchTM(
                    batchDTO.getBatchID(),
                    batchDTO.getBatchName(),
                    batchDTO.getStudentCount()

            );
            batchTMS.add(batchTM);
        }
        tblBatch.setItems(batchTMS);
    }
    @FXML
    void btnBatchSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblBatchId.getText();
        String name = txtBatchName.getText();
        int student_count = Integer.parseInt(txtStudentCount.getText());


        String namePattern = "^[a-zA-Z0-9 ]+$";

        boolean isValidName = name.matches(namePattern);

        txtBatchName.setStyle(txtBatchName.getStyle()+";-fx-border-color: #7367F0;");


        if (!isValidName){
            txtBatchName.setStyle(txtBatchName.getStyle()+";-fx-border-color: red;");
        }



        if (isValidName ){
            BatchDTO batchDTO = new BatchDTO(id, name,student_count);

            boolean isSaved = batchBO.save(batchDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Batch saved...!").show();
               refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Batch...!").show();

            }
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Batch?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES){

            boolean isDeleted = batchBO.delete(batchId);

            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Batch deleted...!").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail to delete Student...!").show();
            }
        }


    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblBatchId.getText();
        String name = txtBatchName.getText();
        String countString = txtStudentCount.getText();


        String namePattern = "^[a-zA-Z0-9 ]+$";
        String studentCountPattern = "^[0-9]+$";

//        Pattern compile = Pattern.compile(namePattern);
//        System.out.println(compile.matcher(name).matches());

        boolean isValidName = name.matches(namePattern);
        boolean isValidStudentCount = countString.matches(studentCountPattern);

        txtBatchName.setStyle(txtBatchName.getStyle()+";-fx-border-color: #7367F0;");
        txtStudentCount.setStyle(txtBatchName.getStyle()+";-fx-border-color: #7367F0;");

        if (!isValidName){
            txtBatchName.setStyle(txtBatchName.getStyle()+";-fx-border-color: red;");
            // textField.setStyle("-fx-border-color: red; -fx-border-width: 1");
        }

        if (!isValidStudentCount){
            txtStudentCount.setStyle(txtStudentCount.getStyle()+";-fx-border-color: red;");
        }


        if (isValidName && isValidStudentCount ){
            int count = Integer.parseInt(countString);
            BatchDTO batchDTO = new BatchDTO(id,name,count);

            boolean isUpdate = batchBO.update(batchDTO);




            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Batch updated...!").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Batch...!").show();
            }
        }


    }

    @FXML
    void onClickTable(MouseEvent event) {
        BatchTM selectedItem = tblBatch.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            lblBatchId.setText(selectedItem.getBatchId());
            txtBatchName.setText(selectedItem.getBatchName());
            txtStudentCount.setText(String.valueOf(selectedItem.getStudentCount()));

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);

        }
    }



}



