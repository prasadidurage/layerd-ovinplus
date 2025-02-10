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
import lk.ijse.gdse72.layerdovinplus.bo.custom.EmployeeBO;
import lk.ijse.gdse72.layerdovinplus.bo.custom.StudentBO;
import lk.ijse.gdse72.layerdovinplus.dto.EmployeeDTO;
import lk.ijse.gdse72.layerdovinplus.dto.tm.EmployeeTM;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<EmployeeTM, String> colEmployeeId;

    @FXML
    private TableColumn<EmployeeTM, String> colEmployeeName;

    @FXML
    private TableColumn<EmployeeTM,String> colJobRole;

    @FXML
    private TableColumn<EmployeeTM, String> colJoinDate;

    @FXML
    private Label lblEmolyeeId;

    @FXML
    private TableView<EmployeeTM> tblEmployee;


    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtJobRole;

    @FXML
    private TextField txtJoinDate;

    @FXML
    private TextField txtSearch;

//    EmolyeeModel emolyeeModel = new EmolyeeModel();
    private ObservableList<EmployeeTM> employeeTMS;
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBo(BOFactory.BOType.EMPLOYEE);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colJoinDate.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));


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
        setupEmployeeSearch();


    }

    private void setupEmployeeSearch() {
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                searchEmployeeDetails(newValue);
            } else {
                try {
                    refreshTable(); // Show all batches if search is cleared
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void searchEmployeeDetails(String searchTerm) {
        try {
            ArrayList<EmployeeDTO> searchResults = employeeBO.searchEmployee(searchTerm);
            employeeTMS = FXCollections.observableArrayList();

            for (EmployeeDTO employee : searchResults) {
                employeeTMS.add(new EmployeeTM(employee.getEmployeeId(),employee.getEmployeeName(),employee.getJoinDate(),employee.getJobRole()));
            }
            tblEmployee.setItems(employeeTMS);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error searching for Employee details!").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextEmployeeId =  employeeBO.getNextEmployeeId();
        lblEmolyeeId.setText(nextEmployeeId);

        txtEmployeeName.setText("");
        txtJoinDate.setText("");
        txtJobRole.setText("");
        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<EmployeeDTO> employeeDTOS = employeeBO.getAllEmployee();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();


        for (EmployeeDTO employeeDTO:employeeDTOS){
            EmployeeTM employeeTM = new EmployeeTM(
                  employeeDTO.getEmployeeId(),
                    employeeDTO.getEmployeeName(),
                    employeeDTO.getJoinDate(),
                    employeeDTO.getJobRole()

            );
            employeeTMS.add(employeeTM);
        }
        tblEmployee.setItems(employeeTMS);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String employeeId = lblEmolyeeId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Employee?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES){

            boolean isDeleted = employeeBO.deleteEmployee(employeeId);

            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Employee deleted...!").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail to delete Employee...!").show();
            }
        }


    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblEmolyeeId.getText();
        String name = txtEmployeeName.getText();
        String joinDate = txtJoinDate.getText();
        String jobRole = txtJobRole.getText();


        String namePattern = "^[a-zA-Z0-9 ]+$";
        String datePattern ="\\d{4}-\\d{2}-\\d{2}";


        boolean isValidName = name.matches(namePattern);
        boolean isValidDate = joinDate.matches(datePattern);

        txtEmployeeName.setStyle(txtEmployeeName.getStyle()+";-fx-border-color: #7367F0;");
        txtJoinDate.setStyle(txtJoinDate.getStyle()+";-fx-border-color: #7367F0;");


        if (!isValidName){
            txtEmployeeName.setStyle(txtEmployeeName.getStyle()+";-fx-border-color: red;");
        }
        if (!isValidDate){
            txtJoinDate.setStyle(txtJoinDate.getStyle()+";-fx-border-color: red;");
       }




        if (isValidName  ){
            EmployeeDTO employeeDTO = new EmployeeDTO(id,name,joinDate,jobRole);

            boolean isSaved = employeeBO.saveEmployee(employeeDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Employee saved...!").show();
                // refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Employee...!").show();
            }
        }
        refreshPage();


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = lblEmolyeeId.getText();
        String name = txtEmployeeName.getText();
        String joindate = txtJoinDate.getText();
        String jobrole = txtJobRole.getText();



        String namePattern = "^[a-zA-Z0-9 ]+$";
        String datepattern = "\\d{4}-\\d{2}-\\d{2}";

//        Pattern compile = Pattern.compile(namePattern);
//        System.out.println(compile.matcher(name).matches());

        boolean isValidName = name.matches(namePattern);
        boolean isvalidDate = joindate.matches(datepattern);

        txtEmployeeName.setStyle(txtEmployeeName.getStyle()+";-fx-border-color: #7367F0;");
        txtJoinDate.setStyle(txtJoinDate.getStyle()+";-fx-border-color: #7367F0;");

        if (!isValidName){
                txtEmployeeName.setStyle(txtEmployeeName.getStyle()+";-fx-border-color: red;");
        }

        if (!isvalidDate){
            txtJoinDate.setStyle(txtJoinDate.getStyle()+";-fx-border-color: red;");
        }


        if (isValidName && isvalidDate ){
            EmployeeDTO employeeDTO = new EmployeeDTO();
            boolean isUpdate = employeeBO.updateEmployee(employeeDTO);




            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Employee updated...!").show();
                refreshPage();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Employee...!").show();
            }
        }


    }

    @FXML
    void onClickTable(MouseEvent event)  {
        EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            lblEmolyeeId.setText(selectedItem.getEmployeeId());
            txtEmployeeName.setText(selectedItem.getEmployeeName());
            txtJoinDate.setText(selectedItem.getJoinDate());
            txtJobRole.setText(selectedItem.getJobRole());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }

    }



}

