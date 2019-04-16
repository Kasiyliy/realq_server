package kz.kasya.realq.services.impl;

import kz.kasya.realq.models.entities.Roles;
import kz.kasya.realq.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Assylkhan
 * on 28.02.2019
 * @project realq
 */
@Service
public class RoleServiceImpl {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Roles> getAll() {
        return roleRepository.findAll();
    }

    public Roles getById(Long id) {
        Roles r = new Roles();
        Optional<Roles> jobOptional = roleRepository.findById(id);
        return jobOptional.isPresent() ? jobOptional.get() : null;
    }

    public Roles getByName(String name) {
        Optional<Roles> jobOptional = roleRepository.findByName(name);
        return jobOptional.isPresent() ? jobOptional.get() : null;
    }

}
