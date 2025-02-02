package lk.ijse.gdse72.layerdovinplus.bo.custom;

import lk.ijse.gdse72.layerdovinplus.bo.SuperBO;
import lk.ijse.gdse72.layerdovinplus.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {

    ArrayList<UserDTO> getAll() throws SQLException, ClassNotFoundException;

   // boolean saveUser(UserDTO userDTO)throws SQLException, ClassNotFoundException;

    boolean save(UserDTO userDTO)throws SQLException, ClassNotFoundException;

    boolean update(UserDTO userDTO)throws SQLException, ClassNotFoundException;

    String getNextId() throws SQLException, ClassNotFoundException;;

    boolean delete(String userId) throws SQLException,ClassNotFoundException;

    ArrayList<UserDTO> search(String searchTerm) throws SQLException;

    ;
}
