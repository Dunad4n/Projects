package com.company.entities;

import java.util.ArrayList;
import java.util.Random;

public class Croupier {

    private Deck deck;
    private int bank = 0;
    private int maxBet = 0;
    private final int COUNT_OF_SHUFFLE = 1000;

    public Croupier() {
        this.deck = new Deck();
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bet) {
        this.bank += bet;
        if (bet > this.maxBet) {
            setMaxBet(bet);
        }
    }

    public int getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(int bet) {
        this.maxBet = bet;
    }

    public ArrayList<Player> distributeCardsToPlayers(ArrayList<Player> players){
        this.shuffle();
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setFirstCard(deck.pollCard());
            players.get(i).setSecondCard(deck.pollCard());
        }
        return players;
    }

    public void shuffle() {
        this.deck = new Deck();
        Random rnd = new Random();
        ArrayList<Card> cards = new ArrayList<>(this.getDeck().getDeck());
        this.getDeck().getDeck().clear();
        int n;
        for (int i = 0; i < COUNT_OF_SHUFFLE; i++) {
            n = rnd.nextInt(51);
            Card card = cards.get(cards.size() - 1);
            cards.set(cards.size() - 1, cards.get(n));
            cards.set(n, card);
        }
        this.getDeck().getDeck().addAll(cards);
    }

    public void distributeCardsToTable(Table table, boolean startOfGame) {
        this.getDeck().getDeck().remove();
        if (startOfGame) {
            for (int i = 0; i < 3; i++) {
                table.getTableCards().add(this.getDeck().pollCard());
            }
        } else {
            table.getTableCards().add(this.getDeck().pollCard());
        }
    }
}
