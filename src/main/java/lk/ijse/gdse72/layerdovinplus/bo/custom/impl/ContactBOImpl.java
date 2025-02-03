package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.custom.ConatctBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.ContactDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.UserDAO;
import lk.ijse.gdse72.layerdovinplus.dto.ContactDTO;
import lk.ijse.gdse72.layerdovinplus.dto.UserDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Contact;
import lk.ijse.gdse72.layerdovinplus.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactBOImpl implements ConatctBO{

    ContactDAO contactDAO = (ContactDAO) DAOFactory
            .getInstance().getDAO(DAOFactory.DAOType.CONTACT);

    @Override
    public ArrayList<ContactDTO> search(String searchTerm) throws SQLException {
        ArrayList<ContactDTO> contactDTOS = new ArrayList<>();
        ArrayList<Contact> contacts = contactDAO.search(searchTerm);
        for (Contact contact : contacts) {
            contactDTOS.add(new ContactDTO(
                    contact.getContactId(),contact.getDate(),contact.getStudentName(),contact.getContactNumber()
            ));
        }
        return contactDTOS;

    }

    @Override
    public boolean delete(String contactId) throws SQLException {
        return contactDAO.delete(contactId);

    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return contactDAO.getNextId();
    }

    @Override
    public ArrayList<ContactDTO> getAll() throws SQLException {
        ArrayList<ContactDTO> contactDTOS = new ArrayList<>();
        ArrayList<Contact> contacts = contactDAO.getAll();
        for (Contact contact : contacts) {
            contactDTOS.add(new ContactDTO(contact.getContactId(),contact.getDate(),contact.getStudentName(),contact.getContactNumber()));

        }
        return contactDTOS;

    }

    @Override
    public boolean save(ContactDTO contactDTO) throws SQLException {
        //      return   userDAO.save(new User(userDTO.getUserId(), userDTO.getUserName(), userDTO.getPassword(), userDTO.getConfirmPassword(), userDTO.getEmail()));
        return  contactDAO.save(new Contact(contactDTO.getContactId(),contactDTO.getDate(),contactDTO.getStudentName(),contactDTO.getContactNumber()));
    }

    @Override
    public boolean update(ContactDTO contactDTO) throws SQLException {
        return  contactDAO.update(new Contact(contactDTO.getContactId(),contactDTO.getDate(),contactDTO.getStudentName(),contactDTO.getContactNumber()));
    }
}
