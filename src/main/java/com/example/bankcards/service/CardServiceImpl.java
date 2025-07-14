package com.example.bankcards.service;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.CardMapper;
import com.example.bankcards.util.CardStatusEnum;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    private final CardMapper cardMapper;

    public CardServiceImpl(CardRepository cardRepository, UserRepository userRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.cardMapper = cardMapper;
    }

    public Double getBalance(String cardNumber){
        Card card = cardRepository.findByNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Карта не найдена"));
        return card.getBalance();
    }

    public void putTransfer(String sendCardNumber, String reciveCardNumber, Double summ){
        Card sendCard = cardRepository.findByNumber(sendCardNumber)
                .orElseThrow(() -> new RuntimeException("Карта не найдена"));
        Card reciveCard = cardRepository.findByNumber(reciveCardNumber)
                .orElseThrow(() -> new RuntimeException("Карта не найдена"));
        if (summ <= sendCard.getBalance()){
            sendCard.setBalance(sendCard.getBalance() - summ);
            reciveCard.setBalance(reciveCard.getBalance() + summ);
            cardRepository.save(sendCard);
            cardRepository.save(reciveCard);
        }
        else {
            throw new RuntimeException("Карта не найдена");
        }
    }

    public List<CardDto> getUserCards(UserDetails userDetails){
        User user = userRepository.findByName(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return cardMapper.toDtoList(user.getCardList());
    }

    public List<CardDto> getCards(){
        return cardMapper.toDtoList(cardRepository.findAll());
    }

    public void createCard(CardDto cardDto){
        Card card = cardMapper.toEntity(cardDto);
        cardRepository.save(card);
    }

    public void deleteCard(String cardNumber){
        cardRepository.findByNumber(cardNumber).ifPresent(cardRepository::delete);
    }

    public void changeCardStatus(String cardNumber, CardStatusEnum statusEnum){
        Card card = cardRepository.findByNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Карта не найдена"));
        card.setStatus(statusEnum.getDescription());
    }
}
