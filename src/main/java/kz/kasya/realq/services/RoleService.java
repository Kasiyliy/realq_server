package kz.kasya.realq.services;

import kz.kasya.realq.models.entities.Roles;

import java.util.List;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
public interface RoleService {

    List<Roles> getAll();

    Roles getById(Long id);

    Roles getByName(String name);

}
