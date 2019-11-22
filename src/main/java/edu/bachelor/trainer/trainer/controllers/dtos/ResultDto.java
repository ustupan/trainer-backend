package edu.bachelor.trainer.trainer.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ResultDto {

    private Long id;

    private String discipline;

    private String description;

    private Date resultDate; //jak cos dac w stringu

    private Long value;
}
