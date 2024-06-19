package org.example.project_manager_dashboard.controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.example.project_manager_dashboard.models.User;

public class LoginController {
    private static LoginController LoginController;
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbPU");
    public static int loggedInUserId = 0;


    private LoginController() {}

    public static LoginController getLoginController() {
        if (LoginController == null) {
            LoginController = new LoginController();
        }
        return LoginController;
    }

    public int login(String username, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            User user = query.getSingleResult();

            if (user != null) {
                if (user.isBlocked()) {
                    return 2;// If user is found, credentials are correct
                } else {
                    loggedInUserId = user.getUserId();
                    return 1;
                }
            } else {
                return 0;
            }
        } catch (NoResultException e) {
            return 0;
        } finally {
            em.close();
        }
    }

    public boolean nameExisted(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            User user = query.getSingleResult();
            return user != null; // If user is found, credentials are correct
        } catch (NoResultException e) {
            return false; // If no user is found, credentials are incorrect
        } finally {
            em.close();
        }
    }

    public boolean register(String username, String password, String name, String phoneNumber, String type, boolean blocked) {
        if (nameExisted(username)) {
            return false; // Username already taken
        }

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .type(type)
                    .build();
            em.persist(user);
            transaction.commit();
            return true; // Registration successful
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false; // Registration failed
        } finally {
            em.close();
        }
    }

    public void updatePassword(String fullName, String username, String phoneNumber, String newPassword) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.name = :fullName AND u.username = :username AND u.phoneNumber = :phoneNumber", User.class);
            query.setParameter("fullName", fullName);
            query.setParameter("username", username);
            query.setParameter("phoneNumber", phoneNumber);
            User user = query.getSingleResult();
            if (user != null) {
                user.setPassword(newPassword);
                em.merge(user);
            }
            transaction.commit();
        } catch (NoResultException e) {
            transaction.rollback();
            throw new RuntimeException("Account does not exist.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean checkAccountExisted(String fullName, String userName, String phoneNumber) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :userName AND u.name = :fullName AND u.phoneNumber = :phoneNumber", User.class);
            query.setParameter("userName", userName);
            query.setParameter("fullName", fullName);
            query.setParameter("phoneNumber", phoneNumber);
            User user = query.getSingleResult();
            return user != null; // If user is found, credentials are correct
        } catch (NoResultException e) {
            return false; // If no user is found, credentials are incorrect
        } finally {
            em.close();
        }
    }
}
