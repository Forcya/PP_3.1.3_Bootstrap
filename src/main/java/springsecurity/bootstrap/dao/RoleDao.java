package springsecurity.bootstrap.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springsecurity.bootstrap.entity.Role;

import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<Role, Long>  {
    List<Role> findAll();
    Role getRoleByName(String name);
}
