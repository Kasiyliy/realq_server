package kz.kasya.realq.models.entities;

import kz.kasya.realq.models.audits.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Assylkhan
 * on 27.02.2019
 * @project realq
 */

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jobs extends AuditModel {

    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull(message = "category is required")
    private Categories category;
}
