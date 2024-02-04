package com.wykessam.tsgalpha.persistence.entity.card;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Samuel Wykes.
 * Represents a champion card in the TCG.
 */
@Document(collection = "card")
@TypeAlias(value = "champion")
@Getter
@SuperBuilder
public class ChampionCard extends Card {
}
