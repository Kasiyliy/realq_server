package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Jobs;
import kz.kasya.realq.models.entities.Tasks;
import kz.kasya.realq.models.entities.Workers;
import kz.kasya.realq.repositories.WorkerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.stream.Collectors;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Service
public class WorkerService implements UserDetailsService {

    private WorkerRepository workerRepository;

    private SessionFactory hibernateFactory;


    @Autowired
    public WorkerService(EntityManagerFactory factory,
                         WorkerRepository workerRepository) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.workerRepository = workerRepository;
    }

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
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), getAuthority(user));
    }

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

    public Workers findByLoginWithTrashed(String login) {
        Workers worker = workerRepository.findByLogin(login);
        return worker;
    }

    public boolean isLoginAlreadyInUse(String login) {
        return findByLoginWithTrashed(login) != null;
    }


    private Set getAuthority(Workers user) {
        Set authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return authorities;
    }

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

    public List<Workers> getAllWithTrashed() {
        return workerRepository.findAll();
    }

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

    public boolean add(Workers worker) {
        if (worker == null || worker.getId() != null) {
            return false;
        } else {
            workerRepository.save(worker);
            return true;
        }
    }

    public boolean update(Workers job) {
        if (job == null || job.getId() == null) {
            return false;
        } else {
            workerRepository.save(job);
            return true;
        }
    }

    public boolean realDelete(Workers job) {
        if (job == null || job.getId() == null) {
            return false;
        } else {
            workerRepository.delete(job);
            return true;
        }
    }

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
