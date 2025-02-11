package lk.ijse.gdse72.layerdovinplus.dao.custom.impl;

import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.DelivaryDAO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Delivary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DelivaryDAOImpl implements DelivaryDAO {
    @Override
    public ArrayList<Delivary> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Delivery");
        ArrayList<Delivary> deliveryList = new ArrayList<>();

        while (rst.next()) {
            deliveryList.add(new Delivary(
                    rst.getString("DeliveryId"),
                    rst.getDate("Date"),
                    rst.getString("Status"),
                    rst.getString("orderId")
            ));
        }
        return deliveryList;
    }

    @Override
    public boolean save(Delivary entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO Delivery VALUES (?,?,?,?)",
                entity.getDeliveryId(),
                entity.getDeliveryDate(),
                entity.getStatus(),
                entity.getOrderId()
        );
    }

    @Override
    public boolean update(Delivary entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE Delivery SET Date=?, Status=?, orderId=? WHERE DeliveryId=?",
                entity.getDeliveryDate(),
                entity.getStatus(),
                entity.getOrderId(),
                entity.getDeliveryId()
        );
    }


    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT DeliveryId FROM Delivery ORDER BY DeliveryId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            if (lastId != null && lastId.matches("D-\\d+")) {
                return String.format("D-%03d", Integer.parseInt(lastId.substring(2)) + 1);
            }
            throw new SQLException("Unexpected Delivery ID format in the database.");
        }
        return "D-001";
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return SQLUtil.execute("DELETE FROM Delivery WHERE DeliveryId=?", Id);    }

    @Override
    public ArrayList<Delivary> search(String searchTerm) throws SQLException {
        ResultSet rst = SQLUtil.execute(
                "SELECT * FROM Delivery WHERE DeliveryId LIKE ? OR orderId LIKE ?",
                "%" + searchTerm + "%", "%" + searchTerm + "%"
        );

        ArrayList<Delivary> deliveryList = new ArrayList<>();

        while (rst.next()) {
            deliveryList.add(new Delivary(
                    rst.getString("DeliveryId"),
                    rst.getDate("Date"),
                    rst.getString("Status"),
                    rst.getString("orderId")
            ));
        }
        return deliveryList;
    }

    @Override
    public Delivary findById(String selectedId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Delivery WHERE DeliveryId=?", selectedId);

        if (rst.next()) {
            return new Delivary(
                    rst.getString("DeliveryId"),
                    rst.getDate("Date"),
                    rst.getString("Status"),
                    rst.getString("orderId")
            );
        }
        return null; // Return null if not found
    }


    @Override
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return false;
    }
}
