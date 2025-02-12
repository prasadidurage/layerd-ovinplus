package lk.ijse.gdse72.layerdovinplus.dao.custom.impl;

import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.TuteDAO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.Tute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TuteDAOImpl implements TuteDAO {
    @Override
    public ArrayList<Tute> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Tute");
        ArrayList<Tute> tuteList = new ArrayList<>();

        while (rst.next()) {
            tuteList.add(new Tute(
                    rst.getString("tuteId"),
                    rst.getString("tuteName"),
                    rst.getInt("tuteQty"),
                    rst.getDouble("tutePrice")
            ));
        }
        return tuteList;

    }

    @Override
    public boolean save(Tute entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO Tute VALUES (?,?,?,?)",
                entity.getTuteId(),
                entity.getTuteName(),
                entity.getTuteQty(),
                entity.getTutePrice()
        );
    }

    @Override
    public boolean update(Tute entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE Tute SET tuteName=?, tuteQty=?, tutePrice=? WHERE tuteId=?",
                entity.getTuteName(),
                entity.getTuteQty(),
                entity.getTutePrice(),
                entity.getTuteId()
        );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT tuteId FROM Tute ORDER BY tuteId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(5);
            int newIdIndex = Integer.parseInt(substring) + 1;
            return String.format("tute-%03d", newIdIndex);
        }
        return "tute-001";
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return SQLUtil.execute("DELETE FROM Tute WHERE tuteId=?", Id);
    }

    @Override
    public ArrayList<Tute> search(String searchTerm) throws SQLException {
        ResultSet rst = SQLUtil.execute(
                "SELECT * FROM Tute WHERE tuteId LIKE ? OR tuteName LIKE ?",
                "%" + searchTerm + "%",
                "%" + searchTerm + "%"
        );

        ArrayList<Tute> tuteList = new ArrayList<>();
        while (rst.next()) {
            tuteList.add(new Tute(
                    rst.getString("tuteId"),
                    rst.getString("tuteName"),
                    rst.getInt("tuteQty"),
                    rst.getDouble("tutePrice")
            ));
        }
        return tuteList;
    }

    @Override
    public Tute findById(String selectedId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from Tute where tuteId=?", selectedId);

        // If the item is found, create an ItemDTO object with the retrieved data
        if (rst.next()) {
            return new Tute(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );
        }
        return null;
    }

    @Override
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return SQLUtil.execute(
                "update Tute set  tuteQty =  tuteQty - ? where tuteId = ?",
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getTuteId()
        );
    }
}


