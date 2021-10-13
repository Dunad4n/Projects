package com.company.services;

import com.company.Value;
import com.company.entites.Deck;
import com.company.entites.Player;
import com.company.entites.Table;

import java.util.ArrayList;
import java.util.Comparator;


public class Combination {

    public static double forceOfCombination(Player player, Table table) {
        Comparator<Deck.Card> c = new Comparator<Deck.Card>() {
            @Override
            public int compare(Deck.Card o1, Deck.Card o2) {
                return o2.getValue().ordinal() - o1.getValue().ordinal();
            }
        };
        if (royalFlushOrStraightFlush(player, table, c) != 0) {
            return royalFlushOrStraightFlush(player, table, c) + fourOfAKind(player, table, c, true);
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
        return highHand(player, table, false);
    }

    private static double highHand(Player player, Table table, boolean check) {
        Deck.Card higherCard = player.getFirstCard();
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

    private static double onePair(Player player, Table table, Comparator<Deck.Card> c, boolean check) {
        if (check == true) {
            return (Value.ACE.ordinal() + 2) * (Value.ACE.ordinal() + 2);
        }
        ArrayList<Deck.Card> pairs = findCards(player, table, 2);
        if (pairs.size() > 0) {
            pairs.sort(c);
            return (pairs.get(0).getValue().ordinal() + 2) * (pairs.get(0).getValue().ordinal() + 2) + highHand(player, table, check);
        }
        return 0;
    }

    private static double twoPairs(Player player, Table table, Comparator<Deck.Card> c, boolean check) {
        if (check == true) {
            return Math.pow(Value.ACE.ordinal() + 2, 2) + Math.pow(Value.KING.ordinal() + 2, 2) + onePair(player, table, c, check);
        }
        ArrayList<Deck.Card> pairs = findCards(player, table, 2);
        if (pairs.size() > 1) {
            pairs.sort(c);
            return Math.pow(pairs.get(0).getValue().ordinal() + 2, 2) +
                    Math.pow(pairs.get(1).getValue().ordinal() + 2, 2) + highHand(player, table, check);
        }
        return 0;
    }

    private static double threeOfAKind(Player player, Table table, Comparator<Deck.Card> c, boolean check) {
        if (check == true) {
            return Math.pow(Value.ACE.ordinal() + 2, 3) + twoPairs(player, table,c , check);
        }
        ArrayList<Deck.Card> three = findCards(player, table, 3);
        if (three.size() > 0) {
            three.sort(c);
            return Math.pow(three.get(0).getValue().ordinal() + 2, 3) + highHand(player, table, check);
        }
        return 0;
    }

    private static double straight(Player player, Table table, Comparator<Deck.Card> c, boolean check) {
        if (check == true) {
            return 10 * 11 * 12 * 13 * 14 + threeOfAKind(player, table, c, check);
        }
        ArrayList<Deck.Card> straight = findStraight(player, table);
        if (straight.size() > 0) {
            int lowerCard = straight.get(straight.size() - 1).getValue().ordinal() + 2;
            return lowerCard * (lowerCard + 1) * (lowerCard + 2) * (lowerCard + 3) * (lowerCard + 4);
        }
        return 0;
    }

    private static double flush(Player player, Table table, Comparator<Deck.Card> c, boolean check) {
        if (check == true) {
            return 10 * 11 * 12 * 13 * 14 + straight(player, table, c, check);
        }
        ArrayList<Deck.Card> flush = findFlush(player, table);
        if (flush.size() > 0) {
            if (!flush.get(flush.size() - 1).getKind().equals(flush.get(0).getKind())) {
                flush.remove(flush.size() - 1);
                flush.remove(flush.size() - 2);
            }
            flush.sort(c);
            double result = 1;
            for (int i = 0; i < 5; i++) {
                result *= (flush.get(i).getValue().ordinal() + 2);
            }
            return result;
        }
        return 0;
    }

    private static double fullHouse(Player player, Table table, Comparator<Deck.Card> c, boolean check) {
        if (check == true) {
            return 14 * 14 * 14 * 13 * 13 + flush(player, table, c, check);
        }
        ArrayList<Deck.Card> pairs = findCards(player, table, 2);
        ArrayList<Deck.Card> three = findCards(player, table, 3);
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

    private static double fourOfAKind(Player player, Table table, Comparator<Deck.Card> c, boolean check) {
        if (check == true) {
            return 14 * 14 * 14 * 14 + fullHouse(player, table, c, check);
        }
        ArrayList<Deck.Card> four = findCards(player, table, 4);
        if (four.size() > 0) {
            return Math.pow(four.get(0).getValue().ordinal() + 2, 4) + highHand(player, table, check);
        }
        return 0;
    }

    private static double royalFlushOrStraightFlush(Player player, Table table, Comparator<Deck.Card> c) {
        ArrayList<Deck.Card> flush = findFlush(player, table);
        ArrayList<Deck.Card> finalFlush = new ArrayList<>();
        flush.sort(c);
        int n = 1;
        for (int i = 1; i < flush.size(); i++) {
            if (flush.get(i).getValue().ordinal() + 1 == flush.get(i - 1).getValue().ordinal()) {
                n++;
                finalFlush.add(flush.get(i - 1));
            } else {
                n = 1;
                finalFlush.clear();
            }
            if (n == 5) {
                finalFlush.add(flush.get(i));
                break;
            }
        }
        if (n == 5) {
            double result = 1;
            for (int i = 0; i < 5; i++) {
                result *= (finalFlush.get(i).getValue().ordinal() + 2);
            }
            return result;
        }
        return 0;
    }

    private static ArrayList<Deck.Card> findFlush(Player player, Table table) {
        ArrayList<Deck.Card> flush = new ArrayList<>();
        ArrayList<Deck.Card> allCards = new ArrayList<>(table.getTableCards());
        allCards.add(player.getSecondCard());
        allCards.add(player.getFirstCard());
        for (int i = 0; i < allCards.size(); i++) {
            for (int j = i + 1; j < allCards.size(); j++) {
                if (allCards.get(i).getKind().equals(allCards.get(j).getKind())) {
                    flush.add(allCards.get(j));
                }
            }
            flush.add(allCards.get(i));
            if (flush.size() < 5) {
                flush.clear();
            } else {
                return flush;
            }
        }
        return flush;
    }

    private static ArrayList<Deck.Card> findStraight(Player player, Table table) {
        ArrayList<Deck.Card> straights = new ArrayList<>();
        ArrayList<Deck.Card> allCards = new ArrayList<>(table.getTableCards());
        allCards.add(player.getFirstCard());
        allCards.add(player.getSecondCard());
        allCards.sort(new Comparator<Deck.Card>() {
            @Override
            public int compare(Deck.Card o1, Deck.Card o2) {
                return o1.getValue().ordinal() - o2.getValue().ordinal();
            }
        });
        for (int i = 1; i < allCards.size(); i++) {
            if (allCards.get(i).getValue().equals(allCards.get(i - 1).getValue())) {
                allCards.remove(i);
                i--;
            }
        }
        int n;
        for (int i = 0; i < allCards.size(); i++) {
            n = 1;
            for (int j = i + 1; j < allCards.size(); j++) {
                if (allCards.get(j).getValue().ordinal() - 1 == allCards.get(i).getValue().ordinal()) {
                    n++;
                }
                if (n == 5) {
                    straights.add(allCards.get(i));
                }
            }
        }
        return straights;
    }

    private static ArrayList<Deck.Card> findCards(Player player, Table table, int end) {
        ArrayList<Deck.Card> pairs = new ArrayList<>();
        ArrayList<Deck.Card> cards = new ArrayList<>(table.getTableCards());
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
