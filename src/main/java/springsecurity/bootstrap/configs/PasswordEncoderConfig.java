package springsecurity.bootstrap.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    //Кодировка паролей (для сверки паролей в БД с получаемыми паролями из поля)
    //Преобразовывает введенные на странице пароли в обычном виде в bcrypt, чтобы метод daoAuthenticationProvider мог
    //Сравнить с bcrypt паролем из БД
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
