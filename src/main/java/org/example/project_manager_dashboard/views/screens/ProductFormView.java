package org.example.project_manager_dashboard.views.screens;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.project_manager_dashboard.controllers.ProductsController;
import org.example.project_manager_dashboard.models.*;
import org.example.project_manager_dashboard.productFactories.ProductFactory;
import org.example.project_manager_dashboard.productFactories.ProductFactoryProvider;
import org.example.project_manager_dashboard.views.screens.productFormStrategies.ExtraFieldsStrategy;
import org.example.project_manager_dashboard.views.screens.productFormStrategies.ProductFormStrategyFactory;


import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ProductFormView implements Initializable {

    @FXML
    private TextField priceTextField;
    @FXML
    private TextField availableTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField imageURLTextField;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private TextField weightTextField;
    @FXML
    private CheckBox rushDeliveryCheckBox;

    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    @FXML
    private VBox extraFieldsVBox;

    @FXML
    private ImageView imageView;

    private ProductsController productsController;

    private ExtraFieldsStrategy strategy;

    private Runnable onMediaAddedCallback;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productsController = ProductsController.getProductsController();
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateExtraFields(newVal));
    }

    private void updateExtraFields(String category) {
        extraFieldsVBox.getChildren().clear();

        strategy = ProductFormStrategyFactory.getStrategy(category.toLowerCase());

        if (strategy != null) {
            strategy.addExtraFields(extraFieldsVBox);
        } else {
            System.out.println("Category strategy not found: " + category);
        }
    }

    @FXML
    private void saveProduct() {
        try {
            // Validate inputs
            if (nameTextField.getText().isEmpty() || availableTextField.getText().isEmpty() || priceTextField.getText().isEmpty()
                    || imageURLTextField.getText().isEmpty() || categoryChoiceBox.getValue().toLowerCase().isEmpty()
                    || weightTextField.getText().isEmpty()) {
                showAlert("Validation Error", "Please fill all required fields.");
                return;
            }

            Map<String, String> fieldValues = new HashMap<>();
            fieldValues.put("priceTextField", priceTextField.getText());
            fieldValues.put("availableTextField", availableTextField.getText());
            fieldValues.put("nameTextField", nameTextField.getText());
            fieldValues.put("imageURLTextField", imageURLTextField.getText());
            String category = categoryChoiceBox.getValue().toLowerCase();
            fieldValues.put("category", categoryChoiceBox.getValue().toLowerCase());
            fieldValues.put("weightTextField", weightTextField.getText());
            fieldValues.put("rushDeliveryTextField", rushDeliveryCheckBox.isSelected() ? "true" : "false");

            ProductFactory factory = ProductFactoryProvider.getProductFactory(category);
            strategy.addFieldValues(fieldValues, extraFieldsVBox);
            Product product = factory.createProduct(fieldValues);

            // Save product using ProductsView
            Boolean result = productsController.addProduct(product);
            showResultAddMedia(result);

            // Close the form
            Stage stage = (Stage) saveBtn.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter valid numbers for price, available quantity, and weight.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while saving the media. Please try again.");
            e.printStackTrace();
        }
    }

    private void showResultAddMedia(Boolean result) {
        if (result) {
            showAlert("Success", "Product added successfully.");
            if (onMediaAddedCallback != null) {
                onMediaAddedCallback.run();
            }
        } else {
            showAlert("Error", "Failed to add media.");
        }
    }

    public void setOnMediaAdded(Runnable callback) {
        this.onMediaAddedCallback = callback;
    }

    @FXML
    private void cancelSaveMedia() {
        // Close the form
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onDragOver(DragEvent event) {
        if (event.getGestureSource() != imageURLTextField &&
                event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    private void onDragDropped(DragEvent event) {
        boolean success = false;
        if (event.getDragboard().hasFiles()) {
            success = true;
            File file = event.getDragboard().getFiles().get(0);
            String imagePath = file.toURI().toString();
            imageURLTextField.setText(imagePath);
            imageView.setImage(new Image(imagePath));
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void saveImage() {
        String imageURL = imageURLTextField.getText();
        if (!imageURL.isEmpty()) {
            try {
                Image image = new Image(imageURL);
                imageView.setImage(image);
            } catch (Exception e) {
                showAlert("Error", "Failed to load image from URL.");
                e.printStackTrace();
            }
        } else {
            showAlert("Validation Error", "Please enter an image URL.");
        }
    }
}
