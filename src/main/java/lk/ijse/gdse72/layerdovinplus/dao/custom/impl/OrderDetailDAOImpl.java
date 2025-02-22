package lk.ijse.gdse72.layerdovinplus.dao.custom.impl;

import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.OrderDetailDAO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(OrderDetail entity) throws SQLException {
        return SQLUtil.execute(
                "insert into orderdetail  values (?,?,?,?)",
                entity.getTuteId(),
                entity.getOrderId(),
                entity.getQuantity(),
                entity.getPrice()
        );
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException {
        return false;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<OrderDetail> search(String searchTerm) throws SQLException {
        return null;
    }

    @Override
    public OrderDetail findById(String selectedBatchId) throws SQLException {
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

}
