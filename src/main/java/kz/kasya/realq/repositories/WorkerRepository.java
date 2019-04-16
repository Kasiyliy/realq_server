package kz.kasya.realq.repositories;

import java.util.ArrayList;
import kz.kasya.realq.models.entities.Workers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * @author Assylkhan
 * on 03.03.2019
 * @project realq
 */

@Repository
public interface WorkerRepository extends JpaRepository<Workers,Long>{
    Workers findByLogin(String login);

    @Query(value = "select w from Workers  w inner join w.role r where r.id = 2 and w.deletedAt is null" )
    ArrayList<Workers> findAllManagerWorkers();
}
