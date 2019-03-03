package kz.kasya.realq.services;

import kz.kasya.realq.models.Tasks;
import kz.kasya.realq.repositories.TaskRepository;
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
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public List<Tasks> getAll(){
        return taskRepository.findAll();
    }

    public Tasks getById(Long id){
        Optional<Tasks> jobOptional = taskRepository.findById(id);

        if(jobOptional.isPresent()){
            return jobOptional.get();
        }else{
            return null;
        }
    }

    public boolean add(Tasks job){
        if(job==null || job.getId()!=null || job.getJob()==null){
            return false;
        }else{
            taskRepository.save(job);
            return true;
        }
    }

    public boolean update(Tasks job){
        if(job==null || job.getId()==null){
            return false;
        }else{
            taskRepository.save(job);
            return true;
        }
    }

    public boolean delete(Tasks job){
        if(job==null || job.getId()==null){
            return false;
        }else{
            taskRepository.delete(job);
            return true;
        }
    }

}
