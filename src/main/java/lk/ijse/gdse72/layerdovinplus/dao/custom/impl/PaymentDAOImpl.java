package lk.ijse.gdse72.layerdovinplus.dao.custom.impl;

import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.PaymentDAO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public ArrayList<Payment> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from payment");

        ArrayList<Payment> paymentList = new ArrayList<>();

        while (rst.next()) {
            paymentList.add(new Payment(
                    rst.getString(1),   // PaymentId
                    rst.getString(2),   // DeliveryId
                    rst.getDate(3),     // PaymentDate
                    rst.getDouble(4)    // Amount
            ));
        }

        return paymentList;
    }

    @Override
    public boolean save(Payment entity) throws SQLException {
        return SQLUtil.execute(
                "insert into payment values (?,?,?,?)",
                entity.getPaymentId(),
                entity.getDeliveryId(),
                entity.getPaymentDate(),
                entity.getAmount()
        );
    }

    @Override
    public boolean update(Payment entity) throws SQLException {
        return SQLUtil.execute(
                "update payment set DeliveryId=?, Date=?, Amount=? where PaymentId=?",
                entity.getDeliveryId(),
                entity.getPaymentDate(),
                entity.getAmount(),
                entity.getPaymentId()
        );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT PaymentId FROM payment ORDER BY PaymentId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }

        return "P001";

    }
    @Override
    public boolean delete(String Id) throws SQLException {
        return SQLUtil.execute("delete from payment where PaymentId=?", Id);
    }

    @Override
    public ArrayList<Payment> search(String searchTerm) throws SQLException {
        ArrayList<Payment> paymentList = new ArrayList<>();

        // SQL query to search payments by ID or Amount
        String query = "SELECT * FROM payment WHERE PaymentId LIKE ? OR Amount LIKE ?";

        ResultSet rst = SQLUtil.execute(query, "%" + searchTerm + "%", "%" + searchTerm + "%");

        while (rst.next()) {
            paymentList.add(new Payment(
                    rst.getString("PaymentId"),
                    rst.getString("DeliveryId"),
                    rst.getDate("Date"),
                    rst.getDouble("Amount")
            ));
        }

        return paymentList;
    }

    @Override
    public Payment findById(String selectedId) throws SQLException {
        return null;
    }

    @Override
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return false;
    }
}
