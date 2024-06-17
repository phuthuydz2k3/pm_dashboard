package org.example.project_manager_dashboard.views.screens;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainView implements Initializable {

    @FXML
    private AnchorPane drawerPane;

    @FXML
    private AnchorPane contentArea;

    @FXML
    private Button btn1, btn2, toggleBtn;

    private Boolean menuOpen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawerPane.setTranslateX(-209);
        menuOpen = false;
        initializeHoverEffects();
        loadOrders();
    }

    private void initializeHoverEffects() {
        btn1.setOnMouseEntered(e -> changeButtonImage(btn1, "/images/product_white.png"));
        btn1.setOnMouseExited(e -> changeButtonImage(btn1, "/images/product_black.png"));
        btn2.setOnMouseEntered(e -> changeButtonImage(btn2, "/images/order_white.png"));
        btn2.setOnMouseExited(e -> changeButtonImage(btn2, "/images/order_black.png"));

        toggleBtn.setOnMouseEntered(e -> updateToggleBtnHoverIcon(true));
        toggleBtn.setOnMouseExited(e -> updateToggleBtnHoverIcon(false));
    }

    private void updateToggleBtnHoverIcon(boolean hover) {
        String iconPath;
        if (menuOpen) {
            iconPath = hover ? "/images/white_close.png" : "/images/black_close.png";
        } else {
            iconPath = hover ? "/images/white_open.png" : "/images/black_open.png";
        }
        changeButtonImage(toggleBtn, iconPath);
    }

    private void changeButtonImage(Button button, String imagePath) {
        ImageView imageView = (ImageView) ((HBox) button.getGraphic()).getChildren().get(0);
        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
    }

    @FXML
    private void toggleMenu() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(drawerPane);

        if (menuOpen) {
            slide.setToX(-209); // Move drawerPane out
            slide.setOnFinished((ActionEvent e) -> {
                menuOpen = false;
                updateToggleBtnHoverIcon(false); // Update the icon after the menu is closed
            });
        } else {
            slide.setToX(0); // Move drawerPane in
            slide.setOnFinished((ActionEvent e) -> {
                menuOpen = true;
                updateToggleBtnHoverIcon(false); // Update the icon after the menu is opened
            });
        }
        slide.play();
    }

    @FXML
    private void loadProducts() {
        loadPage("/fxml/products.fxml");
    }

    @FXML
    private void loadOrders() {
        loadPage("/fxml/orders.fxml");
    }

    private void loadPage(String fxmlFile) {
        try {
            AnchorPane page = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
