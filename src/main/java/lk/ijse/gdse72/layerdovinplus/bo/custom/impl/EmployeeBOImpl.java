package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.custom.EmployeeBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.custom.BatchDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.EmloyeeDAO;
import lk.ijse.gdse72.layerdovinplus.dto.EmployeeDTO;
import lk.ijse.gdse72.layerdovinplus.dto.StudentDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Employee;
import lk.ijse.gdse72.layerdovinplus.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;



public class EmployeeBOImpl  implements EmployeeBO {
    EmloyeeDAO emloyeeDAO = (EmloyeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public ArrayList<EmployeeDTO> searchEmployee(String searchTerm) throws SQLException {
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
        ArrayList<Employee> employees = emloyeeDAO.getAll();
        for (Employee employee : employees) {
            employeeDTOS.add(new EmployeeDTO(employee.getEmployeeId(),employee.getEmployeeName(),employee.getJoinDate(),employee.getJobRole()));

        }
        return employeeDTOS;
    }

    @Override
    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        return  emloyeeDAO.getNextId();
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException {
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
        ArrayList<Employee> employees = emloyeeDAO.getAll();
        for (Employee employee : employees) {
            employeeDTOS.add(new EmployeeDTO(employee.getEmployeeId(),employee.getEmployeeName(),employee.getJoinDate(),employee.getJobRole()));

        }
        return employeeDTOS;
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws SQLException {
        return emloyeeDAO.delete(employeeId);
    }

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return emloyeeDAO.save(new Employee(
                employeeDTO.getEmployeeId(),employeeDTO.getEmployeeName(),employeeDTO.getJoinDate(),employeeDTO.getJobRole()
        ));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return emloyeeDAO.update(new Employee(
                employeeDTO.getEmployeeId(),employeeDTO.getEmployeeName(),employeeDTO.getJoinDate(),employeeDTO.getJobRole()
        ));
    }
}
