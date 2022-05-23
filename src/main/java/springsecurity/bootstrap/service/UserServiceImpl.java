package springsecurity.bootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springsecurity.bootstrap.dao.UserDao;
import springsecurity.bootstrap.entity.User;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    public User getUser(long id) {
        return userDao.findById(id).get(); //Меняем с getById(id) на findById(id).get()
    }

    @Override
    public void deleteUser(long id) {
        userDao.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.save(user);
    }

    @Override
    //Тот самый подходящий метод для поиска User-а по имени
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}
