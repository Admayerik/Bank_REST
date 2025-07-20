package com.example.bankcards.controller;


import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.UserDto;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.CardStatusEnum;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final CardService cardService;
    private final UserService userService;

    public AdminController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping("/cards")
    private List<CardDto> getCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return cardService.getCards(page, size);
    }

    @GetMapping("/search")
    public List<CardDto> getCardsWithSpecs(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) Double balance,
            @RequestParam(required = false) Date validTill,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return cardService.getCardsWithSpecs(status, number, balance, validTill, page, size);
    }

    @PostMapping("/card/create")
    private void createCard(CardDto cardDto){
        cardService.createCard(cardDto);
    }

    @DeleteMapping("/card/delete")
    private void deleteCard(
            @RequestParam String cardNumber
    ){
        cardService.deleteCard(cardNumber);
    }

    @PutMapping("/card/changeStatus")
    private void changeCardStatus(String cardNumber, CardStatusEnum statusEnum){
        cardService.changeCardStatus(cardNumber, statusEnum);
    }

    @PostMapping("/user/create")
    private void createUser(UserDto userDto){
        userService.createUser(userDto);
    }

    @DeleteMapping("/user/delete")
    private void userDelete(Long id){
        userService.userDelete(id);
    }
}
