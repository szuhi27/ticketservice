package ticketservice.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "room")
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private int rows;
    private int columns;

    @Override
    public String toString() {
        return "Room " + name + " with " + rows * columns + " seats, " + rows + " rows and "
                + columns + " columns.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Room room = (Room) o;
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
