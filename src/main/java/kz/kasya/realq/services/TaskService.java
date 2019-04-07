package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Jobs;
import kz.kasya.realq.models.entities.Tasks;
import kz.kasya.realq.models.responses.TaskWithWorker;
import kz.kasya.realq.repositories.TaskRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Service
@Slf4j
public class TaskService {

    private TaskRepository taskRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public TaskService(EntityManagerFactory factory,
                       TaskRepository taskRepository) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.taskRepository = taskRepository;
    }

    public List<Tasks> getAllWithTrashed() {
        return taskRepository.findAll();
    }

    public List<Tasks> getAll(Boolean desc) {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tasks> criteriaQuery = criteriaBuilder.createQuery(Tasks.class);
        Root<Tasks> root = criteriaQuery.from(Tasks.class);

        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        Predicate predicate2 = criteriaBuilder.isNull(root.get("worker"));
        Predicate predicate3 = criteriaBuilder.isFalse(root.get("completed"));
        Predicate andPredicate = criteriaBuilder.and(predicate, predicate2, predicate3);
        if (desc == null || desc.equals(true)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
        }
        criteriaQuery.where(andPredicate);
        List<Tasks> tasks = session.createQuery(criteriaQuery).list();
        session.close();
        return tasks;
    }

    public List<TaskWithWorker> getAllNoCompleted(Boolean desc) {
        Session session = hibernateFactory.openSession();

        String hql = "FROM Tasks t where t.deletedAt is null and t.completed = false";

        if (desc == null || desc.equals(true)) {
            hql += " order by id desc";
        } else {
            hql += " order by id asc";
        }
        Query query = session.createQuery(hql);
        List<Tasks> tasks = query.getResultList();
        session.close();

        List<TaskWithWorker> taskWithWorkers = tasks.stream().map((t -> new TaskWithWorker(t, t.getWorker()))).collect(Collectors.toList());

        return taskWithWorkers;
    }

    public List<Tasks> getAllWithFixedNumberAndExcept(int count, List<Long> ids, Boolean desc) {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tasks> criteriaQuery = criteriaBuilder.createQuery(Tasks.class);
        Root<Tasks> root = criteriaQuery.from(Tasks.class);

        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        Predicate predicate2 = criteriaBuilder.isNull(root.get("worker"));
        Predicate predicate3 = criteriaBuilder.not(root.get("id").in(ids));
        Predicate predicate4 = criteriaBuilder.isFalse(root.get("completed"));
        Predicate andPredicate = criteriaBuilder.and(predicate, predicate2, predicate3, predicate4);
        if (desc == null || desc.equals(true)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
        }
        criteriaQuery.where(andPredicate);
        List<Tasks> tasks = session.createQuery(criteriaQuery).setMaxResults(count).list();
        session.close();
        return tasks;
    }

    public Tasks getTaskByJobs(Set<Jobs> jobs) {
        Session session = hibernateFactory.openSession();
        List<Long> ids = jobs.stream().map(Jobs::getId).collect(Collectors.toList());
        String hql = "select t FROM Tasks t inner join t.job j where t.deletedAt is null and t.completed = false " +
                " and t.worker is null and j.id in (:ids) group by t.id having t.id = max(t.id) ";
        Tasks task = null;
        try {

            task = (Tasks) session.createQuery(hql)
                    .setMaxResults(1)
                    .setParameterList("ids", ids)
                    .getSingleResult();
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            session.close();
        }
        return task;
    }

    public List<Tasks> getAllWithServiced() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tasks> criteriaQuery = criteriaBuilder.createQuery(Tasks.class);
        Root<Tasks> root = criteriaQuery.from(Tasks.class);

        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        criteriaQuery.where(predicate);
        List<Tasks> tasks = session.createQuery(criteriaQuery).list();
        session.close();
        return tasks;
    }

    public Tasks getById(Long id) {
        Optional<Tasks> jobOptional = taskRepository.findById(id);

        if (jobOptional.isPresent()) {
            return jobOptional.get();
        } else {
            return null;
        }
    }

    public boolean add(Tasks task) {
        if (task == null || task.getId() != null || task.getJob() == null) {
            return false;
        } else {
            taskRepository.save(task);
            return true;
        }
    }

    public boolean update(Tasks task) {
        if (task == null || task.getId() == null) {
            return false;
        } else {
            taskRepository.save(task);
            return true;
        }
    }

    public boolean realDelete(Tasks job) {
        if (job == null || job.getId() == null) {
            return false;
        } else {
            taskRepository.delete(job);
            return true;
        }
    }

    public boolean delete(Tasks task) {
        if (task == null || task.getId() == null) {
            return false;
        } else {
            task.setDeletedAt(new Date());
            taskRepository.save(task);
            return true;
        }
    }

}
