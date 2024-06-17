package org.example.project_manager_dashboard.controllers;

import org.example.project_manager_dashboard.models.Order;
import org.example.project_manager_dashboard.models.OrderItemDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersController {
    private static OrdersController ordersController;
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbPU");
    private static final EntityManager em =  emf.createEntityManager();

    private OrdersController() {}

    public static OrdersController getHomeController() {
        if (ordersController == null) {
            ordersController = new OrdersController();
        }

        return ordersController;
    }

    public List<Order> getOrderList() {
        TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o", Order.class);
        return query.getResultList();
    }

    public List<OrderItemDTO> getOrderItems(int orderId) {
        Query query = em.createNativeQuery("CALL GetMediaByOrderId(:orderId)");
        query.setParameter("orderId", orderId);

        List<Object[]> results = query.getResultList();
        List<OrderItemDTO> orderItems = new ArrayList<>();

        for (Object[] result : results) {
            OrderItemDTO orderItem = new OrderItemDTO();
            orderItem.setMediaId((Integer) result[0]);
            orderItem.setPrice((Float) result[1]);
            orderItem.setAvailable((Integer) result[2]);
            orderItem.setName((String) result[3]);
            orderItem.setImageURL((String) result[4]);
            orderItem.setCategory((String) result[5]);
            orderItem.setWeight((Float) result[6]);
            orderItem.setSupportRushDelivery((short) result[7] == 1);
            orderItem.setQuantity((Integer) result[8]);
            orderItems.add(orderItem);
        }

        return orderItems;
    }

    public void updateOrderState(Order order) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(order); // Update the order state
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
