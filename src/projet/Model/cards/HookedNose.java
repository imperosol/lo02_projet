package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class representing the Hooked Nose rumour card
 * @author Thomas Girod
 */
public final class HookedNose extends AbstractRumourCard implements RumourCard{
    /**
     * {@inheritDoc}
     */
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        // Take a card from the accuser and give it to the owner of this card
        takeRandomCardFromPlayer(cardOwner, accuser);
        return cardOwner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        // Choose a player, then take a random card from his hand and give it
        // to the owner of this card
        Player nextPlayer = this.chooseNextPlayer(cardOwner, allPlayers);
        takeRandomCardFromPlayer(cardOwner, nextPlayer);
        return nextPlayer;
    }

    /**
     * custom method for hunt effect used by the gui
     * @param cardOwner the player who owns this card
     * @param next the player who is to take the next turn (must have previously selected by the current payer)
     * @return the player who is to take the next turn
     */
    public Player huntEffect(Player cardOwner, Player next) {
        // Choose a player, then take a random card from his hand and give it
        // to the owner of this card
        takeRandomCardFromPlayer(cardOwner, next);
        return next;
    }

    /**
     * method to take a random card from the hand of another player
     * @param cardOwner the player who uses the effect of the card
     * @param nextPlayer The player from whom the card is to be taken
     */
    private void takeRandomCardFromPlayer(Player cardOwner, Player nextPlayer) {
        ArrayList<RumourCard> nextPlayerCards = nextPlayer.getCards();
        final int handSize = nextPlayerCards.size();
        if (handSize > 0) {
            RumourCard card = nextPlayerCards.remove(new Random().nextInt(handSize));
            cardOwner.giveCard(card);
            if (cardOwner.isHuman()) {
                System.out.println(cardOwner.printIdentity() + ", vous avez récupéré la carte : " + card);
            }
        } else {
            System.out.println(cardOwner.printIdentity() + " n'a pas de carte, vous ne récupérez rien.");
        }
    }

    /**
     * {@inheritDoc}
     * @return "HookedNose
     */
    @Override
    public String toString() {
        return "HookedNose";
    }
}
