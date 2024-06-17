package org.example.project_manager_dashboard.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.project_manager_dashboard.models.OrderItemDTO;

public class OrderItemController {

    @FXML
    private Label mediaId;

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private Label available;

    @FXML
    private Label category;

    @FXML
    private Label weight;

    @FXML
    private Label quantity;

    @FXML
    private Label supportRushDelivery;

    public void setOrderItemDetails(OrderItemDTO orderItem) {
        mediaId.setText(orderItem.getMediaId().toString());
        name.setText(orderItem.getName());
        price.setText(orderItem.getPrice().toString());
        available.setText(orderItem.getAvailable().toString());
        category.setText(orderItem.getCategory());
        weight.setText(orderItem.getWeight().toString());
        quantity.setText(orderItem.getQuantity().toString());
        supportRushDelivery.setText(orderItem.getSupportRushDelivery().toString());
        updateStateColor(orderItem.getSupportRushDelivery().toString());
    }

    private void updateStateColor(String state) {
        String color;
        switch (state) {
            case "true":
                color = "#4681f4";
                break;
            case "false":
                color = "#ED0800";
                break;
            default:
                color = "transparent"; // Default color if state doesn't match
        }
        supportRushDelivery.setStyle("-fx-background-color: " + color + ";");
    }
}

