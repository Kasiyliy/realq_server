package kz.kasya.realq.models.entities;

import kz.kasya.realq.models.audits.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categories extends AuditModel{
    @NotNull(message = "name is required")
    private @NonNull String name;
}
