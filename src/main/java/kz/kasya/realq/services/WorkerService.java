package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Jobs;
import kz.kasya.realq.models.entities.Tasks;
import kz.kasya.realq.models.entities.Workers;
import kz.kasya.realq.repositories.WorkerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

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

    Set getAuthority(Workers user);

    Workers getFreeWorkerByJobs(Jobs job);

    List<Workers> getAllWithTrashed();

    List<Workers> getAll();

    boolean add(Workers worker);

    boolean update(Workers job);

    boolean realDelete(Workers job);

    boolean delete(Workers worker);

    List<Workers> getAllManagers();

}
