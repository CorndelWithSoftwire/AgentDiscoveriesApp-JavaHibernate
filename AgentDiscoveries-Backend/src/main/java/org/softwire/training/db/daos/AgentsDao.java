package org.softwire.training.db.daos;

import org.softwire.training.models.Agent;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class AgentsDao {

    @Inject EntityManagerFactory entityManagerFactory;

    public Optional<Agent> getAgent(int agentId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Agent agent = em.find(Agent.class, agentId);

        em.getTransaction().commit();
        em.close();

        return Optional.ofNullable(agent);
    }

    public List<Agent> getAgents() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        List<Agent> results = em.createQuery("FROM Agent", Agent.class).getResultList();

        em.getTransaction().commit();
        em.close();

        return results;
    }

    public int createAgent(Agent agent) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.persist(agent);
        em.flush();

        em.getTransaction().commit();
        em.close();

        return agent.getAgentId();
    }

    public void deleteAgent(int agentId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Agent agent = em.find(Agent.class, agentId);
        if (agent != null) {
            em.remove(agent);
        }

        em.getTransaction().commit();
        em.close();
    }

    public void updateAgent(Agent agent) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.merge(agent);

        em.getTransaction().commit();
        em.close();
    }
}
