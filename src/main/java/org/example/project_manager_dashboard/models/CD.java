package org.example.project_manager_dashboard.models;

import lombok.*;

import javax.persistence.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "cd")
public class CD extends Media {

    // Other CD-specific attributes
    @Column(name = "artist")
    private String artist;

    @Column(name = "released_date")
    private String releasedDate;

    @Column(name = "record_lable")
    private String recordLabel;

    @Column(name = "music_type")
    private String musicType;
}
