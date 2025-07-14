package com.example.bankcards.service;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.util.CardStatusEnum;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CardService {
    Double getBalance(String cardNumber);
    void putTransfer(String sendCardNumber, String reciveCardNumber, Double summ);
    List<CardDto> getUserCards(UserDetails userDetails);
    List<CardDto> getCards();
    void createCard(CardDto cardDto);
    void deleteCard(String cardNumber);
    void changeCardStatus(String cardNumber, CardStatusEnum statusEnum);
}
