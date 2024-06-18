package org.example.project_manager_dashboard.views.screens;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.project_manager_dashboard.views.items.ProductView;
import org.example.project_manager_dashboard.controllers.ProductsController;
import org.example.project_manager_dashboard.models.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsView implements Initializable {
    @FXML
    private VBox mediaTable;

    @FXML
    private Button addMediaBtn;

    @FXML
    private Button selectForDeletionBtn;

    @FXML
    private Button cancelDeletion;

    @FXML
    private Button confirmDeletion;


    private Stage productFormStage;

    private final ProductsController productsController = ProductsController.getProductsController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Load data from the database and set it to the table
        List<Product> productList = productsController.getProductList();
        for (Product product : productList) {
            addProduct(product);
        }

        addMediaBtn.addEventHandler(ActionEvent.ACTION, event -> addProduct());
        selectForDeletionBtn.addEventHandler(ActionEvent.ACTION, event -> selectForDeletion());

        cancelDeletion.setVisible(false);
        confirmDeletion.setVisible(false);
    }

    private void addProduct(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product.fxml"));
            AnchorPane mediaItemPane = loader.load();

            // Get the controller for the product item and set the values
            ProductView controller = loader.getController();
            controller.setOrderItemDetails(product);

            mediaItemPane.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> showProductDetails(product));

            // Set the controller as user data for later retrieval
            mediaItemPane.setUserData(controller);

            mediaTable.getChildren().add(mediaItemPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showProductDetails(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/productDetail.fxml"));
            Parent root = loader.load();
            Stage productDetailStage = new Stage();
            productDetailStage.initModality(Modality.APPLICATION_MODAL);
            productDetailStage.initStyle(StageStyle.UTILITY);
            productDetailStage.setTitle("Product Details");

            // Get the controller of the loaded FXML and set the product details
            ProductDetailView productDetailController = loader.getController();
            productDetailController.setProduct(product);
            productDetailController.setProductsViewCallback(this);

            productDetailStage.setScene(new Scene(root));
            productDetailStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void addProduct() {
        try {
            if (productFormStage == null || !productFormStage.isShowing()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/productForm.fxml"));
                Parent root = loader.load();
                productFormStage = new Stage();
                productFormStage.initModality(Modality.APPLICATION_MODAL);
                productFormStage.initStyle(StageStyle.UTILITY);
                productFormStage.setTitle("Add Product");

                // Get the controller of the loaded FXML
                ProductFormView productFormController = loader.getController();

                // Set callback for successful media addition
                productFormController.setOnMediaAdded(() -> {
                    // Refresh product list
                    refreshProductList();
                });

                productFormStage.setScene(new Scene(root));
                productFormStage.show();
            } else {
                productFormStage.toFront();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void selectForDeletion() {
        showCheckBoxes(true);
        cancelDeletion.setVisible(true);
        confirmDeletion.setVisible(true);
        addMediaBtn.setVisible(false);
        selectForDeletionBtn.setVisible(false);
    }

    private void showCheckBoxes(boolean visible) {
        for (Node node : mediaTable.getChildren()) {
            if (node instanceof AnchorPane) {
                ProductView productView = (ProductView) ((AnchorPane) node).getUserData();
                productView.showCheckBox(visible);
            }
        }
    }

    @FXML
    private void cancelDeletion() {
        cancelDeletion.setVisible(false);
        confirmDeletion.setVisible(false);
        addMediaBtn.setVisible(true);
        selectForDeletionBtn.setVisible(true);
        for (Node node : mediaTable.getChildren()) {
            if (node instanceof AnchorPane) {
                ProductView productView = (ProductView) ((AnchorPane) node).getUserData();
                if (productView.isChecked()) {
                    productView.checkBox();
                }
            }
        }
        showCheckBoxes(false);
    }

    @FXML
    private void confirmDeletion() {
        cancelDeletion.setVisible(false);
        confirmDeletion.setVisible(false);
        addMediaBtn.setVisible(true);
        selectForDeletionBtn.setVisible(true);

        int checked = 0;
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node : mediaTable.getChildren()) {
            if (node instanceof AnchorPane) {
                ProductView productView = (ProductView) ((AnchorPane) node).getUserData();
                if (productView.isChecked()) {
                    checked++;
                }
            }
        }
        if (checked <= 10) {
            DailyCounter dailyCounter = productsController.getDailyCounter();
            int sum = checked + dailyCounter.getCounterValue();
            if (sum <= 30) {
                for (Node node : mediaTable.getChildren()) {
                    if (node instanceof AnchorPane) {
                        ProductView productView = (ProductView) ((AnchorPane) node).getUserData();
                        if (productView.isChecked()) {
                            nodesToRemove.add(node);
                            productsController.deleteProduct(productView.getMediaId());
                        }
                    }
                }

                mediaTable.getChildren().removeAll(nodesToRemove);
                showCheckBoxes(false);

                showAlert("Success", "Selected products have been deleted successfully.");
                dailyCounter.setCounterValue(sum);
                productsController.updateDailyCounter(dailyCounter);
            } else {
                showAlert("Failed", "Can not delete more than 30 products a day.");
            }
        } else {
            showAlert("Failed", "Can not delete more than 10 products at a times.");
        }
    }


    public void refreshProductList() {
        mediaTable.getChildren().clear();
        List<Product> productList = productsController.getProductList();
        for (Product product : productList) {
            addProduct(product);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
