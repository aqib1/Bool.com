package com.mancala.service.Impl;

import com.mancala.domain.entities.PlayerEntity;
import com.mancala.domain.enums.PlayerType;
import com.mancala.repository.Impl.PlayerRepository;
import com.mancala.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Autowired
    private PlayerRepository repository;

    @Override
    public PlayerEntity create(String playerName, PlayerType playerType) {
        logger.debug("Creating player against name {}, with type {}", playerName, playerType);
        return repository.save(PlayerEntity
                .builder()
                .uuid(UUID.randomUUID())
                .name(playerName)
                .playerType(playerType)
                .build());
    }
}