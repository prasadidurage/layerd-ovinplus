package lk.ijse.gdse72.layerdovinplus.bo.custom;

import lk.ijse.gdse72.layerdovinplus.bo.SuperBO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDTO;
import lk.ijse.gdse72.layerdovinplus.dto.StudentDTO;
import lk.ijse.gdse72.layerdovinplus.dto.TuteDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO  extends SuperBO {
    String getNextOrderId() throws SQLException, ClassNotFoundException;

    ArrayList<String> getAlltuteIds() throws SQLException;

    ArrayList<String> getAllStudentIds() throws SQLException;

    boolean saveOrder(OrderDTO orderDTO) throws SQLException;

    StudentDTO findByStudentId(String selectedStudentId) throws SQLException;

    TuteDTO findByTuteFId(String selectedTuteId) throws SQLException;
}
