package org.example.project_manager_dashboard.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "delivery_info")
public class DeliveryInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_info_id")
    private Integer deliveryInfoId;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "phone_number", length = 45)
    private String phoneNumber;

    @Column(name = "province", length = 45)
    private String province;

    @Column(name = "instruction", length = 45)
    private String instruction;

    @Column(name = "address", length = 45)
    private String address;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "is_rush")
    private Short isRush;
}
