package kz.kasya.realq.models;

import kz.kasya.realq.models.audits.AuditModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Entity
@Table(name = "categories")
public class Categories extends AuditModel{

    private String name;

    public Categories(String name) {
        this.name = name;
    }

    public Categories() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
