package com.company.entites;

import java.util.ArrayList;

public class Table {

    private ArrayList<Deck.Card> tableCards;

    public Table() {
        this.tableCards = new ArrayList<>();
    }

    public ArrayList<Deck.Card> getTableCards() {
        return tableCards;
    }

    public void setTableCards(ArrayList<Deck.Card> tableCards) {
        this.tableCards = tableCards;
    }
}
