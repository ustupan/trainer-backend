package edu.bachelor.trainer.repository;

import edu.bachelor.trainer.repository.entities.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Optional<Calendar> findById(Long calendarId);
    Optional<Calendar> getByAthleteIdAndTrainerId(Long athlete_id, Long trainer_id);

}
