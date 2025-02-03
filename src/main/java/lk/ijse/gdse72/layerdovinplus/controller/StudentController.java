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
import lk.ijse.gdse72.layerdovinplus.bo.custom.StudentBO;
import lk.ijse.gdse72.layerdovinplus.dto.BatchDTO;
import lk.ijse.gdse72.layerdovinplus.dto.StudentDTO;
import lk.ijse.gdse72.layerdovinplus.dto.tm.StudentTM;
//import lk.ijse.gdse72.layerdovinplus.model.BatchModel;
//import lk.ijse.gdse72.layerdovinplus.model.StudentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class StudentController implements Initializable {
        @FXML
        private Button btnDelete;

        @FXML
        private Button btnReset;

        @FXML
        private Button btnSave;

        @FXML
        private Button btnUpdate;

        @FXML
        private ComboBox<String> cmbBatch;

        @FXML
        private TableColumn<StudentTM, String> colAddress;

        @FXML
        private TableColumn<StudentTM, String> colBatch;

        @FXML
        private TableColumn<StudentTM, String> colStuId;

        @FXML
        private TableColumn<StudentTM, String> colStuName;

        @FXML
        private Label lblBatch;

        @FXML
        private Label lblStuId;

        @FXML
        private TableView<StudentTM> tblStudent;

        @FXML
        private TextField txtAdress;

        @FXML
        private TextField txtStuName;

        @FXML
        private TextField txtStudentSearch;

        //private final BatchModel batchModel = new BatchModel();
        //private final StudentModel studentModel = new StudentModel();
        BatchBO batchBO = (BatchBO) BOFactory.getInstance().getBo(BOFactory.BOType.BATCH);
        StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBo(BOFactory.BOType.STUDENT);


        private ObservableList<StudentTM> studentTMS;

        private Button previousButton = null;

        // Default and selected styles
        private static final String DEFAULT_STYLE = "-fx-background-color: #b2bec3; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 50px; -fx-background-radius: 50px;";
        private static final String SELECTED_STYLE = "-fx-background-color: #ADD8E6; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 50px; -fx-background-radius: 50px;";



        @FXML
        void changeButtonColor(ActionEvent event) {
                Button clickedButton = (Button) event.getSource();

                // If the clicked button is already selected, reset its style
                if (clickedButton.equals(previousButton)) {
                        clickedButton.setStyle(DEFAULT_STYLE);
                        previousButton = null; // Clear the reference since no button is selected
                } else {
                        // Reset the previously clicked button
                        resetButtonStyles();

                        // Apply the selected style to the clicked button
                        clickedButton.setStyle(SELECTED_STYLE);

                        // Update the reference to the currently clicked button
                        previousButton = clickedButton;
                        //resetButtonStyles();
                }


        }

        // Method to reset all buttons to their default styles
        private void resetButtonStyles() {
                // Reset the previous button if it's not null
                if (previousButton != null) {
                        previousButton.setStyle(DEFAULT_STYLE);

                }
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                colStuId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
                colStuName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
                colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
                colBatch.setCellValueFactory(new PropertyValueFactory<>("batchId"));

                try {
                        refreshPage();
//
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
                setupStudentSearch();

        }

        private void setupStudentSearch() {

                txtStudentSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.isEmpty()) {
                                searchStudentDetails(newValue);
                        } else {
                                try {
                                        refreshTable(); // Show all batches if search is cleared
                                } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                }
                        }
                });
        }

        private void searchStudentDetails(String searchTerm) {
                try {
                        ArrayList<StudentDTO> searchResults = studentBO.search(searchTerm);
                        studentTMS = FXCollections.observableArrayList();

                        for (StudentDTO student : searchResults) {
                                studentTMS.add(new StudentTM(student.getStudentId(), student.getStudentName(), student.getAddress(),student.getBatchId()));
                        }
                        tblStudent.setItems(studentTMS);

                } catch (SQLException e) {
                        e.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, "Error searching for batch details!").show();
                }
        }

        private void refreshPage() throws SQLException {
                refreshTable();

                String nextStudentId = studentModel.getNextStudentId();
                lblStuId.setText(nextStudentId);

                txtStuName.setText("");
                txtAdress.setText("");
                lblBatch.setText("");
                cmbBatch.setValue("Select Batch");
                btnSave.setDisable(false);

                btnDelete.setDisable(true);
                btnUpdate.setDisable(true);
                loradBatchId();
        }

        private void refreshTable() throws SQLException {
                ArrayList<StudentDTO> studentDTOS = studentModel.getAllStudent();
                ObservableList<StudentTM> studentTMS = FXCollections.observableArrayList();


                for (StudentDTO studentDTO:studentDTOS){
                        StudentTM studentTM = new StudentTM(
                               studentDTO.getStudentId(),
                                studentDTO.getStudentName(),
                                studentDTO.getAddress(),
                                studentDTO.getBatchId()
                        );
                        studentTMS.add(studentTM);
                }
                tblStudent.setItems(studentTMS);

        }


        @FXML

        void saveOnAction(ActionEvent event) throws SQLException {
                changeButtonColor(event);
                String id = lblStuId.getText();
                String name = txtStuName.getText();
                String address = txtAdress.getText();
               // String batchId = lblBatch.getText();
                String batchId =cmbBatch.getValue();

                if (cmbBatch.getSelectionModel().isEmpty()) {
                        new Alert(Alert.AlertType.ERROR, "Please select Batch for Save..!").show();
                        return;
                }
                String namePattern = "^[a-zA-Z0-9 ]+$";

                boolean isValidName = name.matches(namePattern);

                txtStuName.setStyle(txtStuName.getStyle()+";-fx-border-color: #7367F0;");


                if (!isValidName){
                        txtStuName.setStyle(txtStuName.getStyle()+";-fx-border-color: red;");
                }


                if (isValidName ){
                        StudentDTO studentDTO = new StudentDTO(id,name,address,batchId);

                        boolean isSaved = studentModel.saveStudent(studentDTO);

                        if (isSaved) {
                                new Alert(Alert.AlertType.INFORMATION, "Student saved...!").show();

                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to save Student...!").show();
                        }
                }
                 refreshPage();

        }

        @FXML
        void btnDeleteOnAction(ActionEvent event) throws SQLException {
                String studentId = lblStuId.getText();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Student?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get() == ButtonType.YES){

                        boolean isDeleted = studentModel.deleteSthudent(studentId);

                        if (isDeleted){
                                new Alert(Alert.AlertType.INFORMATION,"Student deleted...!").show();
                                refreshPage();
                        }else {
                                new Alert(Alert.AlertType.ERROR,"Fail to delete Student...!").show();
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
                changeButtonColor(event);
                String id = lblStuId.getText();
                String name = txtStuName.getText();
                String address = txtAdress.getText();
                String batchId =cmbBatch.getValue();


                String namePattern = "^[a-zA-Z0-9 ]+$";

//        Pattern compile = Pattern.compile(namePattern);
//        System.out.println(compile.matcher(name).matches());

                boolean isValidName = name.matches(namePattern);

                txtStuName.setStyle(txtStuName.getStyle()+";-fx-border-color: #7367F0;");

                if (!isValidName){
                        txtStuName.setStyle(txtStuName.getStyle()+";-fx-border-color: red;");
                }



                if (isValidName  ){
                        StudentDTO studentDTO = new StudentDTO(id,name,address,batchId);
                        boolean isUpdate = studentModel.updateStudent(studentDTO);




                        if (isUpdate){
                                new Alert(Alert.AlertType.INFORMATION,"Student updated...!").show();
                                refreshPage();
                        }else {
                                new Alert(Alert.AlertType.ERROR, "Fail to update Student...!").show();
                        }
                }

        }

        @FXML
        void onClickTable(MouseEvent event) {
                StudentTM selectedItem = tblStudent.getSelectionModel().getSelectedItem();
                if (selectedItem != null){
                        lblStuId.setText(selectedItem.getStudentId());
                        txtStuName.setText(selectedItem.getStudentName());
                        txtAdress.setText(selectedItem.getAddress());
                        cmbBatch.setValue(String.valueOf(selectedItem.getBatchId()));
                        btnSave.setDisable(true);

                        btnDelete.setDisable(false);
                        btnUpdate.setDisable(false);

                }


        }

        private void loradBatchId() throws SQLException {
                ArrayList<String> batchIds = batchModel.getAllBatchIds();
                ObservableList<String> observableList = FXCollections.observableArrayList();
                observableList.addAll(batchIds);
                cmbBatch.setItems(observableList);
        }

        @FXML
        void selectBatchOnAction(ActionEvent event) throws SQLException {
                String selectedBatchId = cmbBatch.getSelectionModel().getSelectedItem();
                BatchDTO batchDTO = batchModel.findById(selectedBatchId);

                // If customer found (customerDTO not null)
                if (batchDTO != null) {

                        // FIll customer related labels
                        lblBatch.setText(batchDTO.getBatchName());
                }
                }


}
