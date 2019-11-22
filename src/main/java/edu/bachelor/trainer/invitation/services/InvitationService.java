package edu.bachelor.trainer.invitation.services;

import edu.bachelor.trainer.invitation.controllers.dto.InvitationDto;
import edu.bachelor.trainer.repository.entities.Invitation;

public interface InvitationService {

    Invitation createInvitation(String receiverUsername, String jwtToken);
    void manageInvitation(InvitationDto invitationDto, String jwtToken);
}
