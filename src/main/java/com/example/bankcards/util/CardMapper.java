package com.example.bankcards.util;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardDto toCardDto(Card card);
    List<CardDto> toDtoList(List<Card> cards);

    Card toEntity(CardDto card);
}