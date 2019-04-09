package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Categories;

import java.util.List;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
public interface CategoryService {

    List<Categories> getAllWithTrashed();

    List<Categories> getAll();

    Categories getById(Long id);

    boolean add(Categories category);

    boolean update(Categories category);

    boolean delete(Categories category);

    boolean realDelete(Categories category);

}
