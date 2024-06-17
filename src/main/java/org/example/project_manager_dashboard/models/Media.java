package org.example.project_manager_dashboard.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "media")
@Inheritance(strategy = InheritanceType.JOINED)
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private Integer mediaId;

    @Column(name = "price")
    private Float price;

    @Column(name = "available")
    private Integer available;

    @Column(name = "name")
    private String name;

    @Column(name = "imageURL")
    private String imageURL;

    @Column(name = "category")
    private String category;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "support_rush_delivery")
    private Short supportRushDelivery;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Media media = (Media) obj;
        return Objects.equals(mediaId, media.mediaId);
    }

    // Implement hashCode method based on mediaId
    @Override
    public int hashCode() {
        return Objects.hash(mediaId);
    }
}
