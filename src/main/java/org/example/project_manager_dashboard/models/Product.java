package org.example.project_manager_dashboard.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer mediaId;

    @Column(name = "price")
    private Float price;

    @Column(name = "in_stock")
    private Integer available;

    @Column(name = "title")
    private String name;

    @Column(name = "imageURL")
    private String imageURL;

    @Column(name = "type_product")
    private String category;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "ro_supported")
    private Short supportRushDelivery;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(mediaId, product.mediaId);
    }

    // Implement hashCode method based on mediaId
    @Override
    public int hashCode() {
        return Objects.hash(mediaId);
    }
}
