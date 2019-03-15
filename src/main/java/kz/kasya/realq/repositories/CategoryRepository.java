package kz.kasya.realq.repositories;

import kz.kasya.realq.models.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Repository
public interface CategoryRepository extends JpaRepository<Categories,Long> {

}