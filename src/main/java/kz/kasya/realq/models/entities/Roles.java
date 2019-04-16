package kz.kasya.realq.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * @author Assylkhan
 * on 12.03.2019
 * @project realq
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles {

    public final static Long ROLE_ADMIN_ID = 1L;
    public final static Long ROLE_MANAGER_ID = 2L;
    public final static Long ROLE_GUEST_ID = 3L;

    public final static String ROLE_ADMIN_NAME = "ROLE_ADMIN";
    public final static String ROLE_MANAGER_NAME = "ROLE_MANAGER";
    public final static String ROLE_GUEST_NAME = "ROLE_GUEST";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @Column
    private String description;
}
