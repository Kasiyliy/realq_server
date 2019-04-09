package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Categories;
import kz.kasya.realq.models.entities.Jobs;

import java.util.List;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
public interface JobService {


    List<Jobs> getAllWithTrashed();

    List<Jobs> getAll();

    List<Jobs> getAllBy(Categories category);

    Jobs getById(Long id);

    boolean add(Jobs job);

    boolean update(Jobs job);

    boolean realDelete(Jobs job);

    boolean delete(Jobs job);

}
