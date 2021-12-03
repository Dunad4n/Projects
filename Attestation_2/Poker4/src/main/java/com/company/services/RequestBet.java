package com.company.services;

import com.company.entities.Croupier;
import com.company.entities.Player;
import com.company.entities.Table;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;

public class RequestBet {

    public void callBet(Player player, Table table, Croupier croupier, Combination cmb, Text textTest) {
        double force = cmb.forceOfCombination(player, table);
        textTest.setText("Я нашел силу");
        if (force < 18) {
            textTest.setText("Зашёл в условие Force < 18");
            player.bet(croupier, new Random().nextInt(0, (int) (player.getBank() * 0.2 + 1)));
            textTest.setText("Force < 18");
            return;
        } else if (force < 576) {
            textTest.setText("Зашёл в условие Force < 576");
            player.bet(croupier, new Random().nextInt(0, (int) (player.getBank() * 0.4 + 1)));
            textTest.setText("Force < 576");
            return;
        } else if (force < 3320) {
            textTest.setText("Зашёл в условие Force < 3320");
            player.bet(croupier, new Random().nextInt(0, (int) (player.getBank() * 0.5 + 1)));
            textTest.setText("Force < 3320");
            return;
        } else if (force < 243546) {
            textTest.setText("Зашёл в условие Force < 243546");
            player.bet(croupier, new Random().nextInt(0, (int) (player.getBank() * 0.6 + 1)));
            textTest.setText("Force < 243546");
            return;
        } else if (force < 483786) {
            textTest.setText("Зашёл в условие Force < 483786");
            player.bet(croupier, new Random().nextInt(0, (int) (player.getBank() * 0.7 + 1)));
            textTest.setText("Force < 483786");
            return;
        } else if (force < 947522) {
            textTest.setText("Зашёл в условие Force < 947522");
            player.bet(croupier, new Random().nextInt(0, (int) (player.getBank() * 0.8 + 1)));
            textTest.setText("Force < 947522");
            return;
        } else if (force < 985952) {
            textTest.setText("Зашёл в условие Force < 985952");
            player.bet(croupier, new Random().nextInt(0, (int) (player.getBank() * 0.9 + 1)));
            textTest.setText("Force < 985952");
            return;
        }
        textTest.setText("Ни вошёл ни в одно условие");
        player.bet(croupier, new Random().nextInt(0, player.getBank() + 1));
        textTest.setText("Force < Max");
    }

}
