package kz.kasya.realq.services;

import kz.kasya.realq.models.Tasks;
import kz.kasya.realq.models.Workers;
import kz.kasya.realq.repositories.WorkerRepository;
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
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;

    private SessionFactory hibernateFactory;

    @Autowired
    public WorkerService(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
    }

    public Workers getById(Long id){
        Optional<Workers> jobOptional = workerRepository.findById(id);

        if(jobOptional.isPresent()){
            return jobOptional.get();
        }else{
            return null;
        }
    }

    public List<Workers> getAllWithTrashed(){
        return workerRepository.findAll();
    }

    public List<Workers> getAll(){
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Workers> criteriaQuery = criteriaBuilder.createQuery(Workers.class);
        Root<Workers> root = criteriaQuery.from(Workers.class);

        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        criteriaQuery.where(predicate);
        List<Workers> workers = session.createQuery(criteriaQuery).list();
        session.close();
        return  workers;
    }

    public boolean add(Workers worker){
        if(worker==null || worker.getId()!=null){
            return false;
        }else{
            workerRepository.save(worker);
            return true;
        }
    }

    public boolean update(Workers job){
        if(job==null || job.getId()==null){
            return false;
        }else{
            workerRepository.save(job);
            return true;
        }
    }

    public boolean realDelete(Workers job){
        if(job==null || job.getId()==null){
            return false;
        }else{
            workerRepository.delete(job);
            return true;
        }
    }

    public boolean delete(Workers worker){
        if(worker==null || worker.getId()==null){
            return false;
        }else{
            worker.setDeletedAt(new Date());
            workerRepository.save(worker);
            return true;
        }
    }


}
