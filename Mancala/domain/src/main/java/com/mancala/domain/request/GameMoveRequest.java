package com.mancala.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mancala.domain.base.Base;
import com.mancala.domain.enums.PlayerType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameMoveRequest extends Base {
    @NotNull(message = "Game id required")
    @JsonProperty(value = "uuid")
    private String uuid;
    @JsonProperty(value = "player_type")
    @NotNull(message = "Player type required")
    private PlayerType playerTurn;
    private int index;
    private int currentStones;
    private boolean isPlayerBoard = Boolean.TRUE;
    private boolean isFinalOnLargePit;
}
