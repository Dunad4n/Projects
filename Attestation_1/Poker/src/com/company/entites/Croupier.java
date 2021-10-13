package com.company.entites;

import java.util.ArrayList;
import java.util.Random;

public class Croupier {

    private Deck deck;
    private int bank;
    private final int COUNT_OF_SHUFFLE = 200;

    public Croupier() {
        this.deck = new Deck();
        this.bank = 0;
    }

    public Deck getCroupierDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank += bank;
    }

    public ArrayList<Player> distributeCardsToPlayers(ArrayList<Player> players){
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setFirstCard(deck.getCard());
            players.get(i).setSecondCard(deck.getCard());
        }
        return players;
    }

    public void shuffle() {
        this.deck = new Deck();
        Random rnd = new Random();
        ArrayList<Deck.Card> cards = new ArrayList<>(this.getCroupierDeck().getDeck());
        this.getCroupierDeck().getDeck().clear();
        int n;
        for (int i = 0; i < COUNT_OF_SHUFFLE; i++) {
            n = rnd.nextInt(51);
            Deck.Card card = cards.get(cards.size() - 1);
            cards.set(cards.size() - 1, cards.get(n));
            cards.set(n, card);
        }
        this.getCroupierDeck().getDeck().addAll(cards);
    }

    public void distributeCardsToTable(Table table, boolean startOfGame) {
        this.getCroupierDeck().getDeck().remove();
        if (startOfGame) {
            for (int i = 0; i < 3; i++) {
                table.getTableCards().add(this.getCroupierDeck().getCard());
            }
        } else {
            table.getTableCards().add(this.getCroupierDeck().getCard());
        }
    }
}
