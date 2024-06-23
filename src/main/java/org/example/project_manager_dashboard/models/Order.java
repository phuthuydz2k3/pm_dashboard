package org.example.project_manager_dashboard.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`order`")
public class Order {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "order_id")
   private Integer orderId;

   @Column(name = "shipping_fee")
   private Double shippingAmounts;

   @Column(name = "cart_amount")
   private Double cartAmounts;

   @Column(name = "total_amounts")
   private Double totalAmounts;

   @Column(name = "state")
   private String state;

   @OneToOne
   @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id")
   private DeliveryInfo deliveryInfo;
}
