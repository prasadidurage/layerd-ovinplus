package lk.ijse.gdse72.layerdovinplus.dao.custom;

import lk.ijse.gdse72.layerdovinplus.dao.CrudDAO;
import lk.ijse.gdse72.layerdovinplus.entity.Contact;

public interface ContactDAO extends CrudDAO<Contact> {
    void uniqueMethodForCustomer() ;
}
