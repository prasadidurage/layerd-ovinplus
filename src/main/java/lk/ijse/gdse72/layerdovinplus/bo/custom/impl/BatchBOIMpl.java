package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.custom.BatchBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.custom.BatchDAO;
import lk.ijse.gdse72.layerdovinplus.dto.BatchDTO;
import lk.ijse.gdse72.layerdovinplus.dto.ContactDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.Contact;

import java.sql.SQLException;
import java.util.ArrayList;


public class BatchBOIMpl implements BatchBO {
    BatchDAO batchDAO = (BatchDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BATCH);


    @Override
    public boolean save(BatchDTO batchDTO) throws SQLException {
        return batchDAO.save(new Batch(batchDTO.getBatchID(),batchDTO.getBatchName(),batchDTO.getStudentCount()));
    }

    @Override
    public boolean delete(String batchId) throws SQLException {
        return  batchDAO.delete(batchId);
    }

    @Override
    public boolean update(BatchDTO batchDTO) throws SQLException {
        return batchDAO.update(new Batch(batchDTO.getBatchID(),batchDTO.getBatchName(),batchDTO.getStudentCount()));

    }

    @Override
    public ArrayList<BatchDTO> search(String searchTerm) throws SQLException {
        ArrayList<BatchDTO> batchDTOS = new ArrayList<>();
        ArrayList<Batch> batches = batchDAO.search(searchTerm);
        for (Batch batch : batches) {
            batchDTOS.add(new BatchDTO(
                    batch.getBatchID(),batch.getBatchName(),batch.getStudentCount()
            ));
        }
        return batchDTOS;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return batchDAO.getNextId();
    }

    @Override
    public ArrayList<BatchDTO> getAll() throws SQLException {
        ArrayList<BatchDTO> batchDTOS = new ArrayList<>();
        ArrayList<Batch> batches = batchDAO.getAll();
        for (Batch batch : batches) {
            batchDTOS.add(new BatchDTO(
                    batch.getBatchID(),batch.getBatchName(),batch.getStudentCount()
            ));
        }
        return batchDTOS;
    }

}

