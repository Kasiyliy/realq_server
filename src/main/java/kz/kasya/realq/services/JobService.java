package kz.kasya.realq.services;

import kz.kasya.realq.models.Jobs;
import kz.kasya.realq.repositories.JobRepository;
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
public class JobService {
    @Autowired
    JobRepository jobRepository;

    public List<Jobs> getAll(){
        return jobRepository.findAll();
    }

    public Jobs getById(Long id){
        Optional<Jobs> jobOptional = jobRepository.findById(id);

        if(jobOptional.isPresent()){
            return jobOptional.get();
        }else{
            return null;
        }
    }

    public boolean add(Jobs job){
        if(job==null || job.getId()!=null || job.getCategory()==null){
            return false;
        }else{
            jobRepository.save(job);
            return true;
        }
    }

    public boolean update(Jobs job){
        if(job==null || job.getId()==null){
            return false;
        }else{
            jobRepository.save(job);
            return true;
        }
    }

    public boolean delete(Jobs job){
        if(job==null || job.getId()==null){
            return false;
        }else{
            jobRepository.delete(job);
            return true;
        }
    }

}
