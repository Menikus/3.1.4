package ru.kata.spring.boot_security.demo.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    public List<Role> findAll();

    public Role findById(int id);

    public void save(Role role);

    public void update(int id, Role updRole);

    public void delete(int id);
}
