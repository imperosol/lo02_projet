package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;

public final class Broomstick extends AbstractRumourCard implements RumourCard{
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        return this.chooseNextPlayer(cardOwner, allPlayers);
    }

    @Override
    public String toString() {
        return "Broomstick";
    }
}
