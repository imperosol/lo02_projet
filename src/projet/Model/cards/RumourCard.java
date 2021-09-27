package projet.Model.cards;

import projet.Model.player.Player;

import java.util.ArrayList;

public interface RumourCard {
    Player witchEffect(Player cardOwner);
    Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers);

//    Yami no chikara o himeshi kagi yo,
//    Shin no sugata o ware no mae ni shimese.
//    Keiyaku no moto Sakura ga meijiru.

}
