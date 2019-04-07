package kz.kasya.realq.controllers.rest;

import kz.kasya.realq.models.entities.Workers;
import kz.kasya.realq.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Assylkhan
 * on 02.03.2019
 * @project realq
 */
@RestController
@RequestMapping(path = "api")
@CrossOrigin("*")
public class WorkerController {

    private WorkerService workerService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WorkerController(WorkerService workerService,
                            BCryptPasswordEncoder bCryptPasswordEncoder){
        this.workerService = workerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping(path = "workers")
    public ResponseEntity<List<Workers>> index(){
        return  new ResponseEntity<List<Workers>>(workerService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "workers/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Workers worker = workerService.getById(id);

        return worker!=null ? new ResponseEntity<Workers>(worker, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(path = "workers/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Workers category = workerService.getById(id);
        return workerService.delete(category)? ResponseEntity.status(HttpStatus.OK).build():
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @RequestMapping(path = "workers/{id}", method = {RequestMethod.PATCH , RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Workers worker){
        return workerService.update(worker) ? new ResponseEntity<Workers>(worker, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "workers")
    public ResponseEntity add(@RequestBody Workers worker){
        if(worker != null || worker.getPassword() != null){
            worker.setPassword(bCryptPasswordEncoder.encode(worker.getPassword()));
        }else{
            return ResponseEntity.badRequest().build();
        }
        return workerService.add(worker) ? new ResponseEntity<Workers>(worker,HttpStatus.ACCEPTED) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
