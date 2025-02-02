package lk.ijse.gdse72.layerdovinplus.dao;

import lk.ijse.gdse72.layerdovinplus.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    ArrayList<User> getAll() throws SQLException;
    boolean save(T dto) throws SQLException;
    boolean update(T dto) throws SQLException;
    String getNextId() throws SQLException, ClassNotFoundException;;
    boolean delete(String userId) throws SQLException;
    ArrayList<T> search(String searchTerm) throws SQLException;
}
