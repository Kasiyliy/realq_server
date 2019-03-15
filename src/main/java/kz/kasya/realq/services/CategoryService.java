package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Categories;
import kz.kasya.realq.repositories.CategoryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
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


    private SessionFactory hibernateFactory;

    @Autowired
    public CategoryService(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
    }

    public List<Categories> getAllWithTrashed(){
        return categoryRepository.findAll();
    }

    public List<Categories> getAll(){
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Categories> criteriaQuery = criteriaBuilder.createQuery(Categories.class);
        Root<Categories> root = criteriaQuery.from(Categories.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<Categories> categories = session.createQuery(criteriaQuery).list();
        session.close();
        return  categories;
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
            category.setDeletedAt(new Date());
            categoryRepository.save(category);
            return true;
        }
    }

    public boolean realDelete(Categories category){
        if(category==null || category.getId()==null){
            return false;
        }else{
            categoryRepository.delete(category);
            return true;
        }
    }

}
