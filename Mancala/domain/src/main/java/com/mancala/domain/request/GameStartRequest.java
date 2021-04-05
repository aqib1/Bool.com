package com.mancala.domain.request;

import com.mancala.domain.base.Base;
import lombok.*;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameStartRequest extends Base {
    @NotNull(message = "First player name required")
    private String firstPlayer;
    @NotNull(message = "Second player name required")
    private String secondPlayer;
}
