package org.example.project_manager_dashboard.views.screens;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.project_manager_dashboard.controllers.OrdersController;
import org.example.project_manager_dashboard.views.items.OrderView;
import org.example.project_manager_dashboard.models.DeliveryInfo;
import org.example.project_manager_dashboard.models.Order;
import org.example.project_manager_dashboard.models.OrderItemDTO;
import org.example.project_manager_dashboard.controllers.OrderItemController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OrdersView implements Initializable {

    @FXML
    private VBox ordersContainer;
    @FXML
    private VBox orderItemsContainer; // Updated to use OrderItemDTO
    @FXML
    private ScrollPane sp;
    @FXML
    private ScrollPane spp; // ScrollPane for order items TableView

    @FXML
    private Label fullName;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label email;
    @FXML
    private Label province;
    @FXML
    private Label address;
    @FXML
    private Label rushDeliveryTime;
    @FXML
    private Label shippingGuide;

    @FXML
    private Button approveBtn;
    @FXML
    private Button rejectBtn;

    private Order selectedOrder;

    @FXML
    private TextField mediaSearch, fromAmounts, toAmounts;
    @FXML
    private ChoiceBox<String> amountsType, statusType;

    private final OrdersController ordersController = OrdersController.getHomeController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Retrieve the list of orders from the singleton instance of OrdersController
        List<Order> orderList = ordersController.getOrderList();
        for (Order order : orderList) {
            addOrder(order);
        }

        // Update content of ScrollPanes
        sp.setContent(ordersContainer);
        spp.setContent(orderItemsContainer); // Update content of the order items ScrollPane

        approveBtn.addEventHandler(ActionEvent.ACTION, event -> approve());
        rejectBtn.addEventHandler(ActionEvent.ACTION, event -> reject());
        approveBtn.setVisible(false);
        rejectBtn.setVisible(false);

        amountsType.getItems().addAll("Shipping", "Cart", "Total");
        amountsType.setValue("Shipping");
        statusType.getItems().addAll("Pending", "Approved", "Rejected", "Cancelled", "All");
        statusType.setValue("All");
    }

    @FXML
    private void searchOrders() {
        String mediaId = mediaSearch.getText();
        String selectedAmountType = amountsType.getValue();
        String fromAmountStr = fromAmounts.getText();
        String toAmountStr = toAmounts.getText();
        String selectedStatus = statusType.getValue();

        Float fromAmount = fromAmountStr.isEmpty() ? null : Float.parseFloat(fromAmountStr);
        Float toAmount = toAmountStr.isEmpty() ? null : Float.parseFloat(toAmountStr);

        // Mock data for orders (replace with actual data retrieval logic)
        List<Order> orders = ordersController.getOrderList();

        System.out.println(mediaId);
        List<Order> filteredOrders = orders.stream()
                .filter(order -> mediaId.isEmpty() || order.getOrderId().toString().contains(mediaId))
                .filter(order -> selectedStatus == null || selectedStatus.equals("All") || order.getState().equalsIgnoreCase(selectedStatus))
                .filter(order -> {
                    Float amount = getOrderAmount(order, selectedAmountType);
                    if (amount == null) {
                        return true;
                    }
                    if (fromAmount != null && toAmount != null) {
                        return amount >= fromAmount && amount <= toAmount;
                    } else if (fromAmount != null) {
                        return amount >= fromAmount;
                    } else if (toAmount != null) {
                        return amount <= toAmount;
                    } else {
                        return true;
                    }
                })
                .collect(Collectors.toList());

        displayOrders(filteredOrders);
    }

    private void displayOrders(List<Order> orders) {
        ordersContainer.getChildren().clear();
        for (Order order : orders) {
            addOrder(order);
        }
    }


    private Float getOrderAmount(Order order, String amountType) {
        if (amountType == null) {
            return null;
        }

        switch (amountType) {
            case "Shipping":
                return order.getShippingAmounts();
            case "Cart":
                return order.getCartAmounts();
            case "Total":
                return order.getTotalAmounts();
            default:
                return (float) 0.0;
        }
    }

    private void addOrder(Order order) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/order.fxml"));
            AnchorPane orderItem = loader.load();

            // Get the controller for the order item and set the values
            OrderView controller = loader.getController();
            controller.setOrderDetails(order);

            orderItem.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    displayOrderDetails(order);
                }
            });

            ordersContainer.getChildren().add(orderItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayOrderDetails(Order selectedOrder) {
        OrdersController ordersController = OrdersController.getHomeController();

        // Clear order items container
        orderItemsContainer.getChildren().clear();

        // Fetch and display order items for the selected order ID
        List<OrderItemDTO> selectedOrderItems = ordersController.getOrderItems(selectedOrder.getOrderId());
        for (OrderItemDTO orderItem : selectedOrderItems) {
            addOrderItem(orderItem);
        }

        DeliveryInfo deliveryInfo = selectedOrder.getDeliveryInfo();
        if (deliveryInfo != null) {
            fullName.setText(deliveryInfo.getName());
            phoneNumber.setText(deliveryInfo.getPhoneNumber());
            email.setText(deliveryInfo.getEmail());
            province.setText(deliveryInfo.getProvince());
            address.setText(deliveryInfo.getAddress());
            shippingGuide.setText(deliveryInfo.getInstruction());
            // Convert short value to boolean for rush delivery
            boolean isRushDelivery = deliveryInfo.getIsRush() != null && deliveryInfo.getIsRush() == 1;
            rushDeliveryTime.setText(isRushDelivery ? "Rush Delivery" : "Standard Delivery");
        } else {
            // Clear delivery info fields if no delivery info found for the order
            fullName.setText("N/A");
            phoneNumber.setText("N/A");
            email.setText("N/A");
            province.setText("N/A");
            address.setText("N/A");
            rushDeliveryTime.setText("N/A");
            shippingGuide.setText("N/A");
        }

        this.selectedOrder = selectedOrder;

        updateButtonVisibility(selectedOrder.getState());
    }

    private void addOrderItem(OrderItemDTO orderItem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/orderItem.fxml"));
            AnchorPane orderItemPane = loader.load();

            // Get the controller for the order item and set the values
            OrderItemController controller = loader.getController();
            controller.setOrderItemDetails(orderItem);

            orderItemsContainer.getChildren().add(orderItemPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateButtonVisibility(String state) {
        boolean isPending = "Pending".equals(state);
        approveBtn.setVisible(isPending);
        rejectBtn.setVisible(isPending);
    }

    @FXML
    private void approve() {
        if (selectedOrder != null && selectedOrder.getState().equals("Pending")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to approve this order?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    selectedOrder.setState("Approved");
                    OrdersController.getHomeController().updateOrderState(selectedOrder);
                    updateButtonVisibility(null);
                }
            });
        }
    }

    @FXML
    private void reject() {
        if (selectedOrder != null && selectedOrder.getState().equals("Pending")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to reject this order?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    selectedOrder.setState("Rejected");
                    OrdersController.getHomeController().updateOrderState(selectedOrder);
                    refreshOrderList();
                    updateButtonVisibility(null);
                }
            });
        }
    }

    private void refreshOrderList() {
        List<Order> updatedOrderList = ordersController.getOrderList();
        ordersContainer.getChildren().clear();
        for (Order order : updatedOrderList) {
            addOrder(order);
        }
    }
}
