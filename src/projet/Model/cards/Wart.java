package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;

/**
 * The class representing the Wart rumour card
 * @author Pierrick Dheilly
 */
public final class Wart extends AbstractRumourCard implements RumourCard {
    /**
     * {@inheritDoc}
     */
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return cardOwner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        return this.chooseNextPlayer(cardOwner, allPlayers);
    }

    /**
     * {@inheritDoc}
     * @return "Wart
     */
    @Override
    public String toString() {
        return "Wart";
    }
}
