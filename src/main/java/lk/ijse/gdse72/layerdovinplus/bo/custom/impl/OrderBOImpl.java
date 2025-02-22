package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.custom.OrderBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.*;
import lk.ijse.gdse72.layerdovinplus.db.DBConnection;
import lk.ijse.gdse72.layerdovinplus.dto.*;
import lk.ijse.gdse72.layerdovinplus.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;


public class OrderBOImpl implements OrderBO {

    EmloyeeDAO emloyeeDAO = (EmloyeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    TuteDAO tuteDAO = (TuteDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TUTE);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAIL);
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);

    @Override
    public String getNextOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.getNextId();
    }

    @Override
    public ArrayList<String> getAlltuteIds() throws SQLException {
        ArrayList<String> tuteIds = new ArrayList<>();
        ArrayList<Tute> tutes = tuteDAO.getAll(); // Fetch all batches
        for (Tute tute : tutes) {
            tuteIds.add(tute.getTuteId()); // Extract batch ID and add it to the list
        }

        return tuteIds;
    }

    @Override
    public ArrayList<String> getAllStudentIds() throws SQLException {
        ArrayList<String> studentIds = new ArrayList<>();
        ArrayList<Student> students = studentDAO.getAll(); // Fetch all batches
        for (Student student : students) {
            studentIds.add(student.getStudentId()); // Extract batch ID and add it to the list
        }

        return studentIds;
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false); // 1. Start transaction

            // 2. Save order details to orders table
            boolean isOrderSaved = orderDAO.save(new Order(orderDTO.getOrderId(),orderDTO.getOrderDate(),orderDTO.getStudentId()));
            System.out.println("Order Saved: " );

            if (!isOrderSaved) {
                connection.rollback();
                return false;
            }

            // 3. Save order details list
            for (OrderDetailsDTO orderDetailsDTO : orderDTO.getOrderDetailsDTOS()) {
                // Convert DTO to Entity
                OrderDetail orderDetail = new OrderDetail(
                        orderDetailsDTO.getOrderId(),
                        orderDetailsDTO.getTuteId(),
                        orderDetailsDTO.getQuantity(),
                        orderDetailsDTO.getPrice()

                );

                boolean isOrderDetailsSaved = orderDetailDAO.save(orderDetail); // Save entity instead of DTO
                if (!isOrderDetailsSaved) {
                    connection.rollback();
                    return false;
                }



                // 4. Reduce item quantity
                boolean isItemUpdated = tuteDAO.reduceQty(orderDetail);
                if (!isItemUpdated) {
                    connection.rollback();
                    return false;
                }
            }


            connection.commit(); // 5. Commit transaction if everything is successful
            return true;
        } catch (Exception e) {
            try {
                connection.rollback(); // Rollback transaction on error
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit mode
            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }
        }
    }

    @Override
    public StudentDTO findByStudentId(String selectedStudentId) throws SQLException {
        Student student =studentDAO.findById(selectedStudentId);
        if (student != null) {
            return new StudentDTO(student.getStudentId(),student.getStudentName(),student.getAddress(),student.getBatchId());

        }else {
            return null;
        }
    }

    @Override
    public TuteDTO findByTuteFId(String selectedTuteId) throws SQLException {
        Tute tute = tuteDAO.findById(selectedTuteId); // Fetch batch by ID

        if (tute != null) {
            return new TuteDTO(tute.getTuteId(),tute.getTuteName(),tute.getTuteQty(),tute.getTutePrice());
        } else {
            return null; // Return null if batch is not found
        }
    }

    @Override
    public ArrayList<OrderDTO> searchOrders(String searchText) throws SQLException {
        ArrayList<OrderDTO> orderDTOArrayList = new ArrayList<>();
        ArrayList<Order> orders = orderDAO.search(searchText);
        for (Order order : orders) {
            orderDTOArrayList.add(new OrderDTO(order.getOrderId(),order.getOrderDate(),order.getStudentId()));

        }
        return orderDTOArrayList;
    }

    @Override
    public ArrayList<OrderDTO> getOrders() throws SQLException {
        ArrayList<OrderDTO> orderDTOArrayList = new ArrayList<>();
        ArrayList<Order> orders = orderDAO.getAll();
        for (Order order : orders) {
            orderDTOArrayList.add(new OrderDTO(order.getOrderId(),order.getOrderDate(),order.getStudentId()));

        }
        return orderDTOArrayList;
    }
}
