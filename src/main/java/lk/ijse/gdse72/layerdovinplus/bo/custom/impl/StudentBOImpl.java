package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import lk.ijse.gdse72.layerdovinplus.bo.custom.StudentBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.SQLUtil;
import lk.ijse.gdse72.layerdovinplus.dao.custom.BatchDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.StudentDAO;
import lk.ijse.gdse72.layerdovinplus.db.DBConnection;
import lk.ijse.gdse72.layerdovinplus.dto.BatchDTO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lk.ijse.gdse72.layerdovinplus.dto.StudentDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.OrderDetail;
import lk.ijse.gdse72.layerdovinplus.entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);
    BatchDAO batchDAO = (BatchDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BATCH);



    @Override
    public ArrayList<StudentDTO> search(String searchTerm) throws SQLException {
        ArrayList<StudentDTO> studentDTOS = new ArrayList<>();
        ArrayList<Student> students = studentDAO.search(searchTerm);
        for (Student student : students) {
            studentDTOS.add(new StudentDTO(
                    student.getStudentId(),student.getStudentName(),student.getAddress(),student.getBatchId()
            ));
        }
        return studentDTOS;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return studentDAO.getNextId();
    }

    @Override
    public ArrayList<StudentDTO> getAll() throws SQLException {
        ArrayList<StudentDTO> studentDTOS = new ArrayList<>();
        ArrayList<Student> students = studentDAO.getAll();
        for (Student student : students) {
            studentDTOS.add(new StudentDTO(student.getStudentId(),student.getStudentName(),student.getAddress(),student.getBatchId()));

        }
        return studentDTOS;

    }

    @Override
    public boolean save(StudentDTO studentDTO) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isStudentSave = studentDAO.save(new Student(studentDTO.getStudentId(),studentDTO.getStudentName(),studentDTO.getAddress(),studentDTO.getBatchId()));
            System.out.println("Student  Saved: ");

            if (!isStudentSave) {
                connection.rollback();
                return false;
            }

            boolean isBatchUpdated = batchDAO.updateBatch(new Batch(studentDTO.getBatchId(), null, 0));            System.out.println("Student  l: ");
            if (!isBatchUpdated) {
                connection.rollback();
                return false;
            }
            connection.commit(); // 5. Commit transaction if everything is successful
            return true;

        }catch (SQLException e) {
            try {
                connection.rollback(); // Rollback transaction on error
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit mode
            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }
        }

    }

    @Override
    public boolean delete(String studentId) throws SQLException {
       return studentDAO.delete(studentId);

    }

    @Override
    public boolean update(StudentDTO studentDTO) throws SQLException {
        return  studentDAO.update(new Student(
                studentDTO.getStudentId(),studentDTO.getStudentName(),studentDTO.getAddress(),studentDTO.getBatchId()));
    }

    @Override
    public ArrayList<String> getAllBatchIds() throws SQLException {
        ArrayList<String> batchIds = new ArrayList<>();
        ArrayList<Batch> batches = batchDAO.getAll(); // Fetch all batches
        for (Batch batch : batches) {
            batchIds.add(batch.getBatchID()); // Extract batch ID and add it to the list
        }

        return batchIds;
    }

    @Override
    public BatchDTO findById(String selectedBatchId) throws SQLException {
        Batch batch = batchDAO.findById(selectedBatchId); // Fetch batch by ID

        if (batch != null) {
            return new BatchDTO(batch.getBatchID(), batch.getBatchName(), batch.getStudentCount());
        } else {
            return null; // Return null if batch is not found
        }
    }

}
