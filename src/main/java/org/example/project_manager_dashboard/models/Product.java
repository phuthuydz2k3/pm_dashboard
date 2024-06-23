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
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    protected Integer productId;

    @Column(name = "price")
    protected Double price;

    @Column(name = "in_stock")
    protected Integer available;

    @Column(name = "title")
    protected String name;

    @Column(name = "imageURL")
    protected String imageURL;

    @Column(name = "type_product")
    protected String category;

    @Column(name = "weight")
    protected Double weight;

    @Column(name = "ro_supported")
    protected Short supportRushDelivery;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(productId, product.productId);
    }

    // Implement hashCode method based on mediaId
    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    public abstract Product createCopy();
}
