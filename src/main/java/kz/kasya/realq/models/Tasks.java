package kz.kasya.realq.models;

import kz.kasya.realq.models.audits.AuditModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

    @ManyToOne
    @NotNull(message = "job is required")
    private Jobs job;

    public Tasks(String iin, Jobs job) {
        this.iin = iin;
        this.job = job;
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

}
