package com.company.twittertrendswebapp.api.service;

import com.company.twittertrendswebapp.model.State;

import java.io.FileNotFoundException;
import java.util.List;

public interface IFinalData {
    List<State> getFinalData(String tweetsFileName) throws FileNotFoundException;
}
