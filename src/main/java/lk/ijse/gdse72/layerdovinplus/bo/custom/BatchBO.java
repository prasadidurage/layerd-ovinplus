package lk.ijse.gdse72.layerdovinplus.bo.custom;

import lk.ijse.gdse72.layerdovinplus.bo.SuperBO;
import lk.ijse.gdse72.layerdovinplus.dao.SuperDAO;
import lk.ijse.gdse72.layerdovinplus.dto.BatchDTO;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BatchBO extends SuperBO {

    ArrayList<BatchDTO> search(String searchTerm) throws SQLException;
    String getNextId () throws SQLException, ClassNotFoundException;

    ArrayList<BatchDTO> getAll() throws SQLException;

    boolean save(BatchDTO batchDTO) throws SQLException;

    boolean delete(String batchId) throws SQLException;

    boolean update(BatchDTO batchDTO) throws SQLException;
}
