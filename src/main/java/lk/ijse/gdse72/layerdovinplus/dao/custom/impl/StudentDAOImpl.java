package lk.ijse.gdse72.layerdovinplus.dao.custom.impl;

import lk.ijse.gdse72.layerdovinplus.dao.custom.StudentDAO;
import lk.ijse.gdse72.layerdovinplus.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public ArrayList<Student> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(Student entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Student entity) throws SQLException {
        return false;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Student> search(String searchTerm) throws SQLException {
        return null;
    }
}
