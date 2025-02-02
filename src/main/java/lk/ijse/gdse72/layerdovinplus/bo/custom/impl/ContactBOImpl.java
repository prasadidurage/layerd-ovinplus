package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.custom.ConatctBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.custom.ContactDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.UserDAO;
import lk.ijse.gdse72.layerdovinplus.dto.ContactDTO;
import lk.ijse.gdse72.layerdovinplus.dto.UserDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Contact;
import lk.ijse.gdse72.layerdovinplus.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class ContactBOImpl implements ConatctBO{

    ContactDAO contactDAO = (ContactDAO) DAOFactory
            .getInstance().getDAO(DAOFactory.DAOType.CONTACT);

    @Override
    public ArrayList<ContactDTO> search(String searchName, String contactNumber) throws SQLException {
        ArrayList<ContactDTO> contactDTOS = new ArrayList<>();
        ArrayList<Contact> contacts = contactDAO.search(searchName,contactNumber);
        for (Contact contact : contacts) {
            contactDTOS.add(new ContactDTO(
                    contact.getContactId(),contact.getDate(),contact.getStudentName(),contact.getContactNumber()
            ));
        }
        return contactDTOS;

    }

    @Override
    public boolean delete(String contactId) {

    }
}
