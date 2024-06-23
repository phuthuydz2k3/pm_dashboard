package org.example.project_manager_dashboard.productFactories;

import org.example.project_manager_dashboard.models.Product;

import java.util.Map;

public interface ProductFactory {
    Product createProduct(Map<String, String> fieldValues);
}
