package kz.kasya.realq.models.requests;

import kz.kasya.realq.models.entities.Tasks;
import kz.kasya.realq.models.entities.Workers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Assylkhan
 * on 13.02.2019
 * @project realq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private String content;
    private String sender;
    private MessageCode messageCode;
    private Workers worker;
    private Tasks task;
    private Tasks addedTask;
}
