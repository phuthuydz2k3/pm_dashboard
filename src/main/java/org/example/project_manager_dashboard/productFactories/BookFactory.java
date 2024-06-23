package org.example.project_manager_dashboard.productFactories;

import org.example.project_manager_dashboard.models.Book;
import org.example.project_manager_dashboard.models.Product;

import java.time.LocalDate;
import java.util.Map;

public class BookFactory implements ProductFactory {
    @Override
    public Product createProduct(Map<String, String> fieldValues) {
        // Extract fields specific to Book
        Double price = Double.parseDouble(fieldValues.get("priceTextField"));
        Integer available = Integer.parseInt(fieldValues.get("availableTextField"));
        String name = fieldValues.get("nameTextField");
        String imageURL = fieldValues.get("imageURLTextField");
        Double weight = Double.parseDouble(fieldValues.get("weightTextField"));
        Short supportRushDelivery = fieldValues.get("rushDeliveryTextField").equals("true") ? (short) 1: 0;

        String author = fieldValues.get("authorTextField");
        String coverType = fieldValues.get("coverTypeTextField");
        String publisher = fieldValues.get("publisherTextField");
        LocalDate publishDate = LocalDate.parse(fieldValues.get("publishDatePicker"));
        String category = fieldValues.get("category");

        return Book.builder()
                .price(price)
                .available(available)
                .name(name)
                .imageURL(imageURL)
                .category(category)
                .weight(weight)
                .supportRushDelivery(supportRushDelivery)
                .author(author)
                .coverType(coverType)
                .publisher(publisher)
                .publishDate(java.sql.Date.valueOf(publishDate))
                .build();
    }
}
