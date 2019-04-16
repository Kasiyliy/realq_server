package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Jobs;
import kz.kasya.realq.models.entities.Workers;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
public interface WorkerService extends UserDetailsService {


    Workers getById(Long id);


    Workers findByLogin(String login);

    Workers findByLoginWithTrashed(String login);

    boolean isLoginAlreadyInUse(String login);

    List getAuthority(Workers user);

    Workers getFreeWorkerByJobs(Jobs job);

    List<Workers> getAllWithTrashed();

    List<Workers> getAll();

    boolean add(Workers worker);

    boolean update(Workers job);

    boolean realDelete(Workers job);

    boolean delete(Workers worker);

    List<Workers> getAllManagers();

}
