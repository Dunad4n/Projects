package com.company.services;

import com.company.entities.Croupier;
import com.company.entities.Player;
import com.company.entities.Table;
import javafx.scene.text.Text;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.logging.Logger;

public class RequestAction {

    public void callAction(Player player, Table table, Croupier croupier, Combination cmb) {
        int result = new Random().nextInt(1, 101);
        if (result < 61) {
            new RequestBet().callBet(player, table, croupier, cmb, new Text());
        } else if (result < 91) {
            new RequestCheck().callCheck(player, croupier);
        } else {
            new RequestFold().callFold(player);
        }
    }

}
