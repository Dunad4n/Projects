package com.company.entities;

import com.company.Kind;
import com.company.Value;

import java.util.LinkedList;
import java.util.Queue;

public class Deck {

    public Queue<Card> deck = new LinkedList<>();

    public Deck() {
        for(Value v : Value.values()) {
            for(Kind k : Kind.values()) {
                deck.add(new Card(v, k));
            }
        }
    }

    public Queue<Card> getDeck() {
        return deck;
    }

    public void setDeck(Queue<Card> deck) {
        this.deck = deck;
    }

    public Card pollCard() {
        return deck.poll();
    }

}
