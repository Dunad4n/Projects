package com.company.entities;

import java.util.Random;

public class Player {

    private Card firstCard;
    private Card secondCard;
    private boolean visibleCards = false;
    private int bank = 100;

    public Player() {}

    public Player(Card firstCard, Card secondCard) {
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }

    public Card getFirstCard() {
        return firstCard;
    }

    public void setFirstCard(Card firstCard) {
        this.firstCard = firstCard;
    }

    public Card getSecondCard() {
        return secondCard;
    }

    public void setSecondCard(Card secondCard) {
        this.secondCard = secondCard;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public boolean isVisibleCards() {
        return visibleCards;
    }

    public void setVisibleCards(boolean visibleCards) {
        this.visibleCards = visibleCards;
    }

    public void bet(Croupier croupier, int sumBet) {
        if (sumBet > croupier.getMaxBet()) {
            croupier.setBank(sumBet);
            this.bank -= sumBet;
        } else {
            if (this.bank > croupier.getMaxBet()) {
                croupier.setBank(croupier.getMaxBet());
                this.bank -= croupier.getMaxBet();
            } else {
                croupier.setBank(this.bank);
                this.bank = 0;
            }
        }
    }

    public void check(Croupier croupier) {
        croupier.setBank(0);
    }

    public void fold() {
        this.firstCard = null;
        this.secondCard = null;
    }

    public void addBank(int sum) {
        this.bank += sum;
    }

}
