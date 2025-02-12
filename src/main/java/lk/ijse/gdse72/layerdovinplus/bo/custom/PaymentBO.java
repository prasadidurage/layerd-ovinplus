package lk.ijse.gdse72.layerdovinplus.bo.custom;

import lk.ijse.gdse72.layerdovinplus.bo.SuperBO;
import lk.ijse.gdse72.layerdovinplus.dto.DelivaryDTO;
import lk.ijse.gdse72.layerdovinplus.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    ArrayList<PaymentDTO> searchPayment(String searchTerm) throws SQLException;

    String getNextPaymentId() throws SQLException, ClassNotFoundException;

    ArrayList<PaymentDTO> getAllPayment() throws SQLException;

    boolean deletePayment(String paymentId) throws SQLException;

    boolean savePayment(PaymentDTO paymentDTO) throws SQLException;

    boolean updatePayment(PaymentDTO paymentDTO) throws SQLException;

    ArrayList<String> getAllDeliveryIds() throws SQLException;

    DelivaryDTO findById(String selectedId) throws SQLException;
}
