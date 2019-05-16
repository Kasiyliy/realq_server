package kz.kasya.realq.services.impl;

import kz.kasya.realq.models.entities.Images;
import kz.kasya.realq.repositories.ImageRepository;
import kz.kasya.realq.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Service
public class ImageServiceImpl implements ImageService{

    private ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Images> getAllWithTrashed() {
        return imageRepository.findAll();
    }

    public List<Images> getAll() {
        return imageRepository.findAllByDeletedAtIsNull();
    }


    public Images getById(Long id) {
        Optional<Images> imageOptional = imageRepository.findById(id);

        if (imageOptional.isPresent()) {
            return imageOptional.get();
        } else {
            return null;
        }
    }

    public Images getByJobId(Long id) {
        Optional<Images> imageOptional = imageRepository.findImageByJobIdAndDeletedAtIsNull(id);
        if (imageOptional.isPresent()) {
            return imageOptional.get();
        } else {
            return null;
        }
    }

    public boolean add(Images image) {
        if (image == null || image.getId() != null || image.getJob() == null) {
            return false;
        } else {
            imageRepository.save(image);
            return true;
        }
    }

    public boolean update(Images image,String oldImage) {
        if (image == null || image.getId() == null || image.getJob() == null) {
            return false;
        } else {
            File file = new File(oldImage);
            if(file.exists()){
                file.delete();
            }
            imageRepository.save(image);
            return true;
        }
    }

    public boolean realDelete(Images image) {
        if (image == null || image.getId() == null) {
            return false;
        } else {
            File file = new File(image.getPath());
            if(file.exists()){
                file.delete();
            }
            imageRepository.delete(image);
            return true;
        }
    }

    public boolean delete(Images image) {
        if (image == null || image.getId() == null) {
            return false;
        } else {
            image.setDeletedAt(new Date());
            imageRepository.save(image);
            return true;
        }
    }

}
