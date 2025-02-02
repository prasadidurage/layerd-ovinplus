package lk.ijse.gdse72.layerdovinplus.bo;

import lk.ijse.gdse72.layerdovinplus.bo.custom.impl.ContactBOImpl;
import lk.ijse.gdse72.layerdovinplus.bo.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory ;
    private BOFactory() {}
    public static BOFactory getInstance() {
        return boFactory==null?boFactory=new BOFactory():boFactory;
    }
    public enum BOType {
        USER,CONTACT
    }
    public SuperBO getBo(BOType type) {
        switch (type) {
            case USER:
                return new UserBOImpl();

            case CONTACT:
                return new ContactBOImpl();

                default:
                    return null;
        }
    }
}
