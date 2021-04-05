package com.mancala.domain.entities;

import com.mancala.domain.base.Base;
import com.mancala.domain.enums.GameStatus;
import com.mancala.domain.enums.PlayerType;
import lombok.*;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameEntity extends Base {
    private UUID uuid;
    private GameStatus status;
    private int[] board;
    private int totalTurn;
    private PlayerType playerTurn;
    private PlayerEntity firstPlayer;
    private PlayerEntity secondPlayer;
    private String winnerPlayer;
}
