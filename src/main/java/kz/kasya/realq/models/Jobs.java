package kz.kasya.realq.models;

import kz.kasya.realq.models.audits.AuditModel;

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
public class Jobs extends AuditModel{

    private String name;

    @ManyToOne
    @NotNull(message = "category is not null")
    private Categories category;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Jobs(String name, Categories category) {
        this.name = name;
        this.category = category;
    }

    public Jobs() {
    }
}
