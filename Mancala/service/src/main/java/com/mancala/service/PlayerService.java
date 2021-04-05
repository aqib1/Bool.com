package com.mancala.service;

import com.mancala.domain.entities.PlayerEntity;
import com.mancala.domain.enums.PlayerType;

public interface PlayerService {
    PlayerEntity create(String playerName, PlayerType playerType);
}
