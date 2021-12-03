package com.company.services;

import com.company.entities.Croupier;
import com.company.entities.Player;
import com.company.entities.Table;

import java.util.ArrayList;

public class Cell {

    private ArrayList<Player> players;
    private Table table;
    private Croupier croupier;

    public Cell() {}

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Croupier getCroupier() {
        return croupier;
    }

    public void setCroupier(Croupier croupier) {
        this.croupier = croupier;
    }
}
