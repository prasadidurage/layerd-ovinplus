package lk.ijse.gdse72.layerdovinplus.bo.custom;

import lk.ijse.gdse72.layerdovinplus.bo.SuperBO;
import lk.ijse.gdse72.layerdovinplus.dto.StudentDTO;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentBO extends SuperBO{
    ArrayList<StudentDTO> search(String searchTerm) throws SQLException;
}
