package com.company.services;

import com.company.entities.Croupier;
import com.company.entities.Player;
import com.company.entities.Table;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.ArrayList;

public class Saver {

    public void serialize(ArrayList<Player> players, Table table, Croupier croupier) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Cell cell = new Cell();
        cell.setPlayers(players);
        cell.setTable(table);
        cell.setCroupier(croupier);
        File file = new File("src\\main\\resources\\save\\save.json");
        mapper.writeValue(file, cell);
    }

    public Cell deserialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File("src\\main\\resources\\save\\save.json"), Cell.class);
    }

}
