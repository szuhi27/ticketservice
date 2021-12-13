package ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ticketservice.exception.DoesNotExistsException;
import ticketservice.model.Movie;
import ticketservice.model.Screening;
import ticketservice.repository.MovieRepository;
import ticketservice.repository.RoomRepository;
import ticketservice.repository.ScreeningRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.abs;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Screening screeningCreator(String movie, String room, String date) {
        LocalDateTime dateDate = LocalDateTime.parse(date, formatter);
        return Screening.builder()
                .movie(movie)
                .room(room)
                .date(dateDate)
                .build();
    }

    public void createScreening(Screening s) throws DoesNotExistsException {
        String st = checkScreeningErrorMsg(s.getMovie(), s.getRoom(), s.getDate());
        if (Objects.equals(st, "")) {
            screeningRepository.save(s);
        } else {
            throw new DoesNotExistsException(st);
        }
    }

    private String checkScreeningErrorMsg(String movie, String room, LocalDateTime date) {
        String msg = "";
        if (!movieRepository.existsByTitle(movie)) {
            msg = "Movie " + movie + " does not exist!";
        } else if (!roomRepository.existsByName(room)) {
            msg = "Room " + room + " does not exist!";
        } else {
            List<Screening> all = screeningRepository.findAll();
            Screening[] allArr = new Screening[all.size()];
            all.toArray(allArr);
            for (Screening screening : allArr) {
                if (Objects.equals(screening.getRoom(), room)
                        && !timeCheck(screening, movie, date)) {
                    msg = wrongTimeMsg(screening, movie, date);
                }
            }
        }
        return msg;
    }

    private boolean timeCheck(Screening planned, String movie, LocalDateTime currDate) {
        boolean good = true;
        long dur = Duration.between(planned.getDate(),currDate).toMinutes();
        if (planned.getDate().isBefore(currDate)) {
            if ((int)dur < movieLength(planned.getMovie()) + 10) {
                good = false;
            }
        } else {
            if ((int)dur < movieLength(movie) + 10) {
                good = false;
            }
        }
        return good;
    }

    private String wrongTimeMsg(Screening planned, String movie, LocalDateTime currDate) {
        String msg = "";
        if (planned.getDate().isBefore(currDate)) {
            long dur = abs(Duration.between(planned.getDate(),currDate).toMinutes());
            if ((int)dur < movieLength(planned.getMovie()) + 10) {
                msg = "This would start in the break period after another screening in this room";
                if ((int)dur < movieLength(planned.getMovie())) {
                    msg = "There is an overlapping screening";
                }
            }
        } else {
            long dur = abs(Duration.between(currDate,planned.getDate()).toMinutes());
            if ((int)dur < movieLength(movie) + 10) {
                msg = "This would start in the break period after another screening in this room";
                if ((int)dur < movieLength(movie)) {
                    msg = "There is an overlapping screening";
                }
            }
        }
        return msg;
    }

    private int movieLength(String movie) {
        Movie currMovie = movieRepository.findByTitle(movie);
        return currMovie.getLength();
    }

    public void deleteScreening(Screening screening) throws DoesNotExistsException {
        if (!screeningRepository.existsByMovieAndRoomAndDate(screening.getMovie(), screening.getRoom(),
                screening.getDate())) {
            throw new DoesNotExistsException("Screening does not exists!");
        } else {
            screeningRepository.deleteByMovieAndRoomAndDate(screening.getMovie(), screening.getRoom(),
                    screening.getDate());
        }
    }

    public List<Screening> listScreenings() {
        return screeningRepository.findAll();
    }

}
