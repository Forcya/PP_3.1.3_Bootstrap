package springsecurity.bootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springsecurity.bootstrap.dao.RoleDao;
import springsecurity.bootstrap.entity.Role;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public List<Role> findAllRoles() {
        return roleDao.findAll();
    }

    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}
