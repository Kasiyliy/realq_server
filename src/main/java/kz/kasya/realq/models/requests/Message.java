package kz.kasya.realq.models.requests;

import kz.kasya.realq.models.entities.Tasks;
import kz.kasya.realq.models.requests.MessageCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Assylkhan
 * on 13.02.2019
 * @project realq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String content;
    private String sender;
    private MessageCode messageCode;
    private List<Tasks> tasks = new ArrayList<>();
}
