package org.example.project_manager_dashboard.views.screens;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;
import org.example.project_manager_dashboard.controllers.ProductController;
import org.example.project_manager_dashboard.controllers.ProductsController;
import org.example.project_manager_dashboard.models.*;
import javafx.scene.control.Alert;
import org.example.project_manager_dashboard.views.screens.productDetailStrategies.ProductDetailStrategy;
import org.example.project_manager_dashboard.views.screens.productDetailStrategies.ProductDetailStrategyFactory;

import java.net.URL;

import java.util.ResourceBundle;

public class ProductDetailView implements Initializable {

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productCategoryField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productStockField;

    @FXML
    private TextField productWeightField;

    @FXML
    private TextField productRushDeliveryField;

    @FXML
    private TextField imageURLTextField;

    @FXML
    private AnchorPane productSpecificDetailsPane;

    @FXML
    private Button updateProductBtn, confirmUpdateBtn, cancelUpdateBtn, deleteProductBtn;

    @FXML
    private ImageView productImageView;

    @FXML
    private HBox imageBrowse;

    @FXML
    private CheckBox rushDeliveryCheckBox;

    private Product product;

    @Setter
    private ProductsView productsViewCallback;

    private final ProductsController productsController = ProductsController.getProductsController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelUpdateBtn.setVisible(false);
        confirmUpdateBtn.setVisible(false);
        imageBrowse.setVisible(false);
        rushDeliveryCheckBox.setVisible(false);

        if (product != null) {
            setProductDetails(product);
        }
    }

    public void setProduct(Product product) {
        this.product = product;
        if (productNameField != null) {
            setProductDetails(product);
        }
    }

    public void setProductDetails(Product product) {
        productNameField.setText(product.getName());
        productCategoryField.setText(product.getCategory());
        productPriceField.setText(product.getPrice().toString());
        productStockField.setText(product.getAvailable().toString());
        productWeightField.setText(product.getWeight().toString());
        productRushDeliveryField.setText(product.getSupportRushDelivery() == 1 ? "Yes" : "No");
        // Load and set the image
        String imageURL = product.getImageURL();
        if (imageURL != null && !imageURL.isEmpty()) {
            try {
                Image image = new Image(imageURL);
                productImageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        loadSpecificDetails(product);
    }

    private void loadSpecificDetails(Product product) {
        ProductDetailStrategy strategy = ProductDetailStrategyFactory.getStrategy(product.getClass());
        if (strategy != null) {
            VBox pane = strategy.loadSpecificDetails(product);
            if (pane != null) {
                productSpecificDetailsPane.getChildren().setAll(pane);
            }
        }
    }

    @FXML
    private void updateProduct() {
        if (product != null) {
            updateProductBtn.setVisible(false);
            deleteProductBtn.setVisible(false);
            productRushDeliveryField.setVisible(false);

            cancelUpdateBtn.setVisible(true);
            confirmUpdateBtn.setVisible(true);
            imageBrowse.setVisible(true);
            rushDeliveryCheckBox.setVisible(true);

            makeAllTextFieldsEditable(true);
        }
    }

    private void makeAllTextFieldsEditable(Boolean editable) {
        productNameField.setEditable(editable);
        productPriceField.setEditable(editable);
        productStockField.setEditable(editable);
        productWeightField.setEditable(editable);
        productRushDeliveryField.setEditable(editable);

        VBox pane = (VBox) productSpecificDetailsPane.getChildren().get(0);
        for (TextField textField : pane.getChildren().filtered(node -> node instanceof TextField).toArray(new TextField[0])) {
            textField.setEditable(editable);
        }
    }

    @FXML
    private void deleteProduct() {
        if (product != null) {
            DailyCounter dailyCounter = productsController.getDailyCounter();
            int sum = dailyCounter.getCounterValue() + 1;
            if (sum <= 30) {
                boolean deleted = ProductController.getProductController().deleteProduct(product.getProductId());
                if (deleted) {
                    productsViewCallback.refreshProductList();
                    dailyCounter.setCounterValue(sum);
                    productsController.updateDailyCounter(dailyCounter);

                    // Optionally, update UI or show success message
                    showAlert("Product deleted successfully.");
                    // Clear fields or handle UI state as needed after deletion
                } else {
                    // Handle deletion failure
                    showAlert("Failed to delete product.");
                }
                Stage stage = (Stage) deleteProductBtn.getScene().getWindow();
                stage.close();
            } else  {
                showAlert("Can not delete more than 30 products a day!");
            }
        }
    }

    @FXML
    private void confirmUpdate() {
        if (product != null) {
            Product oldProduct = product.createCopy();

            try {
                product.setName(productNameField.getText());
                product.setCategory(productCategoryField.getText());
                product.setPrice(Double.parseDouble(productPriceField.getText()));
                String currentImageURL = imageURLTextField.getText();
                if (currentImageURL != null && !currentImageURL.isEmpty()) {
                    product.setImageURL(currentImageURL);
                }
                product.setAvailable(Integer.parseInt(productStockField.getText()));
                product.setWeight(Double.parseDouble(productWeightField.getText()));
                product.setSupportRushDelivery((short) (rushDeliveryCheckBox.isSelected() ? 1 : 0));

                // Update specific details using strategy
                ProductDetailStrategy strategy = ProductDetailStrategyFactory.getStrategy(product.getClass());
                if (strategy != null) {
                    strategy.updateProductDetails(product, (VBox) productSpecificDetailsPane.getChildren().get(0));
                }

                boolean updated = ProductController.getProductController().updateProduct(product);
                if (updated) {
                    showAlert("Product updated successfully.");
                } else {
                    product = oldProduct;
                    showAlert("Failed to update product. Reverting to previous state.");
                }
            } catch (Exception e) {
                product = oldProduct;
                showAlert("An error occurred while updating the product. Reverting to previous state.");
            }

//            updateProductBtn.setVisible(true);
//            deleteProductBtn.setVisible(true);
//            productRushDeliveryField.setVisible(true);
//
//            cancelUpdateBtn.setVisible(false);
//            confirmUpdateBtn.setVisible(false);
//            imageBrowse.setVisible(false);
//            rushDeliveryCheckBox.setVisible(false);
//
//            makeAllTextFieldsEditable(false);

            Stage stage = (Stage) updateProductBtn.getScene().getWindow();
            stage.close();

            productsViewCallback.refreshProductList();
        }
    }

    @FXML
    private void cancelUpdate() {
        updateProductBtn.setVisible(true);
        deleteProductBtn.setVisible(true);
        productRushDeliveryField.setVisible(true);

        cancelUpdateBtn.setVisible(false);
        confirmUpdateBtn.setVisible(false);
        imageBrowse.setVisible(false);
        rushDeliveryCheckBox.setVisible(false);

        makeAllTextFieldsEditable(false);

        loadSpecificDetails(product);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
