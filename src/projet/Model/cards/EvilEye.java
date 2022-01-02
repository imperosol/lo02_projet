package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;

/**
 * The class representing the EvilEye rumour card
 * @author Pierrick Dheilly
 */
public final class EvilEye extends AbstractRumourCard implements RumourCard {

    /**
     * {@inheritDoc}
     */
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return this.chooseNextPlayer(cardOwner, allPlayers);
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
     * @return "EvilEye
     */
    @Override
    public String toString() {
        return "EvilEye";
    }
}
