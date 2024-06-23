package org.example.project_manager_dashboard.productFactories;

import org.example.project_manager_dashboard.models.CD;
import org.example.project_manager_dashboard.models.Product;

import java.time.LocalDate;
import java.util.Map;

public class CDFactory implements ProductFactory {
    @Override
    public Product createProduct(Map<String, String> fieldValues) {
        // Extract fields specific to CD
        Double price = Double.parseDouble(fieldValues.get("priceTextField"));
        Integer available = Integer.parseInt(fieldValues.get("availableTextField"));
        String name = fieldValues.get("nameTextField");
        String imageURL = fieldValues.get("imageURLTextField");
        Double weight = Double.parseDouble(fieldValues.get("weightTextField"));
        Short supportRushDelivery = fieldValues.get("rushDeliveryTextField").equals("true") ? (short) 1: 0;

        String artist = fieldValues.get("artistTextField");
        LocalDate releasedDate = LocalDate.parse(fieldValues.get("releasedDatePickerCD"));
        String recordLabel = fieldValues.get("recordLabelTextFieldCD");
        String musicType = fieldValues.get("musicTypeTextField");
        String category = fieldValues.get("category");

        return CD.builder()
                .price(price)
                .available(available)
                .name(name)
                .imageURL(imageURL)
                .category(category)
                .weight(weight)
                .supportRushDelivery(supportRushDelivery)
                .artist(artist)
                .releasedDate(java.sql.Date.valueOf(releasedDate))
                .recordLabel(recordLabel)
                .musicType(musicType)
                .build();
    }
}
