package org.example.project_manager_dashboard.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "book")
public class Book extends Media {

    @Column(name = "author")
    private String author;

    @Column(name = "cover_type")
    private String coverType;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publish_date")
    private String publishDate;

    @Column(name = "num_of_pages")
    private Integer numOfPages;

    @Column(name = "language")
    private String language;

    @Column(name = "book_category")
    private String bookCategory;
}

