package kz.kasya.realq.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.kasya.realq.models.audits.AuditModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */

@Entity
@Table(name = "workers")
@Data
@NoArgsConstructor
public class Workers extends AuditModel {

    @NotNull(message = "name is required")
    private String name;

    @Column(unique = true)
    @NotNull(message = "login is required")
    private String login;

    @NotNull(message = "password is required")
    private String password;

    @ManyToOne(cascade=CascadeType.MERGE)
    private Tasks task;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Jobs> jobs = new HashSet<>();

    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Roles role;


    public Workers(@NotNull(message = "name is required") String name,
                   @NotNull(message = "login is required") String login,
                   @NotNull(message = "password is required") String password,
                   @NotNull(message = "roles is required") Roles role,
                   Tasks task,
                   Set<Jobs> jobs
    ) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.task = task;
        this.jobs = jobs;
        this.role = role;
    }

    public void setTask(Tasks task) {
        this.task = task;
        this.setUpdatedAt(new Date());
    }
}
