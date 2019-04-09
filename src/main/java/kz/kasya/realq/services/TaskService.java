package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Jobs;
import kz.kasya.realq.models.entities.Tasks;
import kz.kasya.realq.models.responses.TaskWithWorker;

import java.util.List;
import java.util.Set;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
public interface TaskService {


    List<Tasks> getAllWithTrashed();

    List<Tasks> getAll(Boolean desc);

    List<TaskWithWorker> getAllNoCompleted(Boolean desc);

    List<Tasks> getAllWithFixedNumberAndExcept(int count, List<Long> ids, Boolean desc);

    Tasks getTaskByJobs(Set<Jobs> jobs);

    List<Tasks> getAllWithServiced();

    Tasks getById(Long id);

    boolean add(Tasks task);

    boolean update(Tasks task);

    boolean realDelete(Tasks job);

    boolean delete(Tasks task);

}
