package edu.bachelor.trainer.invitation.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvitationDto {

    private Long id;

    private String senderUsername;

    private Boolean accepted;
}
