package edu.bachelor.trainer.balance.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController


@RequestMapping("/private")
public class BalanceController {

    @GetMapping("/admin")
    public String test1() {
        return "jestesm w admin";
    }

    @GetMapping("/user")
    public String test2() {
        return "jestesm w user";
    }

}
