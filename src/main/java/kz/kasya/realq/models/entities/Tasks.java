package kz.kasya.realq.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.kasya.realq.models.audits.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Tasks extends AuditModel {

    @NotNull(message = "iin is required")
    private String iin;

    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull(message = "job is required")
    private Jobs job;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private Workers worker;

    private boolean completed = false;

    public Tasks(@NotNull(message = "iin is required") String iin, @NotNull(message = "job is required") Jobs job,
                 Workers worker, boolean completed) {
        this.iin = iin;
        this.job = job;
        this.worker = worker;
        this.completed = completed;
    }

}