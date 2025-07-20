package com.example.bankcards.service;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.CardMapper;
import com.example.bankcards.util.CardSpecification;
import com.example.bankcards.util.CardStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.List;

@Service
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

    @Transactional
    public void putTransfer(String sendCardNumber, String reciveCardNumber, Double summ) {
        Card sendCard = cardRepository.findByNumber(sendCardNumber)
                .orElseThrow(() -> new RuntimeException("Карта не найдена"));
        Card reciveCard = cardRepository.findByNumber(reciveCardNumber)
                .orElseThrow(() -> new RuntimeException("Карта не найдена"));

        if (sendCard.getBalance() < summ) {
            throw new RuntimeException("Недостаточно средств для перевода");
        }

        sendCard.setBalance(sendCard.getBalance() - summ);
        reciveCard.setBalance(reciveCard.getBalance() + summ);
        cardRepository.save(sendCard);
        cardRepository.save(reciveCard);
    }

    public List<CardDto> getUserCards(UserDetails userDetails, int page, int size){
        User user = userRepository.findByName(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        Page<Card> cardPage = cardRepository.findByUser(user, PageRequest.of(page, size));
        List<CardDto> cardDto = cardMapper.toDtoList(cardPage.getContent());

        return cardDto;
    }

    public List<CardDto> getCards(int page, int size){
        return cardMapper.toDtoList(cardRepository.findAll(PageRequest.of(page, size)).getContent());
    }

    public List<CardDto> getCardsWithSpecs(String status, String number, Double balance, Date validTill, int page, int size){
        Specification<Card> spec = Specification.where(CardSpecification.hasStatus(status))
                .and(CardSpecification.hasNumber(number))
                .and(CardSpecification.hasBalance(balance))
                .and(CardSpecification.isValidTill(validTill));

        return cardMapper.toDtoList(cardRepository.findAll(spec, PageRequest.of(page, size)).getContent());
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
