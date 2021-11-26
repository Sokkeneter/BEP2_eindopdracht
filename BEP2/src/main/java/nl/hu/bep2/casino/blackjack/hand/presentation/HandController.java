package nl.hu.bep2.casino.blackjack.hand.presentation;

import nl.hu.bep2.casino.blackjack.hand.application.HandService;
import nl.hu.bep2.casino.blackjack.hand.domain.Hand;
import nl.hu.bep2.casino.security.domain.UserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blackjack")
public class HandController {
    private final HandService handService;


    public HandController(HandService handService) {
        this.handService = handService;
    }

    @PutMapping()
    public Hand dealRandomCard(Authentication authentication, Hand hand){
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        return handService.dealRandomCard(profile.getUsername(), hand);
    }

}
