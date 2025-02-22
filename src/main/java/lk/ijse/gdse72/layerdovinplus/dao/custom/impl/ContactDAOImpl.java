package lk.ijse.gdse72.layerdovinplus.dao.custom.impl;

import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.ContactDAO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.Contact;
import lk.ijse.gdse72.layerdovinplus.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactDAOImpl implements ContactDAO {
    @Override
    public void uniqueMethodForCustomer() {

    }

    @Override
    public ArrayList<Contact> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from contact");

        ArrayList<Contact> contactArrayList = new ArrayList<>();

        while (rst.next()) {

            contactArrayList.add(new Contact(rst.getString(1), rst.getDate(2), rst.getString(3), rst.getInt(4)));
        }
        return contactArrayList;
    }

    @Override
    public boolean save(Contact entity) throws SQLException {
        return SQLUtil.execute(
                "insert into contact values (?,?,?,?)",
                entity.getContactId(),
                entity.getDate(),
                entity.getStudentName(),
                entity.getContactNumber()
        );
    }

    @Override
    public boolean update(Contact entity) throws SQLException {
        return SQLUtil.execute(
                "update contact set date =?, Student_Name=?,contactNumber=? where contactId=?",
                entity.getDate(),
                entity.getStudentName(),
                entity.getContactNumber(),
                entity.getContactId()

        );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT contactId FROM contact ORDER BY contactId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            if (lastId != null && lastId.matches("C-\\d+")) {
                try {
                    return String.format("C-%04d", Integer.parseInt(lastId.substring(2)) + 1);
                } catch (NumberFormatException e) {
                    throw new SQLException("Invalid contact ID format in the database.");
                }
            }
            throw new SQLException("Unexpected contact ID format in the database.");
        }
        return "C-0001"; // Default ID if no records exist
    }

    @Override
    public boolean delete(String ContactId) throws SQLException {
        return SQLUtil.execute("delete from contact where contactId=?", ContactId);
    }

    @Override
    public ArrayList<Contact> search(String searchTerm) throws SQLException {
        String query = "SELECT * FROM contact WHERE Student_Name LIKE ? AND contactNumber = ?";
        ResultSet rst = SQLUtil.execute(query,"%" + searchTerm + "%", "%" + searchTerm + "%");

        ArrayList<Contact> contactArrayList = new ArrayList<>();

        while (rst.next()) {
            contactArrayList.add(new Contact(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getString(3),
                    rst.getInt(4)
            ));

        }

        return contactArrayList;
    }

    @Override
    public Contact findById(String selectedBatchId) throws SQLException {
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

