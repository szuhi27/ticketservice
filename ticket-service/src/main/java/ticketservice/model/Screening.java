package ticketservice.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "screening")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String movie;
    private String room;
    private LocalDateTime date;

    //TODO
    @Override
    public String toString() {
        return movie + ", screened in room " + room + ", at "
            + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
