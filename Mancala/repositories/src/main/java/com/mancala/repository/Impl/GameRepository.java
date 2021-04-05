package com.mancala.repository.Impl;

import com.mancala.domain.entities.GameEntity;
import com.mancala.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.StampedLock;

/**
 * * ===== Database information ==============
 * <p>
 * This GameRepository class is used to provide account details and IO operation
 * against data. For the simplicity of task as it mentioned i
 * am not using any database instead of that i am using map, in future we can
 * replace that structure with actual database
 * </p>
 * ========= Thread safety information =======
 * <p>
 * To make it thread-safe we will use synchronization as we are using Map, and
 * that will be updated, with requests. we have to make our data synchronize for
 * the sake of multiple requests (threads) safety, in the case of database we
 * have isolation to prevent our database
 * </p>
 * <p>
 * Synchronization can be acquired on method level (Coarse grain locking
 * mechanism) but this will make our method slow as no thread else can enter to
 * other methods (in database term we can say highest level of isolation) we
 * will use fine grain locking mechanism separately for read mechanism and
 * writing mechanism
 * </p>
 * <p>
 * I am using java 1.8 StampedLock @see {@link StampedLock} <br>
 * <strong> Reason of using StampedLock is one of its feature optimistic locking
 * in this lock as per documentation said, we do not need to apply full-fledged
 * read lock every time, some time lock is not held by any write operation, we
 * use tryOptimisticRead to check if the lock is hold by write operation and
 * then check result with validate method. </strong> <br>
 * Java 1.8 StampedLock is much more efficient and fast as compared to
 * ReentrantLock specially optimistic locking which make synchronization
 * overhead very slow. You can also use ReentrantLock but it very slow as
 * compared to new java 1.8 stamped lock
 * </p>
 *
 * @author AQIB JAVED
 * @version 1.0
 * @since 02/03/2021
 */
@Repository
public class GameRepository implements BaseRepository<UUID, GameEntity> {
    private static final Logger logger = LoggerFactory.getLogger(GameRepository.class);
    private final Map<UUID, GameEntity> gameMap = new HashMap<>();
    private final StampedLock stampedLock = new StampedLock();

    @Override
    public GameEntity save(GameEntity gameEntity) {
        logger.info("Acquiring write lock for save");
        long stamp = stampedLock.writeLock();
        try {
            gameMap.put(gameEntity.getUuid(), gameEntity);
            logger.debug("GameEntity stored successfully");
            return gameEntity;
        } finally {
            stampedLock.unlockWrite(stamp);
            logger.info("Released write lock for save");
        }
    }

    @Override
    public GameEntity findById(UUID uuid) {
        logger.info("Acquiring optimistic read lock for findById");
        long stamp = stampedLock.tryOptimisticRead();
        // Synchronization overhead is very low if validate() succeeds
        // Always return true if stamp is non zero (as not acquired by write lock)
        if (stampedLock.validate(stamp))
            return findGameById(uuid);
        // Only in the case when write lock is acquired we need to apply read lock
        stamp = stampedLock.readLock();
        try {
            return findGameById(uuid);
        } finally {
            stampedLock.unlockRead(stamp);
            logger.info("Releasing read lock for findById");
        }
    }

    @Override
    public void clear() {
        logger.info("Acquiring write lock for clear games");
        long stamp = stampedLock.writeLock();
        try {
            if (!gameMap.isEmpty()) {
                gameMap.clear();
            }
        } finally {
            stampedLock.unlockWrite(stamp);
            logger.info("Releasing write lock for clear games");
        }
    }

    @Override
    public int size() {
        logger.info("Acquiring optimistic read lock for size");
        long stamp = stampedLock.tryOptimisticRead();
        // Synchronization overhead is very low if validate() succeeds
        // Always return true if stamp is non zero (as not acquired by write lock)
        if (stampedLock.validate(stamp))
            return gameMap.size();
        // Only in the case when write lock is acquired we need to apply read lock
        stamp = stampedLock.readLock();
        try {
            return gameMap.size();
        } finally {
            stampedLock.unlockRead(stamp);
            logger.info("Releasing read lock for size");
        }
    }

    private GameEntity findGameById(UUID uuid) {
        return gameMap.get(uuid);
    }

}
