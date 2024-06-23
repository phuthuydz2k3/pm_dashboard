package org.example.project_manager_dashboard.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "book")
public class Book extends Product {

    @Column(name = "author")
    private String author;

    @Column(name = "cover_type")
    private String coverType;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publish_date")
    private Date publishDate;

//    @Column(name = "num_of_pages")
//    private Integer numOfPages;
//
//    @Column(name = "language")
//    private String language;
//
//    @Column(name = "book_category")
//    private String bookCategory;

    @Override
    public Product createCopy() {
        return Book.builder()
                .productId(this.productId)
                .name(this.name)
                .category(this.category)
                .price(this.price)
                .available(this.available)
                .weight(this.weight)
                .supportRushDelivery(this.supportRushDelivery)
                .author(this.author)
                .coverType(this.coverType)
                .publisher(this.publisher)
                .publishDate(this.publishDate)
                .build();
    }
}

