package kz.kasya.realq.models;

import kz.kasya.realq.models.audits.AuditModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Entity
@Table(name = "tasks")
public class Tasks extends AuditModel{

    private String iin;

    @ManyToOne
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
