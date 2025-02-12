package lk.ijse.gdse72.layerdovinplus.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.layerdovinplus.bo.BOFactory;
import lk.ijse.gdse72.layerdovinplus.bo.custom.EmployeeBO;
import lk.ijse.gdse72.layerdovinplus.bo.custom.OrderBO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDTO;
import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lk.ijse.gdse72.layerdovinplus.dto.StudentDTO;
import lk.ijse.gdse72.layerdovinplus.dto.TuteDTO;
import lk.ijse.gdse72.layerdovinplus.dto.tm.PlaceOrderTM;
//import lk.ijse.gdse72.ovinplus.model.OrderModel;
//import lk.ijse.gdse72.ovinplus.model.StudentModel;
//import lk.ijse.gdse72.ovinplus.model.TuteModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML
    private AnchorPane orderPane;

    @FXML
    private Button btnOrderView;

    @FXML
    private Button btnAddtoCart;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnReset;

    @FXML
    public ComboBox<String> cmbStudentId;

    @FXML
    private ComboBox<String> cmbTuteId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colTuteId;

    @FXML
    private TableColumn<?, ?> colTuteName;

    @FXML
    private Label lblTuteQty;

    @FXML
    public Label lblOrderId;

    @FXML
    private Label lblStudentName;

    @FXML
    private Label lblTuteName;

    @FXML
    private Label lblTutePrice;

    @FXML
    public Label orderDate;

    @FXML
    private TableView<PlaceOrderTM> tblCart;

    @FXML
    private TextField txtAddToCartQty;

//    private final OrderModel orderModel = new OrderModel();
//    private final StudentModel studentModel = new StudentModel();
//    private final TuteModel tuteModel = new TuteModel();

    // Observable list to manage cart items in TableView
    private final ObservableList<PlaceOrderTM> placeOrderTMS = FXCollections.observableArrayList();
    OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBo(BOFactory.BOType.ORDER);



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("OrderController");
        setCellValues();
        System.out.println("ssd");

        // Load data and initialize the page
        try {
            refreshPage();
            System.out.println("dd");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private void setCellValues() {
        colTuteId.setCellValueFactory(new PropertyValueFactory<>("tuteId"));
        colTuteName.setCellValueFactory(new PropertyValueFactory<>("tuteName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        // Bind the cart items observable list to the TableView
        tblCart.setItems(placeOrderTMS);
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        // Get the next order ID and set it to the label
        lblOrderId.setText(orderBO.getNextOrderId());

        // Set the current date to the order date label
//        orderDate.setText(String.valueOf(LocalDate.now()));
        orderDate.setText(LocalDate.now().toString());

        // Load customer and item IDs into ComboBoxes
        loadStudentIds();
        loadTuteId();

//        ComboBox on action set
//        cmbCustomerId.setOnAction(e->{
//            String selectedCusId = cmbCustomerId.getSelectionModel().getSelectedItem();
//            System.out.println(selectedCusId);
//        });

        // Clear selected customer, item, and their associated labels
        cmbStudentId.getSelectionModel().clearSelection();
        cmbTuteId.getSelectionModel().clearSelection();
        lblTuteName.setText("");
        lblTuteQty.setText("");
        lblTutePrice.setText("");
        txtAddToCartQty.setText("");
        lblStudentName.setText("");

        // Clear the cart observable list
        placeOrderTMS.clear();

        // Refresh the table to reflect changes
        tblCart.refresh();
    }


    // methana poddak blnn
    private void loadTuteId() throws SQLException {
//        ArrayList<String> batchIds = studentBO.getAllBatchIds();
//        ObservableList<String> observableList = FXCollections.observableArrayList();
//        observableList.addAll(batchIds);
//        cmbBatch.setItems(observableList)

        ArrayList<String> tuteIds = orderBO.getAlltuteIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(tuteIds);
        cmbTuteId.setItems(observableList);
    }

    private void loadStudentIds() throws SQLException {
        ArrayList<String> studentIds = orderBO.getAllStudentIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(studentIds);
        cmbStudentId.setItems(observableList);
    }


    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedTuteId = cmbTuteId.getValue();

        // If no item is selected, show an error alert and return
        if (selectedTuteId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select Tute..!").show();
            return; // Exit the method if no item is selected.
        }

        String quantityPattern = "^[0-9]+$";
        String pricePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
//        right :- 500.00. 500.65, 500,
//        wrong :- 787.8, 6777.9999

        boolean isValidQty = txtAddToCartQty.getText().matches(quantityPattern);
        boolean isValidPrice = txtAddToCartQty.getText().matches(pricePattern);

        if (!isValidQty){
            new Alert(Alert.AlertType.ERROR,"Invalid qty").show();
            return;
        }
        if (!isValidPrice){
            new Alert(Alert.AlertType.ERROR,"Invalid Price").show();
            return;
        }

        String tuteName = lblTuteName.getText();
        int cartQty = Integer.parseInt(txtAddToCartQty.getText());
        int qtyOnHand = Integer.parseInt(lblTuteQty.getText());

        // Check if there are enough items in stock; if not, show an error alert and return
        if (qtyOnHand < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough Tutes..!").show();
            return; // Exit the method if there's insufficient stock.
        }

        // Clear the text field for adding quantity after retrieving the input value.
        txtAddToCartQty.setText("");

        double unitPrice = Double.parseDouble(lblTutePrice.getText());
        double total = unitPrice * cartQty;

        // Loop through each item in cart's observable list.
        for (PlaceOrderTM placeOrderTM : placeOrderTMS) {

            // Check if the item is already in the cart
            if (placeOrderTM.getTuteId().equals(selectedTuteId)) {
                // Update the existing CartTM object in the cart's observable list with the new quantity and total.
                int newQty = placeOrderTM.getCartQuantity() + cartQty;
                placeOrderTM.setCartQuantity(newQty); // Add the new quantity to the existing quantity in the cart.
                placeOrderTM.setTotal(unitPrice * newQty); // Recalculate the total price based on the updated quantity

                // Refresh the table to display the updated information.
                tblCart.refresh();
                return; // Exit the method as the cart item has been updated.
            }
        }


        // Create a "Remove" button for the item to allow it to be removed from the cart later.
        Button btn = new Button("Remove");

        // If the item does not already exist in the cart, create a new CartTM object to represent it.
        PlaceOrderTM newPlaceOrderTM = new PlaceOrderTM(
                selectedTuteId,
                tuteName,
                cartQty,
                unitPrice,
                total,
                btn
        );

        // Set an action for the "Remove" button, which removes the item from the cart when clicked.
        btn.setOnAction(actionEvent -> {

            // Remove the item from the cart's observable list (cartTMS).
            placeOrderTMS.remove(newPlaceOrderTM);

            // Refresh the table to reflect the removal of the item.
            tblCart.refresh();
        });

        // Add the newly created CartTM object to the cart's observable list.
        placeOrderTMS.add(newPlaceOrderTM);

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add Tute to cart..!").show();
            return;
        }
        if (cmbStudentId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select Student for place order..!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(orderDate.getText());
        String studentId = cmbStudentId.getValue();

        // List to hold order details
        ArrayList<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();

        // Collect data for each item in the cart and add to order details array
        for (PlaceOrderTM placeOrderTM : placeOrderTMS) {

            // Create order details for each cart item
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    orderId,
                    placeOrderTM.getTuteId(),
                    placeOrderTM.getCartQuantity(),
                    placeOrderTM.getUnitPrice()
            );

            // Add to order details array
            orderDetailsDTOS.add(orderDetailsDTO);
        }

        // Create an OrderDTO with relevant order data
        OrderDTO orderDTO = new OrderDTO(
                orderId,
                dateOfOrder,
                studentId,
                orderDetailsDTOS
        );

        boolean isSaved = orderBO.saveOrder(orderDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order saved..!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order fail..!").show();
        }


    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();

    }

    @FXML
    void cmbStudentOnAction(ActionEvent event) throws SQLException {
        String selectedStudentId = cmbStudentId.getSelectionModel().getSelectedItem();
        StudentDTO studentDTO = orderBO.findByStudentId(selectedStudentId);

        // If customer found (customerDTO not null)
        if (studentDTO != null) {

            // FIll customer related labels
            lblStudentName.setText(studentDTO.getStudentName());
        }


    }

    @FXML
    void cmbTuteOnAction(ActionEvent event) throws SQLException {
        String selectedTuteId = cmbTuteId.getSelectionModel().getSelectedItem();
        TuteDTO tuteDTO = orderBO.findByTuteFId(selectedTuteId);

        // If customer found (customerDTO not null)
        if (tuteDTO != null) {

            // FIll customer related label
            lblTuteName.setText(tuteDTO.getTuteName());
            lblTuteQty.setText(String.valueOf(tuteDTO.getTuteQty()));
            lblTutePrice.setText(String.valueOf(tuteDTO.getTutePrice()));
        }



    }
    @FXML
    void btnOrderViewOnAction(ActionEvent event) {
        try {
            orderPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/OrderTable.fxml"));


            load.prefWidthProperty().bind(orderPane.widthProperty());
            load.prefHeightProperty().bind(orderPane.heightProperty());

            orderPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }

    }


}