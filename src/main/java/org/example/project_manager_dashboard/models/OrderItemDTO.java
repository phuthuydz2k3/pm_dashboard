package org.example.project_manager_dashboard.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    private Integer mediaId;
    private Double price;
    private Integer available;
    private String name;
    private String imageURL;
    private String category;
    private Double weight;
    private Boolean supportRushDelivery;
    private Integer quantity;
}
