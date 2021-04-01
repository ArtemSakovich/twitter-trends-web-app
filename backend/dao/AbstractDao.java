package com.company.twittertrendswebapp.dao;

import com.company.twittertrendswebapp.api.dao.IGenericDao;
import com.company.twittertrendswebapp.model.AEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AbstractDao<T extends AEntity> implements IGenericDao<T> {
    private List<T> repository = new ArrayList<>();

    @Override
    public T getById(Long id) {
        for (T entity : repository) {
            if (id.equals(entity.getId())) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        return repository;
    }

    @Override
    public void save(T entity) {
        repository.add(entity);
    }

    @Override
    public Long getMaxId() {
        if (!repository.isEmpty()) {
            return repository.stream().max(Comparator.comparing(AEntity::getId)).get().getId();
        } else {
            return 0L;
        }
    }

    @Override
    public void clearData() {
        repository.clear();
    }
}
