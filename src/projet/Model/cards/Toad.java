package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;

public final class Toad extends AbstractRumourCard implements RumourCard{
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        cardOwner.revealIdentity();
        if (cardOwner.isWitch()) {
            // return the player to the left of the card owner
            int playerIndex = allPlayers.indexOf(cardOwner);
            return allPlayers.get((playerIndex + 1) % allPlayers.size());
        } else {
            return this.chooseNextPlayer(cardOwner, allPlayers);
        }
    }

    @Override
    public boolean witchUserTakesTurn() {
        return true;
    }

    @Override
    public boolean witchNeedsInteraction() {
        return false;
    }

    @Override
    public String toString() {
        return "Toad";
    }
}
