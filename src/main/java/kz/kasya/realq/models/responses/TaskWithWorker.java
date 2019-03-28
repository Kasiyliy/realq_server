package kz.kasya.realq.models.responses;

import kz.kasya.realq.models.entities.Tasks;
import kz.kasya.realq.models.entities.Workers;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Assylkhan
 * on 27.03.2019
 * @project realq
 */
@NoArgsConstructor
@Data
@ToString
public class TaskWithWorker {
    private Tasks task;
    private Workers worker;

    public TaskWithWorker(Tasks task, Workers worker) {
        this.task = task;
        this.worker = worker;
    }
}
