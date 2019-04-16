package kz.kasya.realq.services.impl;

import kz.kasya.realq.models.entities.Jobs;
import kz.kasya.realq.models.entities.Workers;
import kz.kasya.realq.repositories.WorkerRepository;
import kz.kasya.realq.services.WorkerService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Service
public class WorkerServiceImpl implements WorkerService {

    private WorkerRepository workerRepository;

    private SessionFactory hibernateFactory;


    @Autowired
    public WorkerServiceImpl(EntityManagerFactory factory,
                             WorkerRepository workerRepository) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.workerRepository = workerRepository;
    }

    @Override
    public Workers getById(Long id) {
        Optional<Workers> jobOptional = workerRepository.findById(id);

        if (jobOptional.isPresent()) {
            return jobOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Workers user = findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        User authUser = new User(user.getLogin(), user.getPassword(), getAuthority(user));
        return authUser;
    }

    @Override
    public Workers findByLogin(String login) {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Workers> criteriaQuery = criteriaBuilder.createQuery(Workers.class);
        Root<Workers> root = criteriaQuery.from(Workers.class);

        Predicate predicate1 = criteriaBuilder.isNull(root.get("deletedAt"));
        Predicate predicate2 = criteriaBuilder.equal(root.get("login"), login);
        Predicate andPredicate = criteriaBuilder.and(predicate1, predicate2);

        criteriaQuery.where(andPredicate);
        Workers worker;
        try {
            worker = session.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            worker = null;
        }
        session.close();
        return worker;
    }

    @Override
    public Workers findByLoginWithTrashed(String login) {
        Workers worker = workerRepository.findByLogin(login);
        return worker;
    }

    @Override
    public boolean isLoginAlreadyInUse(String login) {
        return findByLoginWithTrashed(login) != null;
    }

    @Override
    public List getAuthority(Workers user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    @Override
    public Workers getFreeWorkerByJobs(Jobs job) {
        Session session = hibernateFactory.openSession();

        String hql = "select w FROM Workers w inner join w.jobs j where w.deletedAt is null  " +
                " and w.task is null and j.id = (:id) order by j.createdAt asc";
        Workers worker = null;
        try {

            worker = (Workers) session.createQuery(hql)
                    .setMaxResults(1)
                    .setParameter("id", job.getId())
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return worker;
    }

    @Override
    public List<Workers> getAllManagers() {
        return workerRepository.findAllManagerWorkers();
    }

    @Override
    public List<Workers> getAllWithTrashed() {
        return workerRepository.findAll();
    }

    @Override
    public List<Workers> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Workers> criteriaQuery = criteriaBuilder.createQuery(Workers.class);
        Root<Workers> root = criteriaQuery.from(Workers.class);

        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        criteriaQuery.where(predicate);
        List<Workers> workers = session.createQuery(criteriaQuery).list();
        session.close();
        return workers;
    }

    @Override
    public boolean add(Workers worker) {
        if (worker == null || worker.getId() != null) {
            return false;
        } else {
            workerRepository.save(worker);
            return true;
        }
    }

    @Override
    public boolean update(Workers job) {
        if (job == null || job.getId() == null) {
            return false;
        } else {
            workerRepository.save(job);
            return true;
        }
    }

    @Override
    public boolean realDelete(Workers job) {
        if (job == null || job.getId() == null) {
            return false;
        } else {
            workerRepository.delete(job);
            return true;
        }
    }

    @Override
    public boolean delete(Workers worker) {
        if (worker == null || worker.getId() == null) {
            return false;
        } else {
            worker.setDeletedAt(new Date());
            workerRepository.save(worker);
            return true;
        }
    }


}
