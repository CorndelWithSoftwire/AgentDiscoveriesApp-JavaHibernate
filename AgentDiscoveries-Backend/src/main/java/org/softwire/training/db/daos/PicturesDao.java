package org.softwire.training.db.daos;

import org.softwire.training.models.ProfilePicture;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

public class PicturesDao {

    @Inject EntityManagerFactory entityManagerFactory;

    public Optional<ProfilePicture> getPicture(int userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        ProfilePicture picture = em.find(ProfilePicture.class, userId);

        em.getTransaction().commit();
        em.close();

        return Optional.ofNullable(picture);
    }


    public void createOrUpdateUserPicture(int userId, byte[] pictureBytes, String fileType) {
        ProfilePicture picture = new ProfilePicture(pictureBytes, fileType, userId);

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.merge(picture);

        em.getTransaction().commit();
        em.close();
    }

    public void deleteUserPicture(int userId){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        ProfilePicture picture = em.find(ProfilePicture.class, userId);
        if (picture != null) {
            em.remove(picture);
        }

        em.getTransaction().commit();
        em.close();
    }
}
