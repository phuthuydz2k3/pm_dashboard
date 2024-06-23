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
@Table(name = "dvd")
public class DVD extends Product {

    // Other DVD-specific attributes
    @Column(name = "disc_type")
    private String discType;

    @Column(name = "director")
    private String director;

    @Column(name = "studio")
    private String studio;

//    @Column(name = "released_date")
//    private String releasedDate;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "runtime")
    private String runtime;

    @Override
    public Product createCopy() {
        return DVD.builder()
                .productId(this.getProductId())
                .name(this.getName())
                .category(this.getCategory())
                .price(this.getPrice())
                .available(this.getAvailable())
                .weight(this.getWeight())
                .supportRushDelivery(this.getSupportRushDelivery())
                .discType(this.getDiscType())
                .director(this.getDirector())
                .studio(this.getStudio())
                .subtitle(this.getSubtitle())
                .runtime(this.getRuntime())
                .build();
    }
}

