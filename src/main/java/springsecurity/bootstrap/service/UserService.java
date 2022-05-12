package springsecurity.bootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springsecurity.bootstrap.dao.UserDao;
import springsecurity.bootstrap.entity.User;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserDao userDao;


    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void saveUser(User user) {
        userDao.save(user);
    }

    public User getUser(long id) {
        return userDao.getById(id);
    }

    public void deleteUser(long id) {
        userDao.deleteById(id);
    }

    public void updateUser(User user) {
        userDao.save(user);
    }

    //Тот самый подходящий метод для поиска User-а по имени
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}
