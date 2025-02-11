package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.custom.OrderBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.OrderDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.OrderDetailDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.StudentDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.TuteDAO;
import lk.ijse.gdse72.layerdovinplus.db.DBConnection;
import lk.ijse.gdse72.layerdovinplus.dto.*;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.Student;
import lk.ijse.gdse72.layerdovinplus.entity.Tute;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    TuteDAO tuteDAO = (TuteDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TUTE);
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_DETAIL);

    @Override
    public String getNextOrderId() throws SQLException, ClassNotFoundException {
        return  orderDAO.getNextId();
    }

    @Override
    public ArrayList<TuteDTO> getAlltuteIds() throws SQLException {
        ArrayList<TuteDTO> tuteDTOArrayList = new ArrayList<>();
        ArrayList<Tute>tutes=tuteDAO.getAll();
        for(Tute tute:tutes){
            tuteDTOArrayList.add(new TuteDTO(tute.getTuteId(),tute.getTuteName(),tute.getTuteQty(),tute.getTutePrice()));
        }
        return tuteDTOArrayList;
    }

    @Override
    public ArrayList<StudentDTO> getAllStudentIds() throws SQLException {
        ArrayList<StudentDTO> studentDTOS = new ArrayList<>();
        ArrayList<Student> students = studentDAO.getAll();
        for (Student student : students) {
            studentDTOS.add(new StudentDTO(student.getStudentId(),student.getStudentName(),student.getAddress(),student.getBatchId()));
        }
        return studentDTOS;
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // Disable auto-commit for transaction management
            connection.setAutoCommit(false);

            // Step 1: Insert the main order into the 'orders' table
            boolean isOrderSaved = SQLUtil.execute(
                    "insert into orders (orderId, orderDate, studentId) values (?,?,?)",
                    orderDTO.getOrderId(),
                    orderDTO.getOrderDate(),
                    orderDTO.getStudentId()
            );

            // If the order wasn't saved, rollback and return false
            if (!isOrderSaved) {
                connection.rollback();
                return false;
            }

            // Step 2: Iterate through each order detail and save them
            for (OrderDetailsDTO orderDetailsDTO : orderDTO.getOrderDetailsDTOS()) {
                // Save the individual order detail into the 'order_details' table
                boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDTO);
                if (!isOrderDetailsSaved) {
                    connection.rollback(); // Rollback if saving any order detail fails
                    return false;
                }

                // Update the stock quantity for the corresponding tute
                boolean isItemUpdated = tuteDAO.reduceQty(orderDetailsDTO);
                if (!isItemUpdated) {
                    connection.rollback(); // Rollback if quantity update fails
                    return false;
                }
            }

            // Step 3: Commit the transaction if everything is successful
            connection.commit();
            return true;

        } catch (Exception e) {
            // Print exception for debugging
            e.printStackTrace();
            try {
                // Rollback the transaction in case of any exception
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            // Step 4: Set auto-commit back to true after transaction
            connection.setAutoCommit(true);
        }
    }

    private boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into orderDetail  values (?,?,?,?)",
                orderDetailsDTO.getTuteId(),
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice()
        );
    }



    @Override
    public StudentDTO findByStudentId(String selectedStudentId) throws SQLException {
        Student student = studentDAO.findById(selectedStudentId); // Fetch batch by ID

        if (student != null) {
            return new StudentDTO(student.getStudentId(),student.getStudentName(),student.getAddress(),student.getBatchId());
        } else {
            return null; // Return null if batch is not found
        }
    }

    @Override
    public TuteDTO findByTuteFId(String selectedTuteId) throws SQLException {
        Tute tute =tuteDAO.findById(selectedTuteId);
        if (tute != null) {
            return new TuteDTO(tute.getTuteId(),tute.getTuteName(),tute.getTuteQty(),tute.getTutePrice());
        }else {
            return null;
        }
    }


    // public ArrayList<String> getAllOrderIds() throws SQLException {
//        ResultSet rst = SQLUtil.execute("select orderId from orders");
//
//        ArrayList<String> orderIds = new ArrayList<>();
//
//        while (rst.next()) {
//            orderIds.add(rst.getString(1));
//        }
//
//        return orderIds;
//    }

}
