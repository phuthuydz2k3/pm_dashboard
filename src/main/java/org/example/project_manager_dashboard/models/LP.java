package org.example.project_manager_dashboard.models;

import lombok.*;

import javax.persistence.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "lp")
public class LP extends Product {

    // Other CD-specific attributes
    @Column(name = "artist")
    private String artist;

    @Column(name = "released_date")
    private Date releasedDate;

    @Column(name = "record_lable")
    private String recordLabel;

    @Column(name = "music_type")
    private String musicType;

    @Override
    public Product createCopy() {
        return LP.builder()
                .productId(this.getProductId())
                .name(this.getName())
                .category(this.getCategory())
                .price(this.getPrice())
                .available(this.getAvailable())
                .weight(this.getWeight())
                .supportRushDelivery(this.getSupportRushDelivery())
                .artist(this.getArtist())
                .releasedDate(this.getReleasedDate())
                .recordLabel(this.getRecordLabel())
                .musicType(this.getMusicType())
                .build();
    }
}
