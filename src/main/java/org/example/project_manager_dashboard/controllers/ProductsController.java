package org.example.project_manager_dashboard.controllers;

import org.example.project_manager_dashboard.models.Book;
import org.example.project_manager_dashboard.models.Product;

import javax.persistence.*;
import java.util.List;

public class ProductsController {
    private static ProductsController productsController;
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbPU");

    private ProductsController() {}

    public static ProductsController getProductsController() {
        if (productsController == null) {
            productsController = new ProductsController();
        }

        return productsController;
    }

    // New method to get all media
    public List<Product> getProductList() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Product> query = em.createQuery("SELECT m FROM Product m", Product.class);
        return query.getResultList();
    }

    // New method to get a specific media by its id and determine its type
    public Product getMediaById(Integer mediaId) {
        EntityManager em = emf.createEntityManager();
        try {
            Product product = em.find(Product.class, mediaId);
            if (product instanceof Book) {
                return product;
            }
            // Add more instanceof checks here if you have other subclasses of Product
            return product;
        } finally {
            em.close();
        }
    }

    public boolean addProduct(Product product) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(product);
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

    public void deleteProduct(Integer mediaId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Product product = em.find(Product.class, mediaId);
            if (product != null) {
                em.remove(product);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
