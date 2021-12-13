package ticketservice.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "screening")
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String movie;
    private String room;
    private LocalDateTime date;

    @Override
    public String toString() {
        return movie + ", screened in room " + room + ", at "
            + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Screening screening = (Screening) o;
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
