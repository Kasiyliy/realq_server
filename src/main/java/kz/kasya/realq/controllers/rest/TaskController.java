package kz.kasya.realq.controllers.rest;

import kz.kasya.realq.models.entities.Tasks;
import kz.kasya.realq.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    public ResponseEntity<List<Tasks>> index(@RequestParam(required = false) Integer count,
                                             @RequestParam(required = false) Long ids[],
                                             @RequestParam(required = false) Boolean desc){

        List<Tasks> tasks = null;

        if(count!=null && ids == null){
            tasks = taskService.getAllWithFixedNumberAndExcept(count, Arrays.asList(0L), desc);
        }else if(count!=null && ids !=null){
            tasks = taskService.getAllWithFixedNumberAndExcept(count,Arrays.asList(ids), desc);
        }else{
            tasks = taskService.getAll(desc);
        }
        return  new ResponseEntity<List<Tasks>>(tasks, HttpStatus.OK);
    }

    @GetMapping(path = "tasks/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Tasks task = taskService.getById(id);

        return task!=null ? new ResponseEntity<Tasks>(task, HttpStatus.OK):
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
        return taskService.update(task) ? new ResponseEntity<Tasks>(task, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "tasks")
    public ResponseEntity add(@RequestBody Tasks task){
        return taskService.add(task) ? new ResponseEntity<Tasks>(task,HttpStatus.ACCEPTED):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

}
