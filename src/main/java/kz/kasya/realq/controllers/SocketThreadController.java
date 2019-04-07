package kz.kasya.realq.controllers;

import kz.kasya.realq.models.entities.Tasks;
import kz.kasya.realq.models.entities.Workers;
import kz.kasya.realq.models.requests.Message;
import kz.kasya.realq.models.requests.MessageCode;
import kz.kasya.realq.services.CalculationService;
import kz.kasya.realq.services.TaskService;
import kz.kasya.realq.services.WorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author Assylkhan
 * on 13.02.2019
 * @project realq
 */
@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SocketThreadController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessagingTemplate template;

    private CalculationService calculationService;

    private WorkerService workerService;

    private TaskService taskService;


    @Autowired
    public SocketThreadController(SimpMessagingTemplate template,
                                  CalculationService calculationService,
                                  WorkerService workerService,
                                  TaskService taskService) {
        this.template = template;
        this.calculationService = calculationService;
        this.workerService = workerService;
        this.taskService = taskService;
    }


    @MessageMapping("/socket")
    @SendTo("/thread/messages")
    public Message send(Message message) {
        logger.info("Message received: " + message.toString());
        Workers worker = !message.getSender().equalsIgnoreCase("anonymous")
                ? workerService.findByLogin(message.getSender()) : null;
        System.out.println(message.getSender() + ": " + message.getContent());
        System.out.println(message);
        if (message.getMessageCode() == MessageCode.TASK_ADDED) {
            calculationService.addTask(message.getTask());
            Workers w = calculationService.getWorkerForTasks();

            message.setAddedTask(message.getTask());

            if (w != null && w.getTask() != null) {
                message.setTask(w.getTask());
                message.setWorker(w);
                message.setMessageCode(MessageCode.TASK_ADDED_TASK_TAKEN);
            }
        } else if (worker != null) {

            if (message.getMessageCode() == MessageCode.DO_SEARCH) {
                worker = calculationService.getTaskForWorker(worker);
                if (worker.getTask() != null) {
                    message.setWorker(worker);
                    message.setMessageCode(MessageCode.TASK_TAKEN);
                }
            } else if (message.getMessageCode() == MessageCode.TASK_RELEASED) {
                if (worker.getTask() != null) {
                    Tasks task = worker.getTask();
                    task.setCompleted(true);
                    taskService.update(task);
                    worker.setTask(null);
                    workerService.update(worker);
                    message.setWorker(worker);
                    message.setMessageCode(MessageCode.FINISHED);
                    message.setTask(task);
                }
            }
        }
        return message;
    }


}
