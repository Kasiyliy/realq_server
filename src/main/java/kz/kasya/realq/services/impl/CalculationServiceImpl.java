package kz.kasya.realq.services.impl;

import kz.kasya.realq.models.entities.Tasks;
import kz.kasya.realq.models.entities.Workers;
import kz.kasya.realq.services.CalculationService;
import kz.kasya.realq.services.TaskService;
import kz.kasya.realq.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Assylkhan
 * on 9.04.2019
 * @project realq
 */
@Service
public class CalculationServiceImpl implements CalculationService{


    private TaskService taskService;
    private WorkerService workerService;

    @Autowired
    public CalculationServiceImpl(TaskService taskService,
                              WorkerService workerService) {
        this.taskService = taskService;
        this.workerService = workerService;
    }

    @Override
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

    @Override
    public synchronized void addTask(Tasks task) {
        taskService.add(task);
    }

    @Override
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
