package org.softwire.training.db.daos;

import org.softwire.training.models.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class UsersDao {

    @Inject EntityManagerFactory entityManagerFactory;

    public Optional<User> getUserByUsername(String username) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        List<User> results = em.createQuery("FROM users WHERE username LIKE :username", User.class).setParameter("username", username).getResultList();

        em.getTransaction().commit();
        em.close();

        User user = null;
        if (!results.isEmpty()) {
            user = results.get(0);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> getUser(int userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, userId);

        em.getTransaction().commit();
        em.close();

        return Optional.ofNullable(user);
    }

    public List<User> getUsers() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        List<User> results = em.createQuery("FROM users", User.class).getResultList();

        em.getTransaction().commit();
        em.close();

        return results;
    }

    public int addUser(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.persist(user);
        em.flush();

        em.getTransaction().commit();
        em.close();

        return user.getUserId();
    }

    public void deleteUser(int userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, userId);
        if (user != null) {
            em.remove(user);
        }

        em.getTransaction().commit();
        em.close();
    }

    public void updateUser(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.merge(user);

        em.getTransaction().commit();
        em.close();
    }
}
