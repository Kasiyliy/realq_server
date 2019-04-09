package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Tasks;
import kz.kasya.realq.models.entities.Workers;

/**
 * @author Assylkhan
 * on 11.03.2019
 * @project realq
 */

public interface CalculationService {

    Workers getTaskForWorker(Workers worker);

    void addTask(Tasks task);

    Workers getWorkerForTasks();

}
