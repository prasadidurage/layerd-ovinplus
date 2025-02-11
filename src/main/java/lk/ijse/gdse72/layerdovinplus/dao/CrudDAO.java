package lk.ijse.gdse72.layerdovinplus.dao;

import lk.ijse.gdse72.layerdovinplus.dto.BatchDTO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    ArrayList<T> getAll() throws SQLException;
    boolean save(T entity) throws SQLException;
    boolean update(T entity) throws SQLException;
    String getNextId() throws SQLException, ClassNotFoundException;;
    boolean delete(String Id) throws SQLException;
    ArrayList<T> search(String searchTerm) throws SQLException;
    T findById(String selectedId) throws SQLException;
    boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException;
}
