package com.mancala.repository;

public interface BaseRepository<T, U> {
    U save(U u);
    U findById(T t);
    void clear();
    int size();
}
