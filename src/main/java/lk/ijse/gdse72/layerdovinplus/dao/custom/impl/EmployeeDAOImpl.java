package lk.ijse.gdse72.layerdovinplus.dao.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.custom.EmployeeBO;
import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.EmloyeeDAO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.Employee;
import lk.ijse.gdse72.layerdovinplus.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmloyeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");
        ArrayList<Employee> employeeList = new ArrayList<>();

        while (rst.next()) {
            employeeList.add(new Employee(
                    rst.getString("EmployeeId"),
                    rst.getString("Name"),
                    rst.getString("joinDate"),
                    rst.getString("jobRole")
            ));
        }
        return employeeList;
    }

    @Override
    public boolean save(Employee entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO employee VALUES (?,?,?,?)",
                entity.getEmployeeId(),
                entity.getEmployeeName(),
                entity.getJoinDate(),
                entity.getJobRole()
        );
    }

    @Override
    public boolean update(Employee entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE employee SET Name=?, joinDate=?, jobRole=? WHERE EmployeeId=?",
                entity.getEmployeeName(),
                entity.getJoinDate(),
                entity.getJobRole(),
                entity.getEmployeeId()
        );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT EmployeeId FROM employee ORDER BY EmployeeId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int newIdIndex = Integer.parseInt(substring) + 1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return SQLUtil.execute("DELETE FROM employee WHERE EmployeeId=?", Id);

    }

    @Override
    public ArrayList<Employee> search(String searchTerm) throws SQLException {
        ResultSet rst = SQLUtil.execute(
                "SELECT * FROM employee WHERE EmployeeId LIKE ? OR Name LIKE ?",
                "%" + searchTerm + "%",
                "%" + searchTerm + "%"
        );

        ArrayList<Employee> employeeList = new ArrayList<>();
        while (rst.next()) {
            employeeList.add(new Employee(
                    rst.getString("EmployeeId"),
                    rst.getString("Name"),
                    rst.getString("joinDate"),
                    rst.getString("jobRole")
            ));
        }
        return employeeList;
    }

    @Override
    public Employee findById(String selectedBatchId) throws SQLException {
        return null;
    }

    @Override
    public boolean reduceQty(OrderDetail orderDetail) throws SQLException {
        return false;
    }

    @Override
    public boolean updateBatch(Batch batch) {
        return false;
    }
}
