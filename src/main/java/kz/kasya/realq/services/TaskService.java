package kz.kasya.realq.services;

import kz.kasya.realq.models.Tasks;
import kz.kasya.realq.repositories.TaskRepository;
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
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    private SessionFactory hibernateFactory;

    @Autowired
    public TaskService(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
    }

    public List<Tasks> getAllWithTrashed(){
        return taskRepository.findAll();
    }

    public List<Tasks> getAll(Boolean desc){
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tasks> criteriaQuery = criteriaBuilder.createQuery(Tasks.class);
        Root<Tasks> root = criteriaQuery.from(Tasks.class);

        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        Predicate predicate2 = criteriaBuilder.isNull(root.get("worker"));
        Predicate andPredicate = criteriaBuilder.and(predicate,predicate2);
        if(desc == null || desc.equals(true)){
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        }else{
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
        }
        criteriaQuery.where(andPredicate);
        List<Tasks> tasks = session.createQuery(criteriaQuery).list();
        session.close();
        return  tasks;
    }

    public List<Tasks> getAllWithFixedNumberAndExcept(int count, List<Long> ids, Boolean desc){
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tasks> criteriaQuery = criteriaBuilder.createQuery(Tasks.class);
        Root<Tasks> root = criteriaQuery.from(Tasks.class);

        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        Predicate predicate2 = criteriaBuilder.isNull(root.get("worker"));
        Predicate predicate3 = criteriaBuilder.not(root.get("id").in(ids));
        Predicate andPredicate = criteriaBuilder.and(predicate,predicate2, predicate3);
        if(desc == null || desc.equals(true)){
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        }else{
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
        }
        criteriaQuery.where(andPredicate);
        List<Tasks> tasks = session.createQuery(criteriaQuery).setMaxResults(count).list();
        session.close();
        return  tasks;
    }

    public List<Tasks> getAllWithServiced(){
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Tasks> criteriaQuery = criteriaBuilder.createQuery(Tasks.class);
        Root<Tasks> root = criteriaQuery.from(Tasks.class);

        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        criteriaQuery.where(predicate);
        List<Tasks> tasks = session.createQuery(criteriaQuery).list();
        session.close();
        return  tasks;
    }

    public Tasks getById(Long id){
        Optional<Tasks> jobOptional = taskRepository.findById(id);

        if(jobOptional.isPresent()){
            return jobOptional.get();
        }else{
            return null;
        }
    }

    public boolean add(Tasks task){
        if(task==null || task.getId()!=null || task.getJob()==null){
            return false;
        }else{
            taskRepository.save(task);
            return true;
        }
    }

    public boolean update(Tasks task){
        if(task==null || task.getId()==null){
            return false;
        }else{
            taskRepository.save(task);
            return true;
        }
    }

    public boolean realDelete(Tasks job){
        if(job==null || job.getId()==null){
            return false;
        }else{
            taskRepository.delete(job);
            return true;
        }
    }

    public boolean delete(Tasks task){
        if(task==null || task.getId()==null){
            return false;
        }else{
            task.setDeletedAt(new Date());
            taskRepository.save(task);
            return true;
        }
    }

}
