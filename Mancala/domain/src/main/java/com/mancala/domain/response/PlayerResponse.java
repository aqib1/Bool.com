package com.mancala.domain.response;

import com.mancala.domain.base.Base;
import com.mancala.domain.enums.PlayerType;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerResponse extends Base {
    private UUID uuid;
    private String name;
    private PlayerType playerType;
}
