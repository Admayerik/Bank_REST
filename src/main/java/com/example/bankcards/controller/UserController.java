package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.service.CardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final CardService cardService;

    public UserController(CardService cardService) {
        this.cardService = cardService;
    }


    @GetMapping("/balance")
    private Double getBalance(
            @RequestParam String cardNumber
    ){
        return cardService.getBalance(cardNumber);
    }

    @PutMapping("/transfer")
    private void putTransfer(
            @RequestParam String sendCardNumber,
            @RequestParam String reciveCardNumber,
            @RequestParam Double summ
    ){
        cardService.putTransfer(sendCardNumber, reciveCardNumber, summ);
    }

    @GetMapping("/cards")
    private List<CardDto> getUserCards(@AuthenticationPrincipal UserDetails userDetails){
        return cardService.getUserCards(userDetails);
    }
}
