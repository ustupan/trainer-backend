package edu.bachelor.trainer.repository;

import edu.bachelor.trainer.repository.entities.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
