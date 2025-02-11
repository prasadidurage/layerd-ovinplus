package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.custom.OrderBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.OrderDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.OrderDetailDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.StudentDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.TuteDAO;
import lk.ijse.gdse72.layerdovinplus.db.DBConnection;
import lk.ijse.gdse72.layerdovinplus.dto.BatchDTO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDTO;
import lk.ijse.gdse72.layerdovinplus.dto.StudentDTO;
import lk.ijse.gdse72.layerdovinplus.dto.TuteDTO;
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
        // trancestion tika



        // @connection: Retrieves the current connection instance for the database
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // @isOrderSaved: Saves the order details into the orders table
            boolean isOrderSaved = SQLUtil.execute(
                    "insert into orders values (?,?,?)",
                    orderDTO.getOrderId(),
                    orderDTO.getOrderDate(),
                    orderDTO.getStudentId()
            );
            // If the order is saved successfully
            if (isOrderSaved) {
                // @isOrderDetailListSaved: Saves the list of order details
                // boolean isOrderDetailSaved = orderDetailsDAO.save(orderDetail);
                boolean isOrderDetailListSaved = orderDetailDAO.saveDetail(orderDTO.getOrderDetailsDTOS());
                if (isOrderDetailListSaved) {
                    // @commit: Commits the transaction if both order and details are saved successfully
                    connection.commit(); // 2
                    return true;
                }
            }
            // @rollback: Rolls back the transaction if order details saving fails
            connection.rollback(); // 3
            return false;
        } catch (Exception e) {
            // @catch: Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        } finally {
            // @finally: Resets auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }
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
