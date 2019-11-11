package edu.bachelor.trainer.balance.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private")
public class BalanceController {

    @GetMapping
    public String test1() {
        return "jestesm w tekst1";
    }


}
