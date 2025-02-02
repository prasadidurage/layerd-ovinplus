package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.SuperBO;
import lk.ijse.gdse72.layerdovinplus.bo.custom.UserBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.custom.UserDAO;
import lk.ijse.gdse72.layerdovinplus.dto.UserDTO;
import lk.ijse.gdse72.layerdovinplus.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;



public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory
            .getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public ArrayList<UserDTO> getAll() throws SQLException {
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        ArrayList<User> users = userDAO.getAll();
        for (User user : users) {
            userDTOS.add(new UserDTO(user.getUserId(), user.getUserName(), user.getPassword(), user.getConfirmPassword(), user.getEmail()));

        }
        return userDTOS;
    }

    @Override
    public boolean save(UserDTO userDTO) throws SQLException, ClassNotFoundException {
      return   userDAO.save(new User(userDTO.getUserId(), userDTO.getUserName(), userDTO.getPassword(), userDTO.getConfirmPassword(), userDTO.getEmail()));
    }

    @Override
    public boolean update(UserDTO userDTO) throws SQLException, ClassNotFoundException {
      return   userDAO.update(new User(userDTO.getUserId(), userDTO.getUserName(), userDTO.getPassword(), userDTO.getConfirmPassword(), userDTO.getEmail()));

    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return userDAO.getNextId();
    }

    @Override
    public boolean delete(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.delete(userId);
    }

    @Override
    public ArrayList<UserDTO> search(String searchTerm) throws SQLException {
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        ArrayList<User> users = userDAO.search(searchTerm);
        for (User user : users) {
            userDTOS.add(new UserDTO(user.getUserId(), user.getUserName(), user.getPassword(), user.getConfirmPassword(), user.getEmail()));

        }
        return userDTOS;
    }
}
