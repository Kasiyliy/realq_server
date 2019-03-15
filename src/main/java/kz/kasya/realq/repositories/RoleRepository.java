package kz.kasya.realq.repositories;

import kz.kasya.realq.models.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Assylkhan
 * on 15.03.2019
 * @project realq
 */
@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    public Optional<Roles> findByName(String name);

}
