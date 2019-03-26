package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Tasks;
import kz.kasya.realq.models.entities.Workers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Assylkhan
 * on 11.03.2019
 * @project realq
 */
@Service
public class CalculationService {

    @Autowired
    TaskService taskService;

    @Autowired
    WorkerService workerService;

    public synchronized Workers getTaskForWorker(Workers worker) {
        Tasks task = taskService.getTaskByJobs(worker.getJobs());
        if (task != null) {
            worker.setTask(task);
            workerService.update(worker);
            task.setWorker(worker);
            taskService.update(task);
        }
        return worker;
    }

    public synchronized void addTask(Tasks task){
        taskService.add(task);
    }

    public synchronized Workers getWorkerForTasks() {
        List<Tasks> tasks = taskService.getAll(true);
        Workers w = null;
        for (Tasks t : tasks) {
            w = workerService.getFreeWorkerByJobs(t.getJob());
            if (w != null) {
                t.setWorker(w);
                w.setTask(t);
                taskService.update(t);
                workerService.update(w);
                break;
            }
        }
        return w;
    }


}
