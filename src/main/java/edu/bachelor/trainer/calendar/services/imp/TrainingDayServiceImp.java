package edu.bachelor.trainer.calendar.services.imp;

import edu.bachelor.trainer.calendar.controllers.dtos.TrainingDayDto;
import edu.bachelor.trainer.calendar.services.TrainingDayService;
import edu.bachelor.trainer.common.exceptions.CalendarNotExistException;
import edu.bachelor.trainer.common.exceptions.TrainingDayNotExistException;
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

    @Override
    public TrainingDay editTrainingDay(TrainingDayDto trainingDayDto) {
        Optional<Calendar> findCalendar = calendarRepository.findById(trainingDayDto.getCalendarId());
        Optional<TrainingDay> findTrainingDay = trainingDayRepository.findById(trainingDayDto.getId());
        if (!findCalendar.isPresent()) {
            throw new CalendarNotExistException("Calendar does not exist");
        }
        if(!findTrainingDay.isPresent()) {
            throw new TrainingDayNotExistException("Training day does not exist");
        }

        TrainingDay trainingDay = new TrainingDay();
        trainingDay.setId(trainingDayDto.getId());
        trainingDay.setDescription(trainingDayDto.getDescription());
        trainingDay.setTitle(trainingDayDto.getTitle());
        trainingDay.setTrainingDate(trainingDayDto.getTrainingDate());
        trainingDay.setCalendar(findCalendar.get());
        trainingDay.setDispositionLevel(trainingDayDto.getDispositionLevel());
        trainingDay.setMotivationLevel(trainingDayDto.getMotivationLevel());
        trainingDay.setNote(trainingDayDto.getNote());

        return trainingDayRepository.save(trainingDay);
    }


}
