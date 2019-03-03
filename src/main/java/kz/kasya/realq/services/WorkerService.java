package kz.kasya.realq.services;

import kz.kasya.realq.models.Workers;
import kz.kasya.realq.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Workers> getAll(){
        return workerRepository.findAll();
    }

    public Workers getById(Long id){
        Optional<Workers> jobOptional = workerRepository.findById(id);

        if(jobOptional.isPresent()){
            return jobOptional.get();
        }else{
            return null;
        }
    }

    public boolean add(Workers job){
        if(job==null || job.getId()!=null || job.getTask()==null){
            return false;
        }else{
            workerRepository.save(job);
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

    public boolean delete(Workers job){
        if(job==null || job.getId()==null){
            return false;
        }else{
            workerRepository.delete(job);
            return true;
        }
    }

}
