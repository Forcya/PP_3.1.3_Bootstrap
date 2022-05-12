package springsecurity.bootstrap.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "User4")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "age")
    private int age;
    @Column(name = "email")
    private String email;
    @Column(name = "login")
    private String username;
    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "users_roles", //тут создается 3-я вспомогательная (Join) таблица со связями дву других таблиц, прописываем ее имя
            joinColumns = @JoinColumn(name = "user_id"), //каким столбцом связана 3-я таблица с 1-ой
            inverseJoinColumns = @JoinColumn(name = "role_id")) //каким столбцом связана 3-я таблица с 2-ой
    private Collection<Role> roles = new HashSet<>(); //Создаем Коллекцию ролей

    public User() {
    }

    public User(String name, String surname, int age, String email, String username, String password, Collection<Role> roles) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Set<String> getNamesOfRoles() {
        return getRoles().stream().map(Role::getName)
                .map(name -> name.startsWith("ROLE_") ? name.substring(5) : name).collect(Collectors.toSet());
    }

    //Метод для вывода имеющихся у User-а ролей на View (update)
    public boolean containsRoleName(String roleName) {
        return roles.stream().map(Role::getName).collect(Collectors.toList()).contains(roleName);
    }

    //Преобразовываем роли User-а в права для преобразования User-а из БД в User-а Spring (UserDetails)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    //Флажки вкл/выкл. Внутри методов описание
    @Override
    public boolean isAccountNonExpired() { //true если учетная запись пользователя действительна (т.е. не просрочена)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //true если пользователь не заблокирован
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //true если учетные данные пользователя действительны (т. е. не просрочены)
        return true;
    }

    @Override
    public boolean isEnabled() { //true если пользователь включен
        return true;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}





