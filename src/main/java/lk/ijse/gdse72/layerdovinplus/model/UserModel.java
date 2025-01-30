package lk.ijse.gdse72.layerdovinplus.model;

import lk.ijse.gdse72.layerdovinplus.dto.UserDTO;
import lk.ijse.gdse72.layerdovinplus.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserModel {
    public static String userId;
    public String getNextUserId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT userId FROM User ORDER BY userId DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString("userId"); // Use column name for clarity
            if (lastId != null && lastId.matches("U-\\d{3}")) { // Validate format
                int numericPart = Integer.parseInt(lastId.substring(2)); // Extract numeric part
                int newIdIndex = numericPart + 1;
                return String.format("U-%03d", newIdIndex); // Format to "U-XXX"
            }
        }

        // Default ID if no valid records are found
        return "U-001";
    }

    public  boolean saveUser(UserDTO userDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into User values (?,?,?,?,?)",
                userDTO.getUserId(),
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getConfirmPassword(),
                userDTO.getEmail()
        );
    }
    public ArrayList<UserDTO> getAllUser() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from User");

        ArrayList<UserDTO> userDTOS = new ArrayList<>();

        while (rst.next()) {
            UserDTO userDTO = new UserDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)

            );
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }
    public boolean updateUser(UserDTO userDTO) throws SQLException {
        return CrudUtil.execute(
                "update User set userName=?,userpassword=?,userconfirmPasword=?,useremail=? where userId=?",
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getConfirmPassword(),
                userDTO.getEmail(),
                userDTO.getUserId()

        );
    }
    public boolean deleteUser(String userId) throws SQLException {
        return CrudUtil.execute("delete from User where userId=?", userId);
    }
    public ArrayList<String> getAllUserIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select userId from User");

        ArrayList<String> userIds = new ArrayList<>();

        while (rst.next()) {
            userIds.add(rst.getString(1));
        }

        return userIds;
    }
    public ArrayList<UserDTO> searchUser(String searchTerm) throws SQLException {
        ArrayList<UserDTO> results = new ArrayList<>();

        // SQL query to find batches by ID or Name
        String query = "SELECT * FROM User WHERE userId LIKE ? OR userName LIKE ?";

        ResultSet rst = CrudUtil.execute(query, "%" + searchTerm + "%", "%" + searchTerm + "%");

        while (rst.next()) {
            UserDTO userDTO = new UserDTO(
                   rst.getString("userId"),
                    rst.getString("userName"),
                    rst.getString("userpassword"),
                    rst.getString("userconfirmPasword"),
                    rst.getString("useremail")
            );
            results.add(userDTO);
        }

        return results;
    }

    public UserDTO findById(String selectedUserId) throws SQLException {
        // Execute SQL query to find the item by ID
        ResultSet rst = CrudUtil.execute("select * from User where userId=?", selectedUserId);

        // If the item is found, create an ItemDTO object with the retrieved data
        if (rst.next()) {
            return new UserDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }

        // Return null if the item is not found
        return null;
    }
    public boolean searchByEmail(String email) throws SQLException {
        //String sql = "SELECT * FROM admin WHERE a_email = ?";
        ResultSet rst = CrudUtil.execute("select * from User where useremail =?", email);
        return false;
    }

    public boolean searchByName(String userName) {
        return false;
    }
}
