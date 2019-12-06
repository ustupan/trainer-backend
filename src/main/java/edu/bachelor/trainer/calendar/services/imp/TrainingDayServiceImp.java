package edu.bachelor.trainer.calendar.services.imp;

import edu.bachelor.trainer.calendar.controllers.dtos.TrainingDayDto;
import edu.bachelor.trainer.calendar.services.TrainingDayService;
import edu.bachelor.trainer.common.exceptions.CalendarNotExistException;
import edu.bachelor.trainer.repository.CalendarRepository;
import edu.bachelor.trainer.repository.TrainingDayRepository;
import edu.bachelor.trainer.repository.entities.Calendar;
import edu.bachelor.trainer.repository.entities.TrainingDay;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingDayServiceImp implements TrainingDayService {

    private final CalendarRepository calendarRepository;
    private final TrainingDayRepository trainingDayRepository;

    TrainingDayServiceImp(CalendarRepository calendarRepository, TrainingDayRepository trainingDayRepository){
        this.calendarRepository = calendarRepository;
        this.trainingDayRepository = trainingDayRepository;
    }

    @Override
    public TrainingDay createTrainingDay(TrainingDayDto trainingDayDto) {

        Optional<Calendar> calendar = calendarRepository.findById(trainingDayDto.getCalendarId());

        if (!calendar.isPresent()) {
            throw new CalendarNotExistException("Calendar does not exist");
        }

        TrainingDay trainingDay = new TrainingDay();
        trainingDay.setDescription(trainingDayDto.getDescription());
        trainingDay.setTitle(trainingDayDto.getTitle());
        trainingDay.setTrainingDate(trainingDayDto.getTrainingDate());
        trainingDay.setCalendar(calendar.get());

        return trainingDayRepository.save(trainingDay);
    }

    private boolean calendarNotExist(long calendarId) {
        Optional<Calendar> calendar = calendarRepository.findById(calendarId);
        return !calendar.isPresent();
    }
}
