package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.custom.TuteBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.custom.TuteDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.UserDAO;
import lk.ijse.gdse72.layerdovinplus.dto.StudentDTO;
import lk.ijse.gdse72.layerdovinplus.dto.TuteDTO;
import lk.ijse.gdse72.layerdovinplus.dto.UserDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Student;
import lk.ijse.gdse72.layerdovinplus.entity.Tute;
import lk.ijse.gdse72.layerdovinplus.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class TuteBOImpl implements TuteBO {

    TuteDAO tuteDAO = (TuteDAO) DAOFactory
            .getInstance().getDAO(DAOFactory.DAOType.TUTE);
    @Override
    public ArrayList<TuteDTO> searchTute(String searchTerm) throws SQLException {
        ArrayList<TuteDTO> tuteDTOS = new ArrayList<>();
        ArrayList<Tute> tutes = tuteDAO.search(searchTerm);
        for (Tute tute : tutes) {
            tuteDTOS.add(new TuteDTO(tute.getTuteId(),tute.getTuteName(),tute.getTuteQty(),tute.getTutePrice()));

        }
        return tuteDTOS;
    }

    @Override
    public String getNextTuteId() throws SQLException, ClassNotFoundException {
        return tuteDAO.getNextId();
    }

    @Override
    public ArrayList<TuteDTO> getAllTute() throws SQLException {
        ArrayList<TuteDTO> tuteDTOS = new ArrayList<>();
        ArrayList<Tute> tutes = tuteDAO.getAll();
        for (Tute tute : tutes) {
            tuteDTOS.add(new TuteDTO(tute.getTuteId(),tute.getTuteName(),tute.getTuteQty(),tute.getTutePrice()));

        }
        return tuteDTOS;
    }

    @Override
    public boolean deleteTute(String tuteId) throws SQLException {
        return tuteDAO.delete(tuteId);
    }

    @Override
    public boolean updateTute(TuteDTO tuteDTO) throws SQLException {
        return tuteDAO.update(new Tute(
                tuteDTO.getTuteId(),tuteDTO.getTuteName(),tuteDTO.getTuteQty(),tuteDTO.getTutePrice()));
    }

    @Override
    public boolean saveTute(TuteDTO tuteDTO) throws SQLException {
        return tuteDAO.save(new Tute(
                tuteDTO.getTuteId(),tuteDTO.getTuteName(),tuteDTO.getTuteQty(),tuteDTO.getTutePrice()));
    }
}
