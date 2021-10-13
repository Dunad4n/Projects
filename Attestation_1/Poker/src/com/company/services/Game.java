package com.company.services;

import com.company.entites.Croupier;
import com.company.entites.Player;
import com.company.entites.Table;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public static void start() {
        System.out.println("Введите количество игроков : ");
        Scanner sc = new Scanner(System.in);
        int countOfPlayers = sc.nextInt();
        System.out.println("Под каким номером вы хотите играть?");
        int number = sc.nextInt();
        while (number > countOfPlayers) {
            System.out.println("Введите корректный номер игрока ");
            number = sc.nextInt();
        }
        Croupier croupier = new Croupier();
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < countOfPlayers; i++) {
            players.add(new Player());
        }
        String str = " ";
        while (!str.equals("Выйти")) {
            croupier.shuffle();
            gaming(players, croupier, number);
            System.out.println("Введите 'Продолжить', если хотите сыграть ещё или 'Выйти', если хотите закончить играть");
            str = sc.next();
        }
        System.out.println("                                                            Спасибо за игру! До встречи!");
    }

    private static void gaming(ArrayList<Player> players, Croupier croupier, int number) {
        croupier.distributeCardsToPlayers(players);
        Table table = new Table();
        drawGame(players, table, number);
        write(players.get(number - 1), croupier);
        croupier.distributeCardsToTable(table, true);
        drawGame(players, table, number);
        write(players.get(number - 1), croupier);
        croupier.distributeCardsToTable(table, false);
        drawGame(players, table, number);
        write(players.get(number - 1), croupier);
        croupier.distributeCardsToTable(table, false);
        drawGame(players, table, number);
        write(players.get(number - 1), croupier);
        findWinner(players, table, croupier, number);
    }

    private static void drawGame(ArrayList<Player> players, Table table, int number) {
        for (int i = 0; i < 200; i++) {
            System.out.print("-");
        }
        System.out.print("\n");
        for (int i = 0; i < 70; i++) {
            System.out.print(" ");
        }
        String space = "                                                      ";
        if (table.getTableCards().size() > 0) {
            System.out.println("На столе лежат");
            System.out.print(space + "|| ");
            for (int i = 0; i < table.getTableCards().size(); i++) {
                System.out.print(table.getTableCards().get(i).getValue() + " " + table.getTableCards().get(i).getKind() + " || ");
            }
        } else {
            System.out.println("На столе пока пусто.");
        }
        for (int i = 0; i < 3; i++) {
            System.out.print("\n");
        }
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getFirstCard() == null) {
                if (i + 1 == number) {
                    System.out.println(space + "Вы скинули свои карты. ");
                }    
            }else if (i + 1 == number) {
                System.out.println(space + " Ваши карты" + " : " + players.get(i).getFirstCard().getValue() +
                        " " + players.get(i).getFirstCard().getKind() + ", " + players.get(i).getSecondCard().getValue() +
                        " " + players.get(i).getSecondCard().getKind() + "     ");
            } else {
                System.out.println(space + " Карты игрока под номером " + (i + 1) + " : " + players.get(i).getFirstCard().getValue() +
                        " " + players.get(i).getFirstCard().getKind() + ", " + players.get(i).getSecondCard().getValue() +
                        " " + players.get(i).getSecondCard().getKind() + "     ");
            }
        }
    }

    private static void write(Player player, Croupier croupier) {
        String str;
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите 'Поставить n'(n - размер ставки)," +
                " чтобы поставить некоторую сумму, 'Банк', чтобы узнать размер своего банка или 'Продолжить', чтобы продолжить.");
        str = sc.nextLine();
        while (!str.equals("Продолжить")){
            String[] mas = str.split(" ");
            if (mas[0].equals("Поставить")) {
                player.bet(croupier, Integer.parseInt(mas[1]));
                break;
            }
            if (mas[0].equals("Банк")) {
                System.out.println("У вас осталось " + player.getBank());
            }
            System.out.println("Введите 'Поставить n'(n - размер ставки), " +
                    " чтобы поставить некоторую сумму, 'Банк', чтобы узнать размер своего банка или 'Продолжить', чтобы продолжить.");
            str = sc.nextLine();
        }
    }

    private static void findWinner(ArrayList<Player> players, Table table, Croupier croupier, int number) {
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            result.add(Combination.forceOfCombination(players.get(i), table));
        }
        double max = result.get(0);
        for (int i = 1; i < result.size(); i++) {
            if (max < result.get(i)) {
                max = result.get(i);
            }
        }
        if (result.indexOf(max) + 1 == number ) {
            System.out.println("                                                       Вы победили и выиграли " + croupier.getBank() + "!");
            players.get(number - 1).setBank(croupier.getBank());
        } else {
            System.out.println("                                                 Победил игрок под номером "
                    + (result.indexOf(max) + 1) + " и выиграл " + croupier.getBank());
            players.get(result.indexOf(max)).setBank(croupier.getBank());
            if (players.get(number - 1).getBank() == 0) {
                System.out.println("                                                 У вас закончились деньги, вы проиграли :(");
            }
        }
        croupier.setBank(-croupier.getBank());
    }

}
