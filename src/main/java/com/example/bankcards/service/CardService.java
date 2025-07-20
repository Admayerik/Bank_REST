package com.example.bankcards.service;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.util.CardStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface CardService {
    Double getBalance(String cardNumber);
    void putTransfer(String sendCardNumber, String reciveCardNumber, Double summ);
    List<CardDto> getUserCards(UserDetails userDetails, int page, int size);
    List<CardDto> getCards(int page, int size);
    List<CardDto> getCardsWithSpecs(String status, String number, Double balance, Date validTill, int page, int size);
    void createCard(CardDto cardDto);
    void deleteCard(String cardNumber);
    void changeCardStatus(String cardNumber, CardStatusEnum statusEnum);
}
