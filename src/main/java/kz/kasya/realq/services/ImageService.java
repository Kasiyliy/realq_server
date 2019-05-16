package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Images;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
public interface ImageService {


    Images getById(Long id);

    Images getByJobId(Long imageId);

    boolean add(Images job);

    boolean update(Images job, String oldImage);

    boolean realDelete(Images job);

    boolean delete(Images job);

}
