package lk.ijse.gdse72.layerdovinplus.bo.custom;

import lk.ijse.gdse72.layerdovinplus.bo.SuperBO;
import lk.ijse.gdse72.layerdovinplus.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    ArrayList<EmployeeDTO> searchEmployee(String searchTerm) throws SQLException;

    String getNextEmployeeId() throws SQLException, ClassNotFoundException;

    ArrayList<EmployeeDTO> getAllEmployee() throws SQLException;

    boolean deleteEmployee(String employeeId) throws SQLException;

    boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException;

    boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException;
}
