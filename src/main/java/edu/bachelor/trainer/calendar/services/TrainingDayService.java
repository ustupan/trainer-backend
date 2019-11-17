package edu.bachelor.trainer.calendar.services;

import edu.bachelor.trainer.calendar.controllers.dtos.TrainingDayDto;
import edu.bachelor.trainer.repository.entities.TrainingDay;

public interface TrainingDayService {
    TrainingDay createTrainingDay(TrainingDayDto trainingDayDto);
}
