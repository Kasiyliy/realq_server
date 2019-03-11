package kz.kasya.realq.models;

import kz.kasya.realq.models.audits.AuditModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Entity
@Table(name = "tasks")
public class Tasks extends AuditModel{

    @NotNull(message = "iin is required")
    private String iin;

    @ManyToOne(cascade=CascadeType.MERGE)
    @NotNull(message = "job is required")
    private Jobs job;


    @ManyToOne(cascade=CascadeType.MERGE)
    private Workers worker;

    private boolean completed = false;

    public Tasks(@NotNull(message = "iin is required") String iin, @NotNull(message = "job is required") Jobs job,
                 Workers worker, boolean completed) {
        this.iin = iin;
        this.job = job;
        this.worker = worker;
        this.completed = completed;
    }

    public Tasks() {
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public Jobs getJob() {
        return job;
    }

    public void setJob(Jobs job) {
        this.job = job;
    }

    public Workers getWorker() {
        return worker;
    }

    public void setWorker(Workers worker) {
        this.worker = worker;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}