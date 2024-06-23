package org.example.project_manager_dashboard.productFactories;

import org.example.project_manager_dashboard.models.DVD;
import org.example.project_manager_dashboard.models.Product;

import java.util.Map;

public class DVDFactory implements ProductFactory {
    @Override
    public Product createProduct(Map<String, String> fieldValues) {
        // Extract fields specific to DVD
        Double price = Double.parseDouble(fieldValues.get("priceTextField"));
        Integer available = Integer.parseInt(fieldValues.get("availableTextField"));
        String name = fieldValues.get("nameTextField");
        String imageURL = fieldValues.get("imageURLTextField");
        Double weight = Double.parseDouble(fieldValues.get("weightTextField"));
        Short supportRushDelivery = fieldValues.get("rushDeliveryTextField").equals("true") ? (short) 1: 0;

        String discType = fieldValues.get("discTypeTextField");
        String director = fieldValues.get("directorTextField");
        String studio = fieldValues.get("studioTextField");
        String subtitle = fieldValues.get("subtitleTextField");
        String runtime = fieldValues.get("runtimeTextField");
        String category = fieldValues.get("category");

        return DVD.builder()
                .price(price)
                .available(available)
                .name(name)
                .imageURL(imageURL)
                .category(category)
                .weight(weight)
                .supportRushDelivery(supportRushDelivery)
                .discType(discType)
                .director(director)
                .studio(studio)
                .subtitle(subtitle)
                .runtime(runtime)
                .build();
    }
}
