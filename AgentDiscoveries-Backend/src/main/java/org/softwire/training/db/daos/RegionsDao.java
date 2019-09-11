package org.softwire.training.db.daos;

import org.softwire.training.models.Region;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class RegionsDao {

    @Inject EntityManagerFactory entityManagerFactory;

    public Optional<Region> getRegion(int regionId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Region region = em.find(Region.class, regionId);

        em.getTransaction().commit();
        em.close();

        return Optional.ofNullable(region);
    }

    public List<Region> getRegions() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        List<Region> results = em.createQuery("FROM Region", Region.class).getResultList();

        em.getTransaction().commit();
        em.close();

        return results;
    }

    public int createRegion(Region region) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.persist(region);
        em.flush();

        em.getTransaction().commit();
        em.close();

        return region.getRegionId();
    }

    public void updateRegion(Region region) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.merge(region);

        em.getTransaction().commit();
        em.close();
    }

    public void deleteRegion(int regionId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Region region = em.find(Region.class, regionId);
        if (region != null) {
            em.remove(region);
        }

        em.getTransaction().commit();
        em.close();
    }
}
