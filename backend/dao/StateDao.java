package com.company.twittertrendswebapp.dao;

import com.company.twittertrendswebapp.api.dao.IStateDao;
import com.company.twittertrendswebapp.model.State;
import org.springframework.stereotype.Repository;

@Repository
public class StateDao extends AbstractDao<State> implements IStateDao{
}
