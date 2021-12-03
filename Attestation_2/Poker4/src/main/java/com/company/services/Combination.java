package com.company.services;

import com.company.Value;
import com.company.entities.Card;
import com.company.entities.Croupier;
import com.company.entities.Player;
import com.company.entities.Table;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Comparator;


public class Combination {

    public Combination() {
    }

    public double findWinner(ArrayList<Player> players, Table table) {
        ArrayList<Double> forcesAllPlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            forcesAllPlayers.add(forceOfCombination(players.get(i), table));
        }
        double maxForce = forcesAllPlayers.get(0);
        for (int i = 1; i < forcesAllPlayers.size(); i++) {
            if (maxForce < forcesAllPlayers.get(i)) {
                maxForce = forcesAllPlayers.get(i);
            }
        }
        return forcesAllPlayers.indexOf(maxForce);
    }

    public double forceOfCombination(Player player, Table table) {
        if (player.getFirstCard() == null) {
            return 0;
        }
        Comparator<Card> c = (o1, o2) -> o2.getValue().ordinal() - o1.getValue().ordinal();
        if (straightFlushOrRoyalFlush(player, table) != 0) {
            return straightFlushOrRoyalFlush(player, table) + fourOfAKind(player, table, c, true);
        }
        if (fourOfAKind(player, table, c, false) != 0) {
            return fourOfAKind(player, table, c, false) + fullHouse(player, table, c, true);
        }
        if (fullHouse(player, table, c, false) != 0) {
            return fullHouse(player, table, c, false) + flush(player, table, c, true);
        }
        if (flush(player, table, c, false) != 0) {
            return flush(player, table, c, false) + straight(player, table, c, true);
        }
        if (straight(player, table, c, false) != 0) {
            return straight(player, table, c, false) + threeOfAKind(player, table, c, true);
        }
        if (threeOfAKind(player, table, c, false) != 0) {
            return threeOfAKind(player, table, c, false) + twoPairs(player, table, c, true);
        }
        if (twoPairs(player, table, c, false) != 0) {
            return twoPairs(player, table, c, false) + onePair(player, table, c, true);
        }
        if (onePair(player, table, c, false) != 0) {
            return onePair(player, table, c, false);
        }
        return highHand(player, table);
    }

    private double highHand(Player player, Table table) {
        Card higherCard = player.getFirstCard();
        if (higherCard.getValue().ordinal() < player.getSecondCard().getValue().ordinal()) {
            higherCard = player.getSecondCard();
        }
        for (int i = 0; i < table.getTableCards().size(); i++) {
            if (table.getTableCards().get(i).getValue().ordinal() > higherCard.getValue().ordinal()) {
                higherCard = table.getTableCards().get(i);
            }
        }
        return higherCard.getValue().ordinal() + 2;
    }

    private double onePair(Player player, Table table, Comparator<Card> c, boolean check) {
        if (check == true) {
            return (Value.ACE.ordinal() + 2) * (Value.ACE.ordinal() + 2);
        }
        ArrayList<Card> pairs = findCards(player, table, 2);
        if (pairs.size() > 0) {
            pairs.sort(c);
            return (pairs.get(0).getValue().ordinal() + 2) * (pairs.get(0).getValue().ordinal() + 2) + highHand(player, table);
        }
        return 0;
    }

    private double twoPairs(Player player, Table table, Comparator<Card> c, boolean check) {
        if (check == true) {
            return Math.pow(Value.ACE.ordinal() + 2, 2) + Math.pow(Value.KING.ordinal() + 2, 2) + onePair(player, table, c, check);
        }
        ArrayList<Card> pairs = findCards(player, table, 2);
        if (pairs.size() > 1) {
            pairs.sort(c);
            return Math.pow(pairs.get(0).getValue().ordinal() + 2, 2) +
                    Math.pow(pairs.get(1).getValue().ordinal() + 2, 2) + highHand(player, table);
        }
        return 0;
    }

    private double threeOfAKind(Player player, Table table, Comparator<Card> c, boolean check) {
        if (check == true) {
            return Math.pow(Value.ACE.ordinal() + 2, 3) + twoPairs(player, table, c, check);
        }
        ArrayList<Card> three = findCards(player, table, 3);
        if (three.size() > 0) {
            three.sort(c);
            return Math.pow(three.get(0).getValue().ordinal() + 2, 3) + highHand(player, table);
        }
        return 0;
    }

    private double straight(Player player, Table table, Comparator<Card> c, boolean check) {
        if (check == true) {
            return 10 * 11 * 12 * 13 * 14 + threeOfAKind(player, table, c, check);
        }
        ArrayList<Card> straight = findStraight(player, table);
        if (straight.size() > 0) {
            int higherCard = straight.get(straight.size() - 1).getValue().ordinal() + 2;
            return higherCard * (higherCard - 1) * (higherCard - 2) * (higherCard - 3) * (higherCard - 4);
        }
        return 0;
    }

    private double flush(Player player, Table table, Comparator<Card> c, boolean check) {
        if (check == true) {
            return 10 * 11 * 12 * 13 * 14 + straight(player, table, c, check);
        }
        ArrayList<Card> flush = findFlush(player, table);
        if (flush.size() != 0) {
            int higherCard = flush.get(flush.size() - 1).getValue().ordinal() + 2;
            return higherCard * (higherCard - 1) * (higherCard - 2) * (higherCard - 3) * (higherCard - 4);
        }
        return 0;
    }

    private double fullHouse(Player player, Table table, Comparator<Card> c, boolean check) {
        if (check == true) {
            return 14 * 14 * 14 * 13 * 13 + flush(player, table, c, check);
        }
        ArrayList<Card> pairs = findCards(player, table, 2);
        ArrayList<Card> three = findCards(player, table, 3);
        for (int i = 0; i < pairs.size(); i++) {
            for (int j = 0; j < three.size(); j++) {
                if (pairs.get(i).getValue().equals(three.get(j).getValue())) {
                    pairs.remove(i);
                    i--;
                    break;
                }
            }
        }
        if (pairs.size() > 0 && three.size() > 0) {
            pairs.sort(c);
            three.sort(c);
            return Math.pow(pairs.get(0).getValue().ordinal() + 2, 2) * Math.pow(three.get(0).getValue().ordinal() + 2, 3);
        }
        return 0;
    }

    private double fourOfAKind(Player player, Table table, Comparator<Card> c, boolean check) {
        if (check == true) {
            return 14 * 14 * 14 * 14 + fullHouse(player, table, c, check);
        }
        ArrayList<Card> four = findCards(player, table, 4);
        if (four.size() > 0) {
            return Math.pow(four.get(0).getValue().ordinal() + 2, 4) + highHand(player, table);
        }
        return 0;
    }

    private double straightFlushOrRoyalFlush(Player player, Table table) {
        ArrayList<Card> allCards = new ArrayList<>(table.getTableCards());
        allCards.add(player.getFirstCard());
        allCards.add(player.getSecondCard());
        allCards.sort(Comparator.comparingInt(o -> o.getValue().ordinal()));
        ArrayList<Card> flushStraight = new ArrayList<>();
        int n = 1;
        for (int i = 1; i < allCards.size(); i++) {
            if (allCards.get(i).getKind().ordinal() == allCards.get(i - 1).getKind().ordinal()) {
                n++;
            } else {
                n = 1;
            }
            if (n >= 5) {
                flushStraight.add(allCards.get(i));
            }
        }
        if (flushStraight.size() != 0) {
            int higherCard = flushStraight.get(flushStraight.size() - 1).getValue().ordinal() + 2;
            return higherCard * (higherCard - 1) * (higherCard - 2) * (higherCard - 3) * (higherCard - 4);
        }
        return 0;
    }

    private ArrayList<Card> findFlush(Player player, Table table) {
        ArrayList<Card> flush = new ArrayList<>();
        ArrayList<Card> allCards = new ArrayList<>(table.getTableCards());
        allCards.add(player.getSecondCard());
        allCards.add(player.getFirstCard());
        allCards.sort((o1, o2) -> o2.getKind().ordinal() - o1.getKind().ordinal());
        int n = 1;
        for (int i = 1; i < allCards.size(); i++) {
            if (allCards.get(i).getKind().ordinal() == allCards.get(i - 1).getKind().ordinal()) {
                n++;
            } else {
                n = 1;
            }
            if (n >= 5) {
                flush.add(allCards.get(i));
            }
        }
        return flush;
    }

    private ArrayList<Card> findStraight(Player player, Table table) {
        ArrayList<Card> straights = new ArrayList<>();
        ArrayList<Card> allCards = new ArrayList<>(table.getTableCards());
        allCards.add(player.getFirstCard());
        allCards.add(player.getSecondCard());
        allCards.sort(Comparator.comparingInt(o -> o.getValue().ordinal()));
        for (int i = 1; i < allCards.size(); i++) {
            if (allCards.get(i).getValue().equals(allCards.get(i - 1).getValue())) {
                allCards.remove(i);
                i--;
            }
        }
        int n = 1;
        for (int i = 1; i < allCards.size(); i++) {
            if (allCards.get(i).getValue().ordinal() - 1 == allCards.get(i - 1).getValue().ordinal()) {
                n++;
            } else {
                n = 1;
            }
            if (n >= 5) {
                straights.add(allCards.get(i));
            }
        }
        return straights;
    }

    private ArrayList<Card> findCards(Player player, Table table, int end) {
        ArrayList<Card> pairs = new ArrayList<>();
        ArrayList<Card> cards = new ArrayList<>(table.getTableCards());
        cards.add(player.getFirstCard());
        cards.add(player.getSecondCard());
        int start;
        for (int i = 0; i < cards.size(); i++) {
            start = 1;
            for (int j = i + 1; j < cards.size(); j++) {
                if (cards.get(i).getValue().equals(cards.get(j).getValue())) {
                    start++;
                }
                if (start == end) {
                    pairs.add(cards.get(i));
                    break;
                }
            }
        }
        return pairs;
    }
}
