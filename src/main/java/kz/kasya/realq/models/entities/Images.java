package kz.kasya.realq.models.entities;

import kz.kasya.realq.models.audits.AuditModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Images extends AuditModel {

    @Column(name = "path")
    String path;

    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    Jobs job;

}
