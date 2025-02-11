package lk.ijse.gdse72.layerdovinplus.dao;

import lk.ijse.gdse72.layerdovinplus.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }
    public static DAOFactory getInstance() {return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;}
    public  enum DAOType {
        USER, BATCH, STUDENT, CONTACT,TUTE,EMPLOYEE,ORDER,ORDER_DETAIL
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

                case TUTE:
                    return new TuteDAOImpl();

                    case EMPLOYEE:
                        return new EmployeeDAOImpl();

                        case ORDER:
                            return new OrderDAOImpl();

                            case ORDER_DETAIL:
                                return new OrderDetailDAOImpl();



                default:
                    return null;
        }
    }
}
