package edu.bachelor.trainer.invitation.services.imp;

import edu.bachelor.trainer.athlete.services.AthleteService;
import edu.bachelor.trainer.common.exceptions.UserNotExistException;
import edu.bachelor.trainer.invitation.controllers.dto.InvitationDto;
import edu.bachelor.trainer.invitation.services.InvitationService;
import edu.bachelor.trainer.repository.InvitationRepository;
import edu.bachelor.trainer.repository.UserRepository;
import edu.bachelor.trainer.repository.entities.Invitation;
import edu.bachelor.trainer.repository.entities.User;
import edu.bachelor.trainer.security.JwtClaims;
import edu.bachelor.trainer.trainer.services.TrainerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class InvitationServiceImp implements InvitationService {

    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final TrainerService trainerService;
    private final AthleteService athleteService;

    public InvitationServiceImp(UserRepository userRepository, InvitationRepository invitationRepository, TrainerService trainerService, AthleteService athleteService) {
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.trainerService = trainerService;
        this.athleteService = athleteService;
    }

    @Override
    public Invitation createInvitation(String receiverUsername, String jwtToken) {
        JwtClaims jwtClaims = new JwtClaims(jwtToken);

        Optional<User> findReceiver = userRepository.findByUsername(receiverUsername);
        Optional<User> findSender = userRepository.findByUsername(jwtClaims.getUserUsername());

        if(!findReceiver.isPresent() || !findSender.isPresent()){
            throw new UserNotExistException("No such user!");
        }
        Invitation invitation = new Invitation();
        invitation.setReceiverUsername(receiverUsername);
        invitation.setSenderUsername(jwtClaims.getUserUsername());

        return invitationRepository.save(invitation);
    }

    @Override
    @Transactional
    public void manageInvitation(InvitationDto invitationDto, String jwtToken) {
        JwtClaims jwtClaims = new JwtClaims(jwtToken);
        Optional<User> findAccepter = userRepository.findByUsername(jwtClaims.getUserUsername());
        Optional<User> findSender = userRepository.findByUsername(invitationDto.getSenderUsername());

        if(!findAccepter.isPresent() || !findSender.isPresent()){
            throw new UserNotExistException("No such user!");
        }

        if(invitationDto.getAccepted()){
            if(userIsTrainer(findAccepter.get())) {
                trainerService.addAthlete(findSender.get().getUsername(), jwtToken);
            }else {
                athleteService.addTrainer(findSender.get().getUsername(), jwtToken);
            }
        }
        deleteInvitation(invitationDto);
    }

    private boolean userIsTrainer(User user){
        return user.getRoles().stream().anyMatch(role -> role.getName().contains("TRAINER"));
    }

    private void deleteInvitation(InvitationDto invitationDto){
        Invitation invitation = invitationRepository.getOne(invitationDto.getId());
        invitationRepository.delete(invitation);
    }

}
