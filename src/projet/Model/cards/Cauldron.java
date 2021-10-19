package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;
import java.util.Random;

public final class Cauldron extends AbstractRumourCard implements RumourCard {
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        // the accuser discards a random card
        Random rand = new Random();
        ArrayList<RumourCard> accuserCards = accuser.getCards();
        accuser.discardCard(accuserCards.get(rand.nextInt(accuserCards.size())));
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        cardOwner.revealIdentity();
        if (cardOwner.isWitch()) {
            // return the player to the left of the card owner
            int playerIndex = allPlayers.indexOf(cardOwner);
            return allPlayers.get(playerIndex + 1);
        } else {
            return this.chooseNextPlayer(cardOwner, allPlayers);
        }
    }

    @Override
    public String toString() {
        return "Cauldron";
    }
}
