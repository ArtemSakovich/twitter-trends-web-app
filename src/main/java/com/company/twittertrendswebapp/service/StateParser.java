package com.company.twittertrendswebapp.service;

import com.company.twittertrendswebapp.api.dao.IStateDao;
import com.company.twittertrendswebapp.api.service.IStateParser;
import com.company.twittertrendswebapp.api.service.ITweetAnalyzer;
import com.company.twittertrendswebapp.model.Polygon;
import com.company.twittertrendswebapp.model.State;
import com.company.twittertrendswebapp.model.Top;
import com.company.twittertrendswebapp.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class StateParser implements IStateParser {
    @Autowired
    private IStateDao stateDao;

    @PostConstruct
    void init() throws IOException {
        ParseJSONFile("src/main/resources/states.json");
    }

    private void ParseJSONFile(String fileName) throws IOException {
        String stateName = "";
        List<Polygon> polygons = new ArrayList<>();
        Boolean flag = false;
        String temp = "";
        Polygon polygon = new Polygon();

        String jsonString =new String( Files.readAllBytes(Paths.get(fileName)));

        jsonString = DeleteAnotherBrackets(DeleteUselessBrackets(jsonString));

        for (int i = 0; i < jsonString.length(); i++) {
            if (jsonString.charAt(i) >= 65 && jsonString.charAt(i) <= 90) {
                stateName = String.valueOf(jsonString.charAt(i)) + jsonString.charAt(i + 1);
                i++;
            }
            if ((jsonString.charAt(i) ==
                    '[' && jsonString.charAt(i + 1) ==
                    '[' && jsonString.charAt(i + 2) == '[')
                    || flag) {
                if (jsonString.charAt(i) != ']') {
                    flag = true;
                    temp += jsonString.charAt(i);
                } else {
                    temp += jsonString.charAt(i);
                    if (!temp.equals("]")) {
                        Top x = GetNumbers(temp);
                        polygon.setPoints(x);
                        temp = "";
                    }
                    if (jsonString.charAt(i + 1) == ']') {
                        polygons.add(polygon);
                        polygon = new Polygon();
                    }
                    if (jsonString.charAt(i + 2) == ']') {
                        State state = new State(stateName, polygons);
                        state.setId(IdGenerator.getInstance().generateId(stateDao.getMaxId()));
                        stateDao.save(state);
                        polygons = new ArrayList<>();
                        flag = false;
                    }
                }
            }
        }
    }

    private Top GetNumbers(String temp) {
        String point = "";

        Top t = new Top();
        for (int i = 0; i < temp.length(); i++)
        {
            if (temp.charAt(i) == '[' && temp.charAt(i+1)!='[')
            {
                while (temp.charAt(i+1) != ',')
                {

                    point += temp.charAt(i+1);


                    i++;
                }
                t.setX(Double.parseDouble(point)) ;
                point = "";
                if (temp.charAt(i+1) == ',')
                {
                    while (temp.charAt(i+2) != ']')
                    {

                        point += temp.charAt(i+2);


                        i++;
                    }

                }
                t.setY(Double.parseDouble(point));
            }
        }
        return t;
    }

    private String DeleteAnotherBrackets(String jsonString) {
        int temp = 0;
        for (int i = 0; i < jsonString.length() - temp - 5; i++)
        {
            if ((jsonString.charAt(i) == '[' && jsonString.charAt(i+1) == '[' && jsonString.charAt(i+2) == '[') &&
                    jsonString.charAt(i-2) != ':')
            {
                jsonString=jsonString.substring(0,i)+jsonString.substring(i+1);
                temp++;
            }
            if ((jsonString.charAt(i) == ']' && jsonString.charAt(i+1) == ']' && jsonString.charAt(i+2) == ']') &&
                    jsonString.charAt(i+5) != '"')
            {
                jsonString=jsonString.substring(0,i)+jsonString.substring(i+1);
                temp++;
            }

        }
        return jsonString;
    }

    private String DeleteUselessBrackets(String jsonString) {
        int temp = 0;

        for (int i = 0; i < jsonString.length() - temp; i++)
        {
            if ((jsonString.charAt(i) == '[' && jsonString.charAt(i+1) == '[' && jsonString.charAt(i+2) == '[' &&
                    jsonString.charAt(i+3) == '[')
                    || (jsonString.charAt(i) == ']' && jsonString.charAt(i+1) == ']' && jsonString.charAt(i+2) == ']'
                    && jsonString.charAt(i+3) == ']'))
            {
                jsonString=jsonString.substring(0,i)+jsonString.substring(i+1);
                temp++;
            }

        }
        return jsonString;
    }
}

