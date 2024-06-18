package org.example.project_manager_dashboard.controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.example.project_manager_dashboard.models.Product;

public class ProductController {
    private static ProductController productController;
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbPU");

    private ProductController() {}

    public static ProductController getProductController() {
        if (productController == null) {
            productController = new ProductController();
        }

        return productController;
    }

    public boolean updateProduct(Product product) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(product); // Use merge for updates
            transaction.commit();
            return true; // Operation succeeded
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false; // Operation failed
        } finally {
            em.close();
        }
    }

    public boolean deleteProduct(Integer productId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Product product = em.find(Product.class, productId);
            if (product != null) {
                em.remove(product);
            }
            transaction.commit();
            return true; // Operation succeeded
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false; // Operation failed
        } finally {
            em.close();
        }
    }
}
