package com.mancala.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mancala.domain.base.Base;
import com.mancala.domain.enums.PlayerType;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerRequest extends Base {
    @NotNull(message = "Player uuid required")
    private UUID uuid;
    @NotNull(message = "Player name required")
    private String name;
    @NotNull(message = "Player type required")
    @JsonProperty(value = "player_type")
    private PlayerType playerType;
}
