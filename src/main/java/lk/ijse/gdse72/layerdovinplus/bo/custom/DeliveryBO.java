package lk.ijse.gdse72.layerdovinplus.bo.custom;

import lk.ijse.gdse72.layerdovinplus.bo.SuperBO;
import lk.ijse.gdse72.layerdovinplus.dto.DelivaryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryBO extends SuperBO {
    String getNextDeliveryId() throws SQLException, ClassNotFoundException;
    ArrayList<DelivaryDTO> getAllDelivery() throws SQLException;
    ArrayList<DelivaryDTO> searchDelivery(String searchTerm) throws SQLException;

    boolean saveDelivery(DelivaryDTO delivaryDTO) throws SQLException;

    boolean deleteDelivery(String deliveryId) throws SQLException;

    boolean updateDelivery(DelivaryDTO delivaryDTO) throws SQLException;

    ArrayList<String> getAllOrderIds() throws SQLException;

    DelivaryDTO findById(String selecteDeliveryId) throws SQLException;
}
