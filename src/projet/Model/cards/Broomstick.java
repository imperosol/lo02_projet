package projet.Model.cards;

import projet.Model.player.Player;

import java.util.ArrayList;

public class Broomstick extends AbstractRumourCard implements RumourCard{
    @Override
    public Player witchEffect(Player cardOwner) {
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        return this.chooseNextPlayer(cardOwner, allPlayers);
    }
}
