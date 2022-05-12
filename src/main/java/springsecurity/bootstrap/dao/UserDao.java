package springsecurity.bootstrap.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springsecurity.bootstrap.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User getUserByUsername(String username);
}
