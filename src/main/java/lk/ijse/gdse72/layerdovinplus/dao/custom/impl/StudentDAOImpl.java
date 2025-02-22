package lk.ijse.gdse72.layerdovinplus.dao.custom.impl;

import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.StudentDAO;
import lk.ijse.gdse72.layerdovinplus.dto.BatchDTO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.OrderDetail;
import lk.ijse.gdse72.layerdovinplus.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public ArrayList<Student> getAll() throws SQLException {

        ResultSet rst = SQLUtil.execute("select * from student");

        ArrayList<Student> studentArrayList = new ArrayList<>();

        while (rst.next()) {
            studentArrayList.add(new Student(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)));
        }
        return studentArrayList;
    }

    @Override
    public boolean save(Student entity) throws SQLException {
        return SQLUtil.execute(
                "insert into student values (?,?,?,?)",
                entity.getStudentId(),
                entity.getStudentName(),
                entity.getAddress(),
                entity.getBatchId()
        );
    }

    @Override
    public boolean update(Student entity) throws SQLException {
        return SQLUtil.execute(
                "update student set StudentName=?, address=?,BatchId =? where StudentId=?",
                entity.getStudentName(),
                entity.getAddress(),
                entity.getBatchId(),
                entity.getStudentId()

        );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select StudentId  from student order by StudentId  desc limit 1");

        if (rst.next()) {

            String lastId = rst.getString(1);
            String substring = lastId.substring(4);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("STU-%03d", newIdIndex);
        }
        // If no existing ID is found, return the starting ID
        return "STU-001";

    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return SQLUtil.execute("delete from student where StudentId=?", Id);

    }

    @Override
    public ArrayList<Student> search(String searchTerm) throws SQLException {



        String query = "SELECT * FROM student WHERE StudentId LIKE ? OR StudentName LIKE ?";

        ResultSet rst = SQLUtil.execute(query, "%" + searchTerm + "%", "%" + searchTerm + "%");

        ArrayList<Student> studentArrayList = new ArrayList<>();

        while (rst.next()) {
            studentArrayList.add(new Student(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4))



            );

        }

        return studentArrayList;
    }

    @Override
    public Student findById(String selecteId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from student where StudentId=?", selecteId);

        if (rst.next()) {
            return new Student(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
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
        return false;
    }
}
