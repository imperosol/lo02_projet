package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;

/**
 * The class representing the Broomstick rumour card
 * @author Pierrick Dheilly
 */
public final class Broomstick extends AbstractRumourCard implements RumourCard{
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
     * @return "BroomStick"
     */
    @Override
    public String toString() {
        return "Broomstick";
    }
}
