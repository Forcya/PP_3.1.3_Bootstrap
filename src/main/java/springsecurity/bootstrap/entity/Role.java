package springsecurity.bootstrap.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
@Table(name="roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="name")
    private String name;
//    @ManyToMany(mappedBy = "roles")
//    public Set<User> users; //Получается бесконечный цикл

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getOnlyName() { return name.substring(5); }

    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public String toString() {
        return getName(); //Чтобы на View возвращать роль User-а по имени
    }
}
