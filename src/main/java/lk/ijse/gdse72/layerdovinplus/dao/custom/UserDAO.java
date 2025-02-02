package lk.ijse.gdse72.layerdovinplus.dao.custom;

import lk.ijse.gdse72.layerdovinplus.dao.CrudDAO;
import lk.ijse.gdse72.layerdovinplus.entity.User;

public interface UserDAO extends CrudDAO<User> {
    void uniqueMethodForCustomer();
}


