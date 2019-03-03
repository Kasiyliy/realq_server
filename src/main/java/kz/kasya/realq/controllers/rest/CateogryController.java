package kz.kasya.realq.controllers.rest;

import kz.kasya.realq.models.Categories;
import kz.kasya.realq.services.CategoryService;
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
public class CateogryController {

    @Autowired
    CategoryService categoryService;


    @GetMapping(path = "categories")
    public ResponseEntity<List<Categories>> index(){
        return  new ResponseEntity<List<Categories>>(categoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "categories/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Categories category = categoryService.getById(id);

        return category!=null ? new ResponseEntity(category, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(path = "categories/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Categories category = categoryService.getById(id);
        return categoryService.delete(category)? ResponseEntity.status(HttpStatus.OK).build():
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @RequestMapping(path = "categories/{id}", method = {RequestMethod.PATCH , RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Categories category){
        return categoryService.update(category) ? new ResponseEntity(category, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "categories")
    public ResponseEntity add(@RequestBody Categories category){
        return categoryService.add(category) ? ResponseEntity.status(HttpStatus.ACCEPTED).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
