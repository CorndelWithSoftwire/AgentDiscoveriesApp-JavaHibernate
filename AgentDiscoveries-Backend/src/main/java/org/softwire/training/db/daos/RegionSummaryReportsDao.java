package org.softwire.training.db.daos;

import org.softwire.training.db.daos.searchcriteria.ReportSearchCriterion;
import org.softwire.training.models.RegionSummaryReport;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RegionSummaryReportsDao implements ReportsDao<RegionSummaryReport> {

    @Inject EntityManagerFactory entityManagerFactory;

    public Optional<RegionSummaryReport> getReport(int reportId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        RegionSummaryReport report = em.find(RegionSummaryReport.class, reportId);

        em.getTransaction().commit();
        em.close();

        return Optional.ofNullable(report);
    }

    public int createReport(RegionSummaryReport report) {
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

        RegionSummaryReport report = em.find(RegionSummaryReport.class, report_id);
        if (report != null) {
            em.remove(report);
        }

        em.getTransaction().commit();
        em.close();
    }

    public List<RegionSummaryReport> searchReports(List<ReportSearchCriterion> searchCriteria) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        String whereClause = ReportsDaoUtils.buildWhereSubClauseFromCriteria(searchCriteria);

        TypedQuery<RegionSummaryReport> query = em.createQuery("FROM RegionSummaryReport " + whereClause, RegionSummaryReport.class);

        for (ReportSearchCriterion criterion : searchCriteria) {
            for (Map.Entry<String, Object> bindingEntry : criterion.getBindingsForSql().entrySet()) {
                query = query.setParameter(bindingEntry.getKey(), bindingEntry.getValue());
            }
        }

        List<RegionSummaryReport> results = query.getResultList();

        em.getTransaction().commit();
        em.close();

        return results;
    }
}
