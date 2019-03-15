package kz.kasya.realq.repositories;

import kz.kasya.realq.models.entities.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Repository
public interface JobRepository extends JpaRepository<Jobs,Long> {

}
