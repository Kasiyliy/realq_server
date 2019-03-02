package kz.kasya.realq.models;

import kz.kasya.realq.models.audits.AuditModel;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */

@Entity
@Table(name = "workers")
public class Workers extends AuditModel {
    private String name;

    private String login;

    private String password;

    @ManyToOne
    private Tasks task;

    @ManyToMany
    private Set<Jobs> jobs = new HashSet<>();

    public Workers() {
    }

    public Workers(String name, String login, String password, Tasks task, Set<Jobs> jobs) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.task = task;
        this.jobs = jobs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Tasks getTask() {
        return task;
    }

    public void setTask(Tasks task) {
        this.task = task;
    }

    public Set<Jobs> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Jobs> jobs) {
        this.jobs = jobs;
    }
}
