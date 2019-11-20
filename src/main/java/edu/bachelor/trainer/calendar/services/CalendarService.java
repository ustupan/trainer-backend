package edu.bachelor.trainer.calendar.services;

import edu.bachelor.trainer.calendar.controllers.dtos.CalendarDto;
import edu.bachelor.trainer.repository.entities.Calendar;

public interface CalendarService {
    Calendar createCalendar(CalendarDto calendarDto, String jwtToken);
}
