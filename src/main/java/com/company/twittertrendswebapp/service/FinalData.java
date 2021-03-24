package com.company.twittertrendswebapp.service;

import com.company.twittertrendswebapp.api.dao.IStateDao;
import com.company.twittertrendswebapp.api.dao.ITweetDao;
import com.company.twittertrendswebapp.api.service.IFinalData;
import com.company.twittertrendswebapp.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
@Component
public class FinalData implements IFinalData {
    private List<State> finalData;
    @Autowired
    private IStateDao stateDao;


    @Override
    public List<State> getFinalData() throws FileNotFoundException {
        return finalData = new ArrayList<>(stateDao.getAll());
    }
}