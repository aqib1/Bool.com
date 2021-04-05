package com.mancala.domain.response;

import com.mancala.domain.base.Base;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameStartResponse extends Base {
    private UUID uuid;
}
