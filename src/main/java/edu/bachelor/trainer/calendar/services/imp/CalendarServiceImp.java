package edu.bachelor.trainer.calendar.services.imp;

import edu.bachelor.trainer.calendar.controllers.dtos.CalendarDto;
import edu.bachelor.trainer.calendar.services.CalendarService;
import edu.bachelor.trainer.repository.AthleteRepository;
import edu.bachelor.trainer.repository.CalendarRepository;
import edu.bachelor.trainer.repository.TrainerRepository;
import edu.bachelor.trainer.repository.entities.Athlete;
import edu.bachelor.trainer.repository.entities.Calendar;
import edu.bachelor.trainer.repository.entities.Trainer;
import org.springframework.stereotype.Service;

@Service
public class CalendarServiceImp implements CalendarService {

    private final TrainerRepository trainerRepository;
    private final AthleteRepository athleteRepository;
    private final CalendarRepository calendarRepository;


    CalendarServiceImp(TrainerRepository trainerRepository,
                       AthleteRepository athleteRepository,
                       CalendarRepository calendarRepository){
        this.trainerRepository = trainerRepository;
        this.athleteRepository = athleteRepository;
        this.calendarRepository = calendarRepository;
    }

    @Override
    public Calendar createCalendar(CalendarDto calendarDto) {

        Trainer trainer = trainerRepository.getOne(calendarDto.getTrainerId());
        Athlete athlete = athleteRepository.getOne(calendarDto.getAthleteId());

        Calendar calendar = new Calendar();
        calendar.setTrainer(trainer);
        calendar.setAthlete(athlete);
        calendar.setTitle(calendarDto.getTitle());

        return calendarRepository.save(calendar);
    }
}
