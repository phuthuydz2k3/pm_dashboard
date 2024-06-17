package org.example.project_manager_dashboard.views.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.project_manager_dashboard.models.Product;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProductView implements Initializable {

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
    private Label supportRushDelivery;
    @FXML
    private Button checkBoxButton;

    private Boolean checked;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checked = false;
        checkBoxButton.setVisible(false); // Hide checkbox button by default
        updateCheckBox();
    }

    public void setOrderItemDetails(Product product) {
        mediaId.setText(product.getMediaId().toString());
        name.setText(product.getName());
        price.setText(product.getPrice().toString());
        available.setText(product.getAvailable().toString());
        category.setText(product.getCategory());
        weight.setText(product.getWeight().toString());
        supportRushDelivery.setText(product.getSupportRushDelivery() == 1 ? "true" : "false");
        updateStateColor(product.getSupportRushDelivery() == 1 ? "true" : "false");
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

    @FXML
    private void checkBox() {
        checked = !checked;
        updateCheckBox();
    }

    private void updateCheckBox() {
        String imageUrl = checked ? "/images/icons8-checked-box-24.png" : "/images/icons8-unchecked-checkbox-50.png";
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toExternalForm()));
        imageView.setFitHeight(20); // Set desired height
        imageView.setFitWidth(20);  // Set desired width
        checkBoxButton.setGraphic(imageView);
    }

    public void showCheckBox(boolean visible) {
        checkBoxButton.setVisible(visible);
    }

    public boolean isChecked() {
        return checked;
    }

    public Integer getMediaId() {
        return Integer.parseInt(mediaId.getText());
    }
}
