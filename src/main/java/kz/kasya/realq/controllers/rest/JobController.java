package kz.kasya.realq.controllers.rest;

import kz.kasya.realq.models.Jobs;
import kz.kasya.realq.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@RestController
@RequestMapping(path = "api")
@CrossOrigin("*")
public class JobController {


    @Autowired
    JobService jobService;

    @GetMapping(path = "jobs")
    public ResponseEntity<List<Jobs>> index(){
        return  new ResponseEntity<List<Jobs>>(jobService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "jobs/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Jobs job = jobService.getById(id);

        return job!=null ? new ResponseEntity<Jobs>(job, HttpStatus.OK):
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(path = "jobs/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Jobs job = jobService.getById(id);
            return jobService.delete(job)? ResponseEntity.status(HttpStatus.OK).build():
           ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @RequestMapping(path = "jobs/{id}", method = {RequestMethod.PATCH , RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Jobs job){
        return jobService.update(job) ? new ResponseEntity<Jobs>(job, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "jobs")
    public ResponseEntity add(@RequestBody Jobs job){
        return jobService.add(job) ? new ResponseEntity<Jobs>(job,HttpStatus.ACCEPTED):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
