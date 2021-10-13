package com.company.entites;

import com.company.Kind;
import com.company.Value;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Deck {

    Queue<Card> deck = new LinkedList<>();

    public Deck() {
        for(Value v : Value.values()) {
            for(Kind k : Kind.values()) {
                deck.add(new Card(v, k));
            }
        }
    }

    public static class Card {
        private Value value;
        private Kind kind;

        public Card() {}

        public Card(Value value, Kind kind) {
            this.value = value;
            this.kind = kind;
        }

        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public Kind getKind() {
            return kind;
        }

        public void setKind(Kind kind) {
            this.kind = kind;
        }
    }

    public Queue<Card> getDeck() {
        return deck;
    }

    public void setDeck(Queue<Card> deck) {
        this.deck = deck;
    }

    public Card getCard() {
        return deck.poll();
    }
}
