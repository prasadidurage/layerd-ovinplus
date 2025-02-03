package lk.ijse.gdse72.layerdovinplus.bo.custom;

import lk.ijse.gdse72.layerdovinplus.bo.SuperBO;
import lk.ijse.gdse72.layerdovinplus.dto.ContactDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ConatctBO extends SuperBO {
    ArrayList<ContactDTO> search(String searchTerm) throws SQLException;
    boolean delete(String contactId) throws SQLException;
    String getNextId() throws SQLException, ClassNotFoundException;
    ArrayList<ContactDTO> getAll() throws SQLException;
    boolean save(ContactDTO contactDTO) throws SQLException;
    boolean update(ContactDTO contactDTO) throws SQLException;
}
