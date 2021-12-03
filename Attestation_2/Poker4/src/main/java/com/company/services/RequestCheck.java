package com.company.services;

import com.company.entities.Croupier;
import com.company.entities.Player;
import com.company.entities.Table;

public class RequestCheck {

    public void callCheck(Player player, Croupier croupier) {
        player.check(croupier);
    }

}
