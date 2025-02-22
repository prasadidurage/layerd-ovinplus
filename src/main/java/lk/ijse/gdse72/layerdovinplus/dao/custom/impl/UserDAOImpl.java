package lk.ijse.gdse72.layerdovinplus.dao.custom.impl;

import lk.ijse.gdse72.layerdovinplus.dao.custom.UserDAO;
import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lk.ijse.gdse72.layerdovinplus.dto.UserDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.OrderDetail;
import lk.ijse.gdse72.layerdovinplus.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from User");

        ArrayList<User> userDTOArrayList = new ArrayList<>();

        while (rst.next()) {
            userDTOArrayList.add(new User(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5)));


        }
        return userDTOArrayList;
    }

    @Override
    public boolean save(User entity) throws SQLException {
        return SQLUtil.execute(
                "insert into User values (?,?,?,?,?)",
                entity.getUserId(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getConfirmPassword(),
                entity.getEmail()
        );
    }

    @Override
    public boolean update(User entity) throws SQLException {
        return SQLUtil.execute(
                "update User set userName=?,userpassword=?,userconfirmPasword=?,useremail=? where userId=?",
                entity.getUserName(),
                entity.getPassword(),
                entity.getConfirmPassword(),
                entity.getEmail(),
                entity.getUserId()

        );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rs = SQLUtil.execute("SELECT userId FROM User ORDER BY userId DESC LIMIT 1");
        return (rs.next() && rs.getString("userId").matches("U-\\d{3}"))
                ? String.format("U-%03d", Integer.parseInt(rs.getString("userId").substring(2)) + 1)
                : "U-001";
    }

    @Override
    public boolean delete(String userId) throws SQLException {
        return SQLUtil.execute("delete from User where userId=?", userId);
    }

    @Override
    public ArrayList<User> search(String searchTerm) throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT * FROM User WHERE userId LIKE ? OR userName LIKE ?",
                "%" + searchTerm + "%", "%" + searchTerm + "%");
        List<User> userList = new ArrayList<>();

        while (rs.next()) userList.add(new User(
                rs.getString("userId"), rs.getString("userName"),
                rs.getString("userpassword"), rs.getString("userconfirmPasword"),
                rs.getString("useremail")
        ));

//        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE id=?",id);
//        rst.next();
//        return new Customer(rst.getString("id"),
//                rst.getString("name"),rst.getString("address"));
//    }
        return (ArrayList<User>) userList;
    }

    @Override
    public User findById(String selectedBatchId) throws SQLException {
        return null;
    }

    @Override
    public boolean reduceQty(OrderDetail orderDetail) throws SQLException {
        return false;
    }

    @Override
    public boolean updateBatch(Batch batch) throws SQLException {
        return false;
    }

    @Override
    public void uniqueMethodForCustomer() {

    }
}
