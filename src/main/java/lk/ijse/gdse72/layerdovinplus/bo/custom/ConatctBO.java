package lk.ijse.gdse72.layerdovinplus.bo.custom;

import lk.ijse.gdse72.layerdovinplus.bo.SuperBO;
import lk.ijse.gdse72.layerdovinplus.dto.ContactDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ConatctBO extends SuperBO {
    ArrayList<ContactDTO> search(String searchName, String contactNumber) throws SQLException;
    boolean delete(String contactId);
}
