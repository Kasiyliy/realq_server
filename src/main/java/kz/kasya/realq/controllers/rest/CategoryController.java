package kz.kasya.realq.controllers.rest;

import kz.kasya.realq.models.entities.Categories;
import kz.kasya.realq.models.entities.Roles;
import kz.kasya.realq.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class CategoryController {


    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "categories")
    public ResponseEntity<List<Categories>> index(){
        return  new ResponseEntity<List<Categories>>(categoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "categories/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Categories category = categoryService.getById(id);

        return category!=null ? new ResponseEntity<Categories>(category, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "categories/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Categories category = categoryService.getById(id);
        return categoryService.delete(category)? ResponseEntity.status(HttpStatus.OK).build():
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "categories/{id}", method = {RequestMethod.PATCH , RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Categories category){
        return categoryService.update(category) ? new ResponseEntity<Categories>(category, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "categories")
    public ResponseEntity<Categories> add(@RequestBody Categories category){
        return categoryService.add(category) ? new ResponseEntity<Categories>(category,HttpStatus.ACCEPTED):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
