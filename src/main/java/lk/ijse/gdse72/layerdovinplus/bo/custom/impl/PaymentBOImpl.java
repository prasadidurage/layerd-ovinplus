package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.custom.PaymentBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.custom.DelivaryDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.PaymentDAO;
import lk.ijse.gdse72.layerdovinplus.dto.DelivaryDTO;
import lk.ijse.gdse72.layerdovinplus.dto.PaymentDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Delivary;
import lk.ijse.gdse72.layerdovinplus.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    DelivaryDAO delivaryDAO = (DelivaryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DELIVARY);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public ArrayList<PaymentDTO> searchPayment(String searchTerm) throws SQLException {
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        ArrayList<Payment> payments = paymentDAO.search(searchTerm);
        for (Payment payment : payments) {
            paymentDTOS.add(new PaymentDTO(
                    payment.getPaymentId(),payment.getDeliveryId(),payment.getPaymentDate(),payment.getAmount()));
        }
        return paymentDTOS;
    }

    @Override
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNextId();
    }

    @Override
    public ArrayList<PaymentDTO> getAllPayment() throws SQLException {
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        ArrayList<Payment> payments = paymentDAO.getAll();
        for (Payment payment : payments) {
            paymentDTOS.add(new PaymentDTO(
                    payment.getPaymentId(),payment.getDeliveryId(),payment.getPaymentDate(),payment.getAmount()));
        }
        return paymentDTOS;
    }

    @Override
    public boolean deletePayment(String paymentId) throws SQLException {
        return paymentDAO.delete(paymentId);
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException {
        return paymentDAO.save(new Payment(
                paymentDTO.getPaymentId(),paymentDTO.getDeliveryId(),paymentDTO.getPaymentDate(),paymentDTO.getAmount()
        ));
    }

    @Override
    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException {
        return paymentDAO.update(new Payment(
                paymentDTO.getPaymentId(),paymentDTO.getDeliveryId(),paymentDTO.getPaymentDate(),paymentDTO.getAmount()
        ));
    }

    @Override
    public ArrayList<String> getAllDeliveryIds() throws SQLException {
        ArrayList<String> delivaryIds = new ArrayList<>();
        ArrayList<Delivary> delivaries = delivaryDAO.getAll(); // Fetch all batches
        for (Delivary delivary : delivaries) {
            delivaryIds.add(delivary.getDeliveryId()); // Extract batch ID and add it to the list
        }

        return delivaryIds;
    }

    @Override
    public DelivaryDTO findById(String selectedId) throws SQLException {
        Delivary delivary = delivaryDAO.findById(selectedId); // Fetch batch by ID

        if (delivary != null) {
            return new DelivaryDTO(delivary.getDeliveryId(),delivary.getDeliveryDate(),delivary.getStatus(),delivary.getOrderId());

        } else {
            return null; // Return null if batch is not found
        }
    }
}
