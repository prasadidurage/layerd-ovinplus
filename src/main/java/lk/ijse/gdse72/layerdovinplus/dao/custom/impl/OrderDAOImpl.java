package lk.ijse.gdse72.layerdovinplus.dao.custom.impl;

import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.OrderDAO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDTO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.Order;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public ArrayList<Order> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM orders");

        // Initialize the list to hold OrderDTO objects
        ArrayList<Order> orderDTOS = new ArrayList<>();

        // Iterate through the result set
        while (rst.next()) {
            // Retrieve order information
            String orderId = rst.getString(1);
            Date orderDate = rst.getDate(2);
            String studentId = rst.getString(3);

            // Retrieve order details (items associated with the order)
            ArrayList<OrderDetailsDTO> orderDetails = getOrderDetailsByOrderId(orderId);

            // Create and add the OrderDTO object to the list
            Order order = new Order(orderId, orderDate, studentId, orderDetails);
            orderDTOS.add(order);
        }

        return orderDTOS;

    }

    private ArrayList<OrderDetailsDTO> getOrderDetailsByOrderId(String orderId) throws SQLException {
        ArrayList<OrderDetailsDTO> orderDetails = new ArrayList<>();

        // Query to get details for the specified order ID
        ResultSet detailsRst = SQLUtil.execute("SELECT * FROM orderdetail WHERE orderId = ?", orderId);

        while (detailsRst.next()) {
            // Extract details for each item in the order
            String orderCode =detailsRst.getString("orderId");
            String tutecode = detailsRst.getString("tuteId");
            int quantity = detailsRst.getInt("tuteQty");
            double unitPrice = detailsRst.getDouble("totalPrice");

            // Add the OrderDetailsDTO object to the list
            orderDetails.add(new OrderDetailsDTO(orderCode,tutecode, quantity, unitPrice));
        }

        return orderDetails;
    }

    public ArrayList<OrderDTO> getAllOrders() throws SQLException {
        // Execute the SQL query to fetch all orders
        ResultSet rst = SQLUtil.execute("SELECT * FROM orders");

        // Initialize the list to hold OrderDTO objects
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();

        // Iterate through the result set
        while (rst.next()) {
            // Retrieve order information
            String orderId = rst.getString(1);
            Date orderDate = rst.getDate(2);
            String studentId = rst.getString(3);

            // Retrieve order details (items associated with the order)
            ArrayList<OrderDetailsDTO> orderDetails = getOrderDetailsByOrderId(orderId);

            // Create and add the OrderDTO object to the list
            OrderDTO orderDTO = new OrderDTO(orderId, orderDate, studentId, orderDetails);
            orderDTOS.add(orderDTO);
        }

        return orderDTOS;
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
            String lastId = rst.getString(1); // Example: "Order -0002"
            String numericPart = lastId.substring(lastId.lastIndexOf('-') + 1); // e.g., "0002"
            int newIdIndex = Integer.parseInt(numericPart) + 1; // e.g., 3
            return String.format("Order -%04d", newIdIndex); // e.g., "Order -0003"
        }
        return "Order -0001";

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
    public Order findById(String selectedId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM orders WHERE orderId = ?", selectedId);
        if (rst.next()) {

            String orderId = rst.getString(1);
            Date orderDate = rst.getDate(2);
            String studentId = rst.getString(3);

            ArrayList<OrderDetailsDTO> orderDetails = getOrderDetailsByOrderId(orderId);

            return new Order(orderId, orderDate, studentId, orderDetails);
        }

        return null;
    }

    @Override
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return false;
    }
}
