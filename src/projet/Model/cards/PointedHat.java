package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.View.CLIView;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class representing the Pointed Hat rumour card
 * @author Thomas Girod
 */
public final class PointedHat extends AbstractRumourCard implements RumourCard{

    /**
     * {@inheritDoc}
     */
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        this.takeOneOfOwnRevealedCards(cardOwner);
        return cardOwner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        this.takeOneOfOwnRevealedCards(cardOwner);
        return this.chooseNextPlayer(cardOwner, allPlayers);
    }

    /**
     * Method to take a revealed card and put it back in the hand of the player.
     * Used by {@link PointedHat#huntEffect(Player, ArrayList)} and {@link PointedHat#witchEffect(Player, ArrayList, Player)}
     * @param cardOwner the player who uses this card
     */
    private void takeOneOfOwnRevealedCards(Player cardOwner) {
        // This method is used by both the huntEffect and the witchEffect methods of this class
        ArrayList<RumourCard> cards = cardOwner.getRevealedCards();
        int choice;
        if (cardOwner.isHuman()) {
            System.out.println("Récupérez une carte parmi les suivantes :");
            choice = CLIView.consoleSelectCardIndex(cards);
        } else {
            choice = new Random().nextInt(cards.size());
        }
        cardOwner.hideCard(choice);
    }

    /**
     * {@inheritDoc}
     * @return true if the card owner has at least one element in his revealedCard List
     */
    @Override
    public boolean isHuntEffectUsable(Player cardOwner) {
        // effect usable if the card has already revealed a rumour card
        return !cardOwner.getRevealedCards().isEmpty();
    }

    /**
     * {@inheritDoc}
     * @return true if the card owner has at least one element in his revealedCard List
     */
    @Override
    public boolean isWitchEffectUsable(Player cardOwner) {
        // effect usable if the card has already revealed a rumour card
        return !cardOwner.getRevealedCards().isEmpty();
    }

    /**
     * toString method of this card
     * @return "PointedHat"
     */
    @Override
    public String toString() {
        return "PointedHat";
    }
}
