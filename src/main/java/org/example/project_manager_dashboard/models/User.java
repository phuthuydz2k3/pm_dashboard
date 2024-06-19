package org.example.project_manager_dashboard.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Integer userId;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @Column(name = "isBlock", nullable = false)
    private boolean blocked;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private DailyCounter dailyCounter;

    @PostPersist
    private void createDailyCounter() {
        DailyCounter counter = new DailyCounter();
        counter.setCounterValue(0);
        counter.setLastResetDate(LocalDate.now());
        counter.setUser(this); // Set the user association
        this.dailyCounter = counter;
    }
}
