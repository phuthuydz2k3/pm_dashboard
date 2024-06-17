package org.example.project_manager_dashboard.controllers;

import org.example.project_manager_dashboard.models.Book;
import org.example.project_manager_dashboard.models.Media;

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
    public List<Media> getMediaList() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Media> query = em.createQuery("SELECT m FROM Media m", Media.class);
        return query.getResultList();
    }

    // New method to get a specific media by its id and determine its type
    public Media getMediaById(Integer mediaId) {
        EntityManager em = emf.createEntityManager();
        try {
            Media media = em.find(Media.class, mediaId);
            if (media instanceof Book) {
                return media;
            }
            // Add more instanceof checks here if you have other subclasses of Media
            return media;
        } finally {
            em.close();
        }
    }

    public boolean addMedia(Media media) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(media);
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
