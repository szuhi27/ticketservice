package ticketservice.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "login")
@ToString
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String uname;
    private AccountRole role;

    public Login(String uname, AccountRole role) {
        this.uname = uname;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Login login = (Login) o;
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
