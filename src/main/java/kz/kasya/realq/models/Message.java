package kz.kasya.realq.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Assylkhan
 * on 13.02.2019
 * @project realq
 */
public class Message {

    private String content;
    private String sender;
    private MessageCode messageCode;
    private List<Tasks> tasks = new ArrayList<>();
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public MessageCode getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(MessageCode messageCode) {
        this.messageCode = messageCode;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }
}
