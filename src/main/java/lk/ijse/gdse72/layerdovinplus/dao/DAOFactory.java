package lk.ijse.gdse72.layerdovinplus.dao;

import lk.ijse.gdse72.layerdovinplus.dao.custom.impl.BatchDAOImpl;
import lk.ijse.gdse72.layerdovinplus.dao.custom.impl.ContactDAOImpl;
import lk.ijse.gdse72.layerdovinplus.dao.custom.impl.StudentDAOImpl;
import lk.ijse.gdse72.layerdovinplus.dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }
    public static DAOFactory getInstance() {return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;}
    public  enum DAOType {
        USER, BATCH, STUDENT, CONTACT
    }
    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case USER:
                return new UserDAOImpl();

            case CONTACT:
                return new ContactDAOImpl();

            case BATCH:
                return new BatchDAOImpl();

            case STUDENT:
                return new StudentDAOImpl();

                default:
                    return null;
        }
    }
}
