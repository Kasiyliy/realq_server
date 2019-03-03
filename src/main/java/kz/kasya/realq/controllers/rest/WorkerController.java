package kz.kasya.realq.controllers.rest;

import kz.kasya.realq.models.Workers;
import kz.kasya.realq.models.Workers;
import kz.kasya.realq.repositories.WorkerRepository;
import kz.kasya.realq.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    WorkerService workerService;


    @GetMapping(path = "workers")
    public ResponseEntity<List<Workers>> index(){
        return  new ResponseEntity<List<Workers>>(workerService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "workers/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Workers category = workerService.getById(id);

        return category!=null ? new ResponseEntity(category, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(path = "workers/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Workers category = workerService.getById(id);
        return workerService.delete(category)? ResponseEntity.status(HttpStatus.OK).build():
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @RequestMapping(path = "workers/{id}", method = {RequestMethod.PATCH , RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Workers category){
        return workerService.update(category) ? new ResponseEntity(category, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "workers")
    public ResponseEntity add(@RequestBody Workers category){
        return workerService.add(category) ? ResponseEntity.status(HttpStatus.ACCEPTED).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
