package lk.ijse.gdse72.layerdovinplus.dao.custom.impl;

import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.BatchDAO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BatchDAOImpl implements BatchDAO {
    @Override
    public ArrayList<Batch> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from batch");

        ArrayList<Batch> batchArrayList = new ArrayList<>();

        while (rst.next()) {
            batchArrayList.add(new Batch(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)

            ));

        }
        return batchArrayList;
    }

    @Override
    public boolean save(Batch entity) throws SQLException {
        return SQLUtil.execute(
                "insert into batch values (?,?,?)",
                entity.getBatchID(),
                entity.getBatchName(),
                entity.getStudentCount()
        );
    }

    @Override
    public boolean update(Batch entity) throws SQLException {
        return SQLUtil.execute(
                "update batch set BatchName=?, Studentcount=? where BatchId=?",
                entity.getBatchName(),
                entity.getStudentCount(),
                entity.getBatchID()

        );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select BatchId from batch order by BatchId  desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("B%03d", newIdIndex);
        }
        return "B001";

    }

    @Override
    public boolean delete(String BatchId) throws SQLException {
        return SQLUtil.execute("delete from batch where BatchId=?", BatchId);
    }

    @Override
    public ArrayList<Batch> search(String searchTerm) throws SQLException {
        ArrayList<Batch> batchArrayList = new ArrayList<>();

        // SQL query to find batches by ID or Name
        String query = "SELECT * FROM batch WHERE BatchId LIKE ? OR BatchName LIKE ?";

        ResultSet rst = SQLUtil.execute(query, "%" + searchTerm + "%", "%" + searchTerm + "%");

        while (rst.next()) {
            batchArrayList.add(new Batch(
                    rst.getString("BatchId"),
                    rst.getString("BatchName"),
                    rst.getInt("StudentCount")
            ));
        }

        return batchArrayList;

    }

    @Override
    public Batch findById(String selectedBatchId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM batch WHERE BatchId=?", selectedBatchId);

        if (rst.next()) {
            return new Batch(
                    rst.getString("BatchId"),
                    rst.getString("BatchName"),
                    rst.getInt("StudentCount")
            );
        }

        return null;
    }

    @Override
    public boolean reduceQty(OrderDetail orderDetail) throws SQLException {
        return false;
    }

    @Override
    public boolean updateBatch(Batch batch) throws SQLException {
        return SQLUtil.execute("UPDATE batch SET StudentCount = StudentCount + 1 WHERE BatchId = ?",
                batch.getBatchID()


        );
    }

    @Override
    public boolean reduceStudentCount(String batchId) throws SQLException {
        return SQLUtil.execute(
                "UPDATE batch SET StudentCount = StudentCount - 1 WHERE BatchId = ?", batchId
        );
    }
}
