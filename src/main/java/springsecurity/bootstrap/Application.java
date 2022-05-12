package springsecurity.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springsecurity.bootstrap.dao.UserDao;
import springsecurity.bootstrap.entity.User;

import java.util.Collection;
import java.util.HashSet;

@SpringBootApplication
public class Application {

	@Autowired
	private static UserDao userDao;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		Collection<String> roles = new HashSet<>();
//		roles.add("USER");
//		userDao.save(new User("Андрей", "Пыльник", 12, "korghik13@mail.ru", "Andrew", "$2a$12$w8q55RQFdpXj7.Lm3WDjGuIFP22oi1erVwz7mjt0OM2ROOCfqKVwS", ));
	}

}
