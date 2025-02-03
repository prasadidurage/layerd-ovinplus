package lk.ijse.gdse72.layerdovinplus.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.layerdovinplus.bo.BOFactory;
import lk.ijse.gdse72.layerdovinplus.bo.custom.ConatctBO;
import lk.ijse.gdse72.layerdovinplus.bo.custom.UserBO;
import lk.ijse.gdse72.layerdovinplus.dto.ContactDTO;
import lk.ijse.gdse72.layerdovinplus.dto.tm.ContactTM;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ContactController implements Initializable {
    @FXML
    private TextField txtContactSearch;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableColumn<ContactTM, Date> colDate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<ContactTM, String> colContactId;

    @FXML
    private TableColumn<ContactTM, Integer> colContactNumber;

    @FXML
    private TableColumn<ContactTM, String> colStudentName;

    @FXML
    private Label lblContactId;

    @FXML
    private TableView<ContactTM> tblcontact;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtStudentName;

    private ObservableList<ContactTM> contactTMS;
    ConatctBO contactBO = (ConatctBO) BOFactory.getInstance().getBo(BOFactory.BOType.CONTACT);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colContactId.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
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
//
//        txtContactSearch.textProperty().addListener((observable, oldValue, newValue) -> {
//            searchBatchDetails(txtStudentName.getText(), newValue);
//        });
//
//        txtStudentName.textProperty().addListener((observable, oldValue, newValue) -> {
//            searchBatchDetails(newValue, txtContactSearch.getText());
//        });
//
//        try {
//            refreshTable(); // Show all batches if search is cleared
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        txtContactSearch.textProperty().addListener((observable, oldValue, newValue) -> {
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
            ArrayList<ContactDTO> searchResults = contactBO.search(searchTerm);
            contactTMS = FXCollections.observableArrayList();

            for (ContactDTO contact : searchResults) {
                contactTMS.add(new ContactTM(
                        contact.getContactId(),
                        contact.getDate(),
                        contact.getStudentName(),
                        contact.getContactNumber()
                ));
            }
            tblcontact.setItems(contactTMS);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error searching for contact details!").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextBatchId = contactBO.getNextId();
        lblContactId.setText(nextBatchId);

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = currentDate.format(formatter);

        txtDate.setText(formattedDate);

        txtStudentName.setText("");
        txtContactNumber.setText("");
        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<ContactDTO> contactDTOS = contactBO.getAll();
        ObservableList<ContactTM> contactTMS = FXCollections.observableArrayList();


        for (ContactDTO  contactDTO:contactDTOS){
            ContactTM contactTM = new ContactTM(
                   contactDTO.getContactId(),
                    contactDTO.getDate(),
                    contactDTO.getStudentName(),
                    contactDTO.getContactNumber()

            );
            contactTMS.add(contactTM);
        }
        tblcontact.setItems(contactTMS);
    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String id = lblContactId.getText();
        String dateStr = txtDate.getText();
        Date date;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate localDate = LocalDate.parse(dateStr, formatter);
            date = Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            txtDate.setStyle("-fx-border-color: red;");
            new Alert(Alert.AlertType.ERROR, "Invalid date format. Please use yyyy/MM/dd.").show();
            return;
        }

        String studentName = txtStudentName.getText();
        String contactNumber = txtContactNumber.getText();

        String namePattern = "^[a-zA-Z0-9 ]+$";  // Validate student name
        String contactNumberPattern = "^(\\+94|0)?(7[0-9]{8}|\\d{2}[0-9]{7})$";  // Validate Sri Lankan contact number

        boolean isValidName = studentName.matches(namePattern);
        boolean isValidContactNumber = contactNumber.matches(contactNumberPattern);

        // Reset styles
        txtStudentName.setStyle(txtStudentName.getStyle() + ";-fx-border-color: #7367F0;");
        txtContactNumber.setStyle(txtContactNumber.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtStudentName.setStyle(txtStudentName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidContactNumber) {
            txtContactNumber.setStyle(txtContactNumber.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidContactNumber) {
            ContactDTO contactDTO = new ContactDTO(id, date, studentName, Integer.parseInt(contactNumber));
            boolean isSaved = contactBO.save(contactDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Contact is saved!").show();
                refreshPage(); // Refresh the page after saving
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save contact!").show();
            }
        }
        refreshPage();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String contactId = lblContactId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Contact?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES){

            boolean isDeleted = contactBO.delete(contactId);

            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Contact deleted...!").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail to delete Contact...!").show();
            }
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblContactId.getText();
        String dateStr = txtDate.getText();
        Date date=Date.valueOf(txtDate.getText());




        String name = txtStudentName.getText();
        String contactNumber = txtContactNumber.getText();

        String namePattern = "^[a-zA-Z0-9 ]+$";  // Validate student name
        String contactNumberPattern = "^(\\+94|0)?(7[0-9]{8}|\\d{2}[0-9]{7})$";  // Validate Sri Lankan contact number

        boolean isValidName = name.matches(namePattern);
        boolean isValidContactNumber = contactNumber.matches(contactNumberPattern);

        // Reset styles
        txtStudentName.setStyle(txtStudentName.getStyle() + ";-fx-border-color: #7367F0;");
        txtContactNumber.setStyle(txtContactNumber.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtStudentName.setStyle(txtStudentName.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidContactNumber) {
            txtContactNumber.setStyle(txtContactNumber.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidContactNumber) {
            ContactDTO contactDTO = new ContactDTO(id, date, name, Integer.parseInt(contactNumber));
            boolean isUpdated = contactBO.update(contactDTO);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Contact updated!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update contact!").show();
            }
        }


    }

    @FXML
    void onClickTable(MouseEvent event) {
        ContactTM selectedItem = tblcontact.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            lblContactId.setText(selectedItem.getContactId());
            txtDate.setText(String.valueOf(selectedItem.getDate()));
            txtStudentName.setText(selectedItem.getStudentName());
            txtContactNumber.setText(String.valueOf(selectedItem.getContactNumber()));

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }

    }

}