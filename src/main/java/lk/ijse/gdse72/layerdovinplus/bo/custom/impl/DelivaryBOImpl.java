package lk.ijse.gdse72.layerdovinplus.bo.custom.impl;

import lk.ijse.gdse72.layerdovinplus.bo.custom.DeliveryBO;
import lk.ijse.gdse72.layerdovinplus.dao.DAOFactory;
import lk.ijse.gdse72.layerdovinplus.dao.custom.DelivaryDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.EmloyeeDAO;
import lk.ijse.gdse72.layerdovinplus.dao.custom.OrderDAO;
import lk.ijse.gdse72.layerdovinplus.dto.BatchDTO;
import lk.ijse.gdse72.layerdovinplus.dto.ContactDTO;
import lk.ijse.gdse72.layerdovinplus.dto.DelivaryDTO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDTO;
import lk.ijse.gdse72.layerdovinplus.entity.Batch;
import lk.ijse.gdse72.layerdovinplus.entity.Contact;
import lk.ijse.gdse72.layerdovinplus.entity.Delivary;
import lk.ijse.gdse72.layerdovinplus.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class DelivaryBOImpl implements DeliveryBO {

    DelivaryDAO delivaryDAO = (DelivaryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DELIVARY);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);

    @Override
    public String getNextDeliveryId() throws SQLException, ClassNotFoundException {
        return delivaryDAO.getNextId();
    }

    @Override
    public ArrayList<DelivaryDTO> getAllDelivery() throws SQLException {
        ArrayList<DelivaryDTO> delivaryDTOS = new ArrayList<>();
        ArrayList<Delivary> delivaries= delivaryDAO.getAll();
        for (Delivary delivary : delivaries) {
            delivaryDTOS.add(new DelivaryDTO(delivary.getDeliveryId(),delivary.getDeliveryDate(),delivary.getStatus(),delivary.getOrderId()));

        }
        return delivaryDTOS;
    }

    @Override
    public ArrayList<DelivaryDTO> searchDelivery(String searchTerm) throws SQLException {
        ArrayList<DelivaryDTO> delivaryDTOS = new ArrayList<>();
        ArrayList<Delivary> delivaries= delivaryDAO.search(searchTerm);
        for (Delivary delivary : delivaries) {
            delivaryDTOS.add(new DelivaryDTO(delivary.getDeliveryId(),delivary.getDeliveryDate(),delivary.getStatus(),delivary.getOrderId()));

        }
        return delivaryDTOS;
    }

    @Override
    public boolean saveDelivery(DelivaryDTO delivaryDTO) throws SQLException {
        return delivaryDAO.save(new Delivary(
                delivaryDTO.getDeliveryId(),delivaryDTO.getDeliveryDate(),delivaryDTO.getStatus(),delivaryDTO.getOrderId()
        ));
    }

    @Override
    public boolean deleteDelivery(String deliveryId) throws SQLException {
        return delivaryDAO.delete(deliveryId);
    }

    @Override
    public boolean updateDelivery(DelivaryDTO delivaryDTO) throws SQLException {
        return delivaryDAO.update(new Delivary(
                delivaryDTO.getDeliveryId(),delivaryDTO.getDeliveryDate(),delivaryDTO.getStatus(),delivaryDTO.getOrderId()
        ));
    }

    @Override
    public ArrayList<String> getAllOrderIds() throws SQLException {
        ArrayList<String> orderIds = new ArrayList<>();
        ArrayList<Order> orders = orderDAO.getAll(); // Fetch all batches
        for (Order order : orders) {
            orderIds.add(order.getOrderId()); // Extract batch ID and add it to the list
        }

        return orderIds;
    }

    @Override
    public DelivaryDTO findById(String selecteDeliveryId) throws SQLException {
        Order order = orderDAO.findById(selecteDeliveryId); // Fetch batch by ID
        Delivary delivary = delivaryDAO.findById(order.getOrderId());

        if (delivary != null) {
            return new DelivaryDTO(delivary.getDeliveryId(),delivary.getDeliveryDate(),delivary.getStatus(),delivary.getOrderId());
        } else {
            return null; // Return null if batch is not found
        }
    }
}
