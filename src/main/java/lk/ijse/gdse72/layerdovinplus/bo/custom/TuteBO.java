package lk.ijse.gdse72.layerdovinplus.bo.custom;

import lk.ijse.gdse72.layerdovinplus.bo.SuperBO;
import lk.ijse.gdse72.layerdovinplus.dto.TuteDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TuteBO extends SuperBO {
    ArrayList<TuteDTO> searchTute(String searchTerm) throws SQLException;

    String getNextTuteId() throws SQLException, ClassNotFoundException;

    ArrayList<TuteDTO> getAllTute() throws SQLException;

    boolean deleteTute(String tuteId) throws SQLException;

    boolean updateTute(TuteDTO tuteDTO) throws SQLException;

    boolean saveTute(TuteDTO tuteDTO) throws SQLException;
}
