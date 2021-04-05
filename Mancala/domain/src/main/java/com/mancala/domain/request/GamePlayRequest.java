package com.mancala.domain.request;

import com.mancala.domain.base.Base;
import lombok.*;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GamePlayRequest extends Base {
    @NotNull(message = "Game id required")
    private String uuid;
}
