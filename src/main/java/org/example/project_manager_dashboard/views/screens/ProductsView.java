package org.example.project_manager_dashboard.views.screens;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.project_manager_dashboard.views.items.ProductView;
import org.example.project_manager_dashboard.controllers.ProductsController;
import org.example.project_manager_dashboard.models.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsView implements Initializable {
    @FXML
    private VBox mediaTable;

    @FXML
    private Button addMediaBtn;

    @FXML
    private Button selectForDeletionBtn;

    private Stage productFormStage;

    private boolean deletionMode = false;

    private final ProductsController productsController = ProductsController.getProductsController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Load data from the database and set it to the table
        List<Product> productList = productsController.getMediaList();
        for (Product product : productList) {
            addProduct(product);
        }

        addMediaBtn.addEventHandler(ActionEvent.ACTION, event -> addMedia());
        selectForDeletionBtn.addEventHandler(ActionEvent.ACTION, event -> selectForDeletion());
    }

    private void addProduct(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product.fxml"));
            AnchorPane mediaItemPane = loader.load();

            // Get the controller for the product item and set the values
            ProductView controller = loader.getController();
            controller.setOrderItemDetails(product);

            // Set the controller as user data for later retrieval
            mediaItemPane.setUserData(controller);

            mediaTable.getChildren().add(mediaItemPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addMedia() {
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
    }

    private void showCheckBoxes(boolean visible) {
        for (Node node : mediaTable.getChildren()) {
            if (node instanceof AnchorPane) {
                ProductView productView = (ProductView) ((AnchorPane) node).getUserData();
                productView.showCheckBox(visible);
            }
        }
    }

    private void handleSelectOrDelete() {
        if (deletionMode) {
            // Deletion mode is active, perform deletion
            deleteSelectedMedia();
        } else {
            // Enter deletion mode
            enterDeletionMode();
        }
    }

    private void enterDeletionMode() {
        deletionMode = true;
        showCheckBoxes(true);

        addMediaBtn.setText("Cancel Delete");
        addMediaBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/icons8-hand-30.png"))));

        selectForDeletionBtn.setText("Delete");
    }

    private void exitDeletionMode() {
        deletionMode = false;
        showCheckBoxes(false);

        addMediaBtn.setText("Add a Product");
        addMediaBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/icons8-plus-26.png"))));

        selectForDeletionBtn.setText("Select Product for Deletion");
    }

    private void deleteSelectedMedia() {
        for (Node node : mediaTable.getChildren()) {
            if (node instanceof AnchorPane) {
                ProductView productView = (ProductView) ((AnchorPane) node).getUserData();
                if (productView.isChecked()) {
                    mediaTable.getChildren().remove(node);
                    productsController.deleteMedia(productView.getMediaId());
                }
            }
        }
        exitDeletionMode();
    }

    private void refreshProductList() {
        mediaTable.getChildren().clear();
        List<Product> productList = productsController.getMediaList();
        for (Product product : productList) {
            addProduct(product);
        }
    }
}
