package kz.kasya.realq.services;

import kz.kasya.realq.models.Categories;
import kz.kasya.realq.repositories.CategoryRepository;
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
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Categories> getAll(){
        return categoryRepository.findAll();
    }

    public Categories getById(Long id){
        Optional<Categories> jobOptional = categoryRepository.findById(id);

        if(jobOptional.isPresent()){
            return jobOptional.get();
        }else{
            return null;
        }
    }

    public boolean add(Categories category){
        if(category==null || category.getId()!=null ){
            return false;
        }else{
            categoryRepository.save(category);
            return true;
        }
    }

    public boolean update(Categories category){
        if(category==null || category.getId()==null){
            return false;
        }else{
            categoryRepository.save(category);
            return true;
        }
    }

    public boolean delete(Categories category){
        if(category==null || category.getId()==null){
            return false;
        }else{
            categoryRepository.delete(category);
            return true;
        }
    }

}
