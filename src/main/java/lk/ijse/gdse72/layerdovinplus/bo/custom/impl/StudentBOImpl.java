package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.custom.StudentBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.custom.StudentDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.UserDAO;
import lk.ijse.gdse72.layerdovinplus.dto.ContactDTO;
import lk.ijse.gdse72.layerdovinplus.dto.StudentDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Contact;
import lk.ijse.gdse72.layerdovinplus.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory
            .getInstance().getDAO(DAOFactory.DAOType.STUDENT);
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
}
