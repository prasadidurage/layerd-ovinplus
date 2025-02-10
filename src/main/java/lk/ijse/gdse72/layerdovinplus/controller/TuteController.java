package lk.ijse.gdse72.layerdovinplus.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.layerdovinplus.bo.BOFactory;
import lk.ijse.gdse72.layerdovinplus.bo.custom.StudentBO;
import lk.ijse.gdse72.layerdovinplus.bo.custom.TuteBO;
import lk.ijse.gdse72.layerdovinplus.db.DBConnection;
import lk.ijse.gdse72.layerdovinplus.dto.TuteDTO;
import lk.ijse.gdse72.layerdovinplus.dto.tm.TuteTM;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.view.JasperViewer;


import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class TuteController implements Initializable {

    @FXML
    private ImageView iconReport;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TuteTM, Double> colPrice;

    @FXML
    private TableColumn<TuteTM, Integer> colQty;

    @FXML
    private TableColumn<TuteTM, String> colTuteId;

    @FXML
    private TableColumn<TuteTM, String> colTuteName;

    @FXML
    private Label lblTuteId;

    @FXML
    private AnchorPane paneStudent;

    @FXML
    private TableView<TuteTM> tblTute;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtTuteName;

    TuteBO tuteBO = (TuteBO) BOFactory.getInstance().getBo(BOFactory.BOType.TUTE);

    private ObservableList<TuteTM> tuteTMS;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTuteId.setCellValueFactory(new PropertyValueFactory<>("tuteId"));
        colTuteName.setCellValueFactory(new PropertyValueFactory<>("tuteName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("tuteQty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("tutePrice"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setupTuteSearch();

    }

    private void setupTuteSearch() {
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                searchTuteDetails(newValue);
            } else {
                try {
                    refreshTable(); // Show all batches if search is cleared
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    private void searchTuteDetails(String searchTerm) {
        try {
            ArrayList<TuteDTO> searchResults = tuteBO.searchTute(searchTerm);
            tuteTMS = FXCollections.observableArrayList();

            for (TuteDTO tute : searchResults) {
                tuteTMS.add(new TuteTM(tute.getTuteId(), tute.getTuteName(), tute.getTuteQty(),tute.getTutePrice()));
            }
            tblTute.setItems(tuteTMS);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error searching for batch details!").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextTuteId = tuteBO.getNextTuteId();
        lblTuteId.setText(nextTuteId);

        txtTuteName.setText("");
        txtQty.setText("");
        txtPrice.setText("");
        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

    }
    private void refreshTable() throws SQLException {
        ArrayList<TuteDTO> tuteDTOS = tuteBO.getAllTute();
        ObservableList<TuteTM> tuteTMS = FXCollections.observableArrayList();


        for (TuteDTO tuteDTO:tuteDTOS){
            TuteTM tuteTM = new TuteTM(
                    tuteDTO.getTuteId(),
                    tuteDTO.getTuteName(),
                    tuteDTO.getTuteQty(),
                    tuteDTO.getTutePrice()
            );
            tuteTMS.add(tuteTM);
        }
        tblTute.setItems(tuteTMS);


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String tuteId = lblTuteId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Table?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES){

            boolean isDeleted = tuteBO.deleteTute(tuteId);

            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Tute deleted...!").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail to delete Tute...!").show();
            }
        }


    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblTuteId.getText();
        String name = txtTuteName.getText();
        String quantityString = txtQty.getText();
        String priceString = txtPrice.getText();

        String quantityPattern = "^[0-9]+$";
        String pricePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name != null;
        boolean isValidQuantity = quantityString.matches(quantityPattern);
        boolean isValidPrice = priceString.matches(pricePattern);

        resetStyles();

        if (!isValidName) {
            txtTuteName.setStyle(txtTuteName.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidQuantity) {
            txtQty.setStyle(txtQty.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + "; -fx-border-color: red;");
        }

        if (isValidName && isValidQuantity && isValidPrice) {
            resetStyles();

            int quantity = Integer.parseInt(quantityString);
            double price = Double.parseDouble(priceString);

            TuteDTO tuteDTO = new TuteDTO(id, name,quantity,price);

           boolean isSaved = tuteBO.updateTute(tuteDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Tute updated successfully!").show();
                refreshPage();
            } else {
              new Alert(Alert.AlertType.ERROR, "Fail to update Tute!").show();
            }
        }

    }

    @FXML
    void onClickTable(MouseEvent event) {
        TuteTM tuteTM = tblTute.getSelectionModel().getSelectedItem();
        if (tuteTM != null) {
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);

            lblTuteId.setText(tuteTM.getTuteId());
            txtTuteName.setText(tuteTM.getTuteName());
            txtQty.setText(String.valueOf(tuteTM.getTuteQty()
            ));
            txtPrice.setText(String.valueOf(tuteTM.getTutePrice()));
        }

    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblTuteId.getText();
        String name = txtTuteName.getText();
        String quantityString = txtQty.getText();
        String priceString = txtPrice.getText();


        String namePattern = "^[a-zA-Z0-9 ]+$";
        String quantityPattern = "^[0-9]+$";
        String pricePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidQuantity = quantityString.matches(quantityPattern);
        boolean isValidPrice = priceString.matches(pricePattern);

        System.out.println(isValidQuantity + " / " + quantityString);
        resetStyles();

        if (!isValidName) {
            txtTuteName.setStyle(txtTuteName.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidQuantity) {
            txtQty.setStyle(txtQty.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + "; -fx-border-color: red;");
        }

        if (isValidName && isValidQuantity && isValidPrice) {
            int quantity = Integer.parseInt(quantityString);
            double price = Double.parseDouble(priceString);

            TuteDTO tuteDTO = new TuteDTO(id, name,quantity,price);

            boolean isSaved = tuteBO.saveTute(tuteDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Tute saved...!").show();
                // refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Tute...!").show();
            }
            resetStyles();
        }
        refreshPage();

    }

    private void resetStyles() {
        txtTuteName.setStyle(txtTuteName.getStyle() + "; -fx-border-color: #7367F0;");
        txtQty.setStyle(txtQty.getStyle() + "; -fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + "; -fx-border-color: #7367F0;");
    }
    @FXML
    void reportOnClick(MouseEvent event) {


//        try {
//            Connection connection = DBConnection.getInstance().getConnection();
//
//
//            Map<String, Object> parameters = new HashMap<>();
//
//
//            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/Report/StudentOrder.jrxml"));
//            JasperPrint jasperPrint = JasperFillManager.fillReport(
//                    jasperReport,
//                    parameters,
//                    connection
//            );
//
//
//            JasperViewer.viewReport(jasperPrint, false);
//        } catch (JRException e) {
//            new Alert(Alert.AlertType.ERROR, "Fail to load report..!");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR, "Data empty..!");
//            e.printStackTrace();
//        }

    }


}

