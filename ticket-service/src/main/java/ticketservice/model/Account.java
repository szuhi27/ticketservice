package ticketservice.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "account")
@ToString
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;
    private String passw;
    private AccountRole accountRole;

    public Account(String username, String passw,AccountRole role) {
        this.username = username;
        this.passw = passw;
        this.accountRole = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}