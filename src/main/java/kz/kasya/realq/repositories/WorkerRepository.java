package kz.kasya.realq.repositories;

import kz.kasya.realq.models.entities.Workers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Assylkhan
 * on 03.03.2019
 * @project realq
 */

@Repository
public interface WorkerRepository extends JpaRepository<Workers,Long>{
    public Workers findByLogin(String login);
}
