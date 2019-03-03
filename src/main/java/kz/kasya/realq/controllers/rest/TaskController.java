package kz.kasya.realq.controllers.rest;

import kz.kasya.realq.models.Tasks;
import kz.kasya.realq.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Assylkhan
 * on 03.03.2019
 * @project realq
 */
@RestController
@RequestMapping(path = "api")
@CrossOrigin("*")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping(path = "tasks")
    public ResponseEntity<List<Tasks>> index(){
        return  new ResponseEntity<List<Tasks>>(taskService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "tasks/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Tasks task = taskService.getById(id);

        return task!=null ? new ResponseEntity(task, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(path = "tasks/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Tasks task = taskService.getById(id);
        return taskService.delete(task)? ResponseEntity.status(HttpStatus.OK).build():
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @RequestMapping(path = "tasks/{id}", method = {RequestMethod.PATCH , RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Tasks task){
        return taskService.update(task) ? new ResponseEntity(task, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "tasks")
    public ResponseEntity add(@RequestBody Tasks task){
        return taskService.add(task) ? ResponseEntity.status(HttpStatus.ACCEPTED).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

}
