package springsecurity.bootstrap.service;

import springsecurity.bootstrap.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void saveUser(User user);

    User getUser(long id);

    void deleteUser(long id);

    void updateUser(User user);

    User getUserByUsername(String username);
}
