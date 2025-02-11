package lk.ijse.gdse72.layerdovinplus.dao.custom.impl;

import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.OrderDAO;
import lk.ijse.gdse72.layerdovinplus.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(Order entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Order entity) throws SQLException {
        return false;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            if (lastId != null && lastId.matches("O-\\d+")) {
                return String.format("O-%04d", Integer.parseInt(lastId.substring(2)) + 1);
            }
            throw new SQLException("Unexpected order ID format in the database.");
        }
        return "O-0001";
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Order> search(String searchTerm) throws SQLException {
        return null;
    }

    @Override
    public Order findById(String selectedBatchId) throws SQLException {
        return null;
    }
}
