package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;

public final class EvilEye extends AbstractRumourCard implements RumourCard {

    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return this.chooseNextPlayer(cardOwner, allPlayers);
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        return this.chooseNextPlayer(cardOwner, allPlayers);
    }

    @Override
    public boolean witchUserTakesTurn() {
        return false;
    }

    @Override
    public boolean witchNeedsInteraction() {
        return false;
    }

    @Override
    public String toString() {
        return "EvilEye";
    }
}
