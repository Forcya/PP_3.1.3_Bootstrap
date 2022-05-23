package springsecurity.bootstrap.service;

import springsecurity.bootstrap.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();

    Role getRoleByName(String name);
}
