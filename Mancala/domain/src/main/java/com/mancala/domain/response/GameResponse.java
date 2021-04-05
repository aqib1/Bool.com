package com.mancala.domain.response;

import com.mancala.domain.base.Base;
import com.mancala.domain.enums.GameStatus;
import com.mancala.domain.enums.PlayerType;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameResponse extends Base {
    private UUID uuid;
    private GameStatus status;
    private int[] board;
    private PlayerType playerTurn;
    private int totalTurn;
    private PlayerResponse firstPlayer;
    private PlayerResponse secondPlayer;
    private String winnerPlayer;
}
