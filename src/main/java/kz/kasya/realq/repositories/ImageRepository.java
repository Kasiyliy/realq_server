package kz.kasya.realq.repositories;

import kz.kasya.realq.models.entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Repository
public interface ImageRepository extends JpaRepository<Images,Long> {

    Optional<Images> findImageByJobIdAndDeletedAtIsNull(Long jobId);

    List<Images> findAllByDeletedAtIsNull();

}