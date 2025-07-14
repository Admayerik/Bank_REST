package com.example.bankcards.util;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CardMapper {
    List<CardDto> toDtoList(List<Card> cards);

    Card toEntity(CardDto card);
}
