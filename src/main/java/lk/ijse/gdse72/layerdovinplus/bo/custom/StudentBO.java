package lk.ijse.gdse72.layerdovinplus.bo.custom;

import lk.ijse.gdse72.layerdovinplus.bo.SuperBO;
import lk.ijse.gdse72.layerdovinplus.dto.BatchDTO;
import lk.ijse.gdse72.layerdovinplus.dto.StudentDTO;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentBO extends SuperBO{
    ArrayList<StudentDTO> search(String searchTerm) throws SQLException;
    String getNextId() throws SQLException, ClassNotFoundException;
    ArrayList<StudentDTO> getAll() throws SQLException;

    boolean save(StudentDTO studentDTO) throws SQLException;

    boolean delete(String studentId) throws SQLException;

    boolean update(StudentDTO studentDTO) throws SQLException;

    ArrayList<String > getAllBatchIds() throws SQLException;

    BatchDTO findById(String selectedBatchId) throws SQLException ;
}
