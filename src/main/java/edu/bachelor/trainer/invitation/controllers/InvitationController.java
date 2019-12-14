package edu.bachelor.trainer.invitation.controllers;

import edu.bachelor.trainer.configuration.SecurityConstants;
import edu.bachelor.trainer.invitation.controllers.dto.InvitationDto;
import edu.bachelor.trainer.invitation.services.InvitationService;
import edu.bachelor.trainer.repository.entities.Invitation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/invitation")
public class InvitationController {
    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping(value = "/getInvitations")
    public ResponseEntity getInvitations( @RequestHeader(SecurityConstants.TOKEN_HEADER) String JwtToken) {
        return ResponseEntity.ok().body(invitationService.getMyInvitations(JwtToken));
    }

    @PostMapping(value = "/addInvitation")
    public ResponseEntity addInvitation(@Valid @RequestBody String receiverUsername, BindingResult bindingResult, @RequestHeader(SecurityConstants.TOKEN_HEADER) String JwtToken) {

        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Invitation invitation = invitationService.createInvitation(receiverUsername, JwtToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/manageInvitation")
    public ResponseEntity manageInvitation(@Valid @RequestBody InvitationDto invitationDto, BindingResult bindingResult, @RequestHeader(SecurityConstants.TOKEN_HEADER) String JwtToken) {
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        invitationService.manageInvitation(invitationDto, JwtToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
