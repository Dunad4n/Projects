package com.company.entites;

import java.util.Random;

public class Player {

    private Deck.Card firstCard;
    private Deck.Card secondCard;
    private int bank = 1000;

    public Player() {}

    public Player(Deck.Card firstCard, Deck.Card secondCard) {
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }

    public Deck.Card getFirstCard() {
        return firstCard;
    }

    public void setFirstCard(Deck.Card firstCard) {
        this.firstCard = firstCard;
    }

    public Deck.Card getSecondCard() {
        return secondCard;
    }

    public void setSecondCard(Deck.Card secondCard) {
        this.secondCard = secondCard;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank += bank;
    }

    public void fold() {
        Random rnd = new Random();
        int n = rnd.nextInt(100);
        if (n > 89 && n % 5 == 0) {
            this.setFirstCard(null);
            this.setSecondCard(null);
        }
    }

    public void bet(Croupier croupier, int sum) {
        if (sum > bank) {
            System.out.println("Вы не можете поставить больше чем есть в вашем банке.");
            return;
        }
        croupier.setBank(sum);
        this.bank -= sum;
    }

}
