package org.example.project_manager_dashboard.views.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.project_manager_dashboard.models.Order;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderView implements Initializable  {
    @FXML
    private Label ordr;

    @FXML
    private Label ship;

    @FXML
    private Label camt;

    @FXML
    private Label tot;

    @FXML
    private Label stat;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setOrderDetails(Order order) {
        ordr.setText(order.getOrderId().toString());
        ship.setText(order.getShippingAmounts().toString());
        camt.setText(order.getCartAmounts().toString());
        Double t = order.getShippingAmounts() + order.getCartAmounts();
        tot.setText(t.toString());
        stat.setText(order.getState());
        updateStateColor(order.getState());
    }

    private void updateStateColor(String state) {
        String color;

        switch (state) {
            case "Pending":
                color = "#ffbd03";
                break;
            case "Approved":
                color = "#33b249";
                break;
            case "Rejected":
                color = "#ED0800";
                break;
            case "Canceled":
                color = "#dd7973";
                break;
            default:
                color = "transparent"; // Default color if state doesn't match
        }
        stat.setStyle("-fx-background-color: " + color + ";");
    }
}

