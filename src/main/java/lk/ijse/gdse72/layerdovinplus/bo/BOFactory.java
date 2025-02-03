package lk.ijse.gdse72.layerdovinplus.bo;

import lk.ijse.gdse72.layerdovinplus.bo.custom.impl.*;
import lk.ijse.gdse72.layerdovinplus.bo.custom.impl.BatchBOIMpl;

public class BOFactory {
    private static BOFactory boFactory ;
    private BOFactory() {}
    public static BOFactory getInstance() {
        return boFactory==null?boFactory=new BOFactory():boFactory;
    }
    public enum BOType {
        USER, BATCH, STUDENT, CONTACT
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
                default:
                    return null;
        }
    }
}
