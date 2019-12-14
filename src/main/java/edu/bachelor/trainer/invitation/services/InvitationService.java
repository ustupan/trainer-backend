package edu.bachelor.trainer.invitation.services;

import edu.bachelor.trainer.invitation.controllers.dto.InvitationDto;
import edu.bachelor.trainer.repository.entities.Invitation;

import java.util.Optional;
import java.util.Set;

public interface InvitationService {

    Invitation createInvitation(String receiverUsername, String jwtToken);
    void manageInvitation(InvitationDto invitationDto, String jwtToken);
    Set<InvitationDto> getMyInvitations(String jwtToken);
}
