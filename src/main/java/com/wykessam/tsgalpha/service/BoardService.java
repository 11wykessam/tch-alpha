package com.wykessam.tsgalpha.service;

import com.wykessam.tsgalpha.dto.board.BoardDTO;
import com.wykessam.tsgalpha.dto.board.BoardDTO.BoardDTOBuilder;
import com.wykessam.tsgalpha.dto.card.CardDTO;
import com.wykessam.tsgalpha.exception.BoardNotFoundException;
import com.wykessam.tsgalpha.persistence.entity.board.Board;
import com.wykessam.tsgalpha.persistence.repository.BoardDBRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Samuel Wykes.
 * Service responsible for handling logic involving {@link Board} objects.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardDBRepository boardDBRepository;
    private final CardService cardService;

    /**
     * Get a player by its unique identifier.
     *
     * @param id {@link UUID}.
     * @return {@link Board}.
     */
    public Mono<Board> getById(final UUID id) {
        return this.boardDBRepository.findById(id)
                .switchIfEmpty(Mono.error(new BoardNotFoundException(id)));
    }

    /**
     * Convert board object to its DTO representation.
     *
     * @param board {@link Board}.
     * @return {@link BoardDTO}.
     */
    public Mono<BoardDTO> toDTO(final Board board) {
        return Mono.just(BoardDTO.builder())
                .flatMap(builder -> this.enrichWithId(builder, board))
                .flatMap(builder -> this.enrichWithCardSet(builder::neutralArea, board))
                .flatMap(builder -> this.enrichWithCardSet(builder::homeArea, board))
                .map(BoardDTOBuilder::build);

    }

    private Mono<BoardDTOBuilder> enrichWithId(final BoardDTOBuilder builder, final Board board) {
        return Mono.just(builder.id(board.getId()));
    }

    private Mono<BoardDTOBuilder> enrichWithCardSet(final Function<Set<CardDTO>, BoardDTOBuilder> enrichmentFunction,
                                                    final Board board) {
        return Flux.fromIterable(board.getNeutralAreaCardIds())
                .flatMap(this.cardService::getById)
                .flatMap(this.cardService::toDTO)
                .collect(Collectors.toSet())
                .map(enrichmentFunction);
    }

}
