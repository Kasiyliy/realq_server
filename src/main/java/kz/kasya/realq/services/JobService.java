package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Categories;
import kz.kasya.realq.models.entities.Jobs;
import kz.kasya.realq.repositories.JobRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Service
public class JobService {

    private JobRepository jobRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public JobService(EntityManagerFactory factory,
                      JobRepository jobRepository) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.jobRepository = jobRepository;
    }

    public List<Jobs> getAllWithTrashed() {
        return jobRepository.findAll();
    }

    public List<Jobs> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Jobs> criteriaQuery = criteriaBuilder.createQuery(Jobs.class);
        Root<Jobs> root = criteriaQuery.from(Jobs.class);

        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<Jobs> jobs = session.createQuery(criteriaQuery).list();

        session.close();
        return jobs;
    }

    public List<Jobs> getAllBy(Categories category) {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Jobs> criteriaQuery = criteriaBuilder.createQuery(Jobs.class);
        Root<Jobs> root = criteriaQuery.from(Jobs.class);

        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        Predicate predicate2 = criteriaBuilder.equal(root.get("category"), category);
        Predicate andPredicate = criteriaBuilder.and(predicate, predicate2);
        criteriaQuery.where(andPredicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<Jobs> jobs = session.createQuery(criteriaQuery).list();

        session.close();
        return jobs;
    }

    public Jobs getById(Long id) {
        Optional<Jobs> jobOptional = jobRepository.findById(id);

        if (jobOptional.isPresent()) {
            return jobOptional.get();
        } else {
            return null;
        }
    }

    public boolean add(Jobs job) {
        if (job == null || job.getId() != null || job.getCategory() == null) {
            return false;
        } else {
            jobRepository.save(job);
            return true;
        }
    }

    public boolean update(Jobs job) {
        if (job == null || job.getId() == null) {
            return false;
        } else {
            jobRepository.save(job);
            return true;
        }
    }

    public boolean realDelete(Jobs job) {
        if (job == null || job.getId() == null) {
            return false;
        } else {
            jobRepository.delete(job);
            return true;
        }
    }

    public boolean delete(Jobs job) {
        if (job == null || job.getId() == null) {
            return false;
        } else {
            job.setDeletedAt(new Date());
            jobRepository.save(job);
            return true;
        }
    }

}
