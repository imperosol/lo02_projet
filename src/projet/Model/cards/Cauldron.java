package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The class representing the Cauldron rumour card
 * @author Pierrick Dheilly
 */
public final class Cauldron extends AbstractRumourCard implements RumourCard {
    /**
     * {@inheritDoc}
     */
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        // the accuser discards a random card
        Random rand = new Random();
        ArrayList<RumourCard> accuserCards = accuser.getCards();
        final int nbr_cards = accuserCards.size();
        if (nbr_cards > 0) {
            RumourCard toDiscard = accuserCards.get(rand.nextInt(nbr_cards));
            System.out.println(accuser.printIdentity() + " discards " + toDiscard);
            accuser.discardCard(toDiscard);
        } else {
            System.out.println(accuser.printIdentity() + " has no card, he doesn't discard anything.");
        }
        return cardOwner;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     * @return "Cauldron"
     */
    @Override
    public String toString() {
        return "Cauldron";
    }
}
