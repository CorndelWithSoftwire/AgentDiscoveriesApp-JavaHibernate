package org.softwire.training.db.daos;

import org.softwire.training.models.Location;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class LocationsDao {

    @Inject EntityManagerFactory entityManagerFactory;

    public Optional<Location> getLocation(int locationId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Location location = em.find(Location.class, locationId);

        em.getTransaction().commit();
        em.close();

        return Optional.ofNullable(location);
    }

    public List<Location> getLocations() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        List<Location> results = em.createQuery("FROM Location", Location.class).getResultList();

        em.getTransaction().commit();
        em.close();

        return results;
    }

    public int createLocation(Location location) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.persist(location);
        em.flush();

        em.getTransaction().commit();
        em.close();

        return location.getLocationId();
    }

    public void deleteLocation(int locationId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Location location = em.find(Location.class, locationId);
        if (location != null) {
            em.remove(location);
        }

        em.getTransaction().commit();
        em.close();
    }

    public void updateLocation(Location location) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.merge(location);

        em.getTransaction().commit();
        em.close();
    }
}
