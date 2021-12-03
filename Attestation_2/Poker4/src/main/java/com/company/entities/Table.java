package com.company.entities;

import java.util.ArrayList;

public class Table {

    private ArrayList<Card> tableCards;

    public Table() {
        this.tableCards = new ArrayList<>();
    }

    public ArrayList<Card> getTableCards() {
        return tableCards;
    }

    public void setTableCards(ArrayList<Card> tableCards) {
        this.tableCards = tableCards;
    }
}
