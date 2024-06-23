package org.example.project_manager_dashboard.views.screens.productDetailStrategies;


import javafx.scene.layout.VBox;
import org.example.project_manager_dashboard.models.Product;

public interface ProductDetailStrategy {
    VBox loadSpecificDetails(Product product);
    void updateProductDetails(Product product, VBox detailsPane);
}