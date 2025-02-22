package lk.ijse.gdse72.layerdovinplus.dao.custom;

import lk.ijse.gdse72.layerdovinplus.dao.CrudDAO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;

import java.sql.SQLException;

public interface BatchDAO extends CrudDAO<Batch> {
    boolean reduceStudentCount(String batch) throws SQLException;
    //  ArrayList<Batch> search(String searchTerm);
}
