package com.company.twittertrendswebapp.api.dao;

import com.company.twittertrendswebapp.model.AEntity;

import java.util.List;

public interface IGenericDao <T extends AEntity>{
    T getById(Long id);

    List<T> getAll();

    void save(T entity);

    Long getMaxId();

    void clearData();
}
