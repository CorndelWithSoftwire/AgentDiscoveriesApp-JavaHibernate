package org.softwire.training.db.daos;

import org.softwire.training.db.daos.searchcriteria.ReportSearchCriterion;
import org.softwire.training.models.LocationStatusReport;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LocationReportsDao implements ReportsDao<LocationStatusReport> {

    @Inject EntityManagerFactory entityManagerFactory;

    public Optional<LocationStatusReport> getReport(int reportId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        LocationStatusReport report = em.find(LocationStatusReport.class, reportId);

        em.getTransaction().commit();
        em.close();

        return Optional.ofNullable(report);
    }

    public int createReport(LocationStatusReport report) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        em.persist(report);
        em.flush();

        em.getTransaction().commit();
        em.close();

        return report.getReportId();
    }

    public void deleteReport(int report_id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        LocationStatusReport report = em.find(LocationStatusReport.class, report_id);
        if (report != null) {
            em.remove(report);
        }

        em.getTransaction().commit();
        em.close();
    }

    public List<LocationStatusReport> searchReports(List<ReportSearchCriterion> searchCriteria) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        String whereClause = ReportsDaoUtils.buildWhereSubClauseFromCriteria(searchCriteria);

        TypedQuery<LocationStatusReport> query = em.createQuery("FROM region_summary_reports " + whereClause, LocationStatusReport.class);

        for (ReportSearchCriterion criterion : searchCriteria) {
            for (Map.Entry<String, Object> bindingEntry : criterion.getBindingsForSql().entrySet()) {
                query = query.setParameter(bindingEntry.getKey(), bindingEntry.getValue());
            }
        }

        List<LocationStatusReport> results = query.getResultList();

        em.getTransaction().commit();
        em.close();

        return results;
    }
}
