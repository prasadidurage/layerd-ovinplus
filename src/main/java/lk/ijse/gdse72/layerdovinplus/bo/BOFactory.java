package lk.ijse.gdse72.layerdovinplus.bo;

import lk.ijse.gdse72.layerdovinplus.bo.custom.impl.*;
import lk.ijse.gdse72.layerdovinplus.bo.custom.impl.BatchBOIMpl;
import lk.ijse.gdse72.layerdovinplus.controller.PaymentController;
import lk.ijse.gdse72.layerdovinplus.dao.custom.PaymentDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.impl.PaymentDAOImpl;

public class BOFactory {
    private static BOFactory boFactory ;
    private BOFactory() {}
    public static BOFactory getInstance() {
        return boFactory==null?boFactory=new BOFactory():boFactory;
    }
    public enum BOType {
USER, BATCH, STUDENT, TUTE, CONTACT,EMPLOYEE,ORDER,DELIVARY,PAYMENT
    }
    public SuperBO getBo(BOType type) {
        switch (type) {
                case USER:
                return new UserBOImpl();

                case CONTACT:
                return new ContactBOImpl();

                case BATCH:
                    return new BatchBOIMpl();


                case STUDENT:
                    return new StudentBOImpl();

                    case TUTE:
                        return new TuteBOImpl();

                        case EMPLOYEE:
                            return new EmployeeBOImpl();

                            case ORDER:
                                return new OrderBOImpl();

                                case DELIVARY:
                                    return new DelivaryBOImpl();

                                    case PAYMENT:
                                        return new PaymentBOImpl();



                default:
                    return null;
        }
    }
}
