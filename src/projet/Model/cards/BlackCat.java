package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.View.CLIView;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class representing the Broomstick rumour card
 * @author Pierrick Dheilly
 */
public final class BlackCat extends AbstractRumourCard implements RumourCard {
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
        cardOwner.discardCard(this);
        ArrayList<RumourCard> discard = cardOwner.getDiscardedCards();
        int choice;
        if (cardOwner.isHuman()) {
            System.out.println("Récupérez une carte parmi les suivantes :");
            choice = CLIView.consoleSelectCardIndex(discard);
        } else {
            choice = new Random().nextInt(discard.size());
        }
        RumourCard takenCard = discard.remove(choice);
        cardOwner.giveCard(takenCard);
        return cardOwner;
    }

    /**
     * Custom hunt effect method used for the GUI
     * @param cardOwner the player who owns this card
     * @param toTake the card the owner of this card has chosen before calling this methos
     * @return the player who owns this card
     */
    public Player huntEffect(Player cardOwner, RumourCard toTake) {
        cardOwner.discardCard(this);
        ArrayList<RumourCard> discard = cardOwner.getDiscardedCards();
        RumourCard takenCard = discard.remove(discard.indexOf(toTake));
        cardOwner.giveCard(takenCard);
        return cardOwner;
    }

    /**
     * {@inheritDoc}
     * @param cardOwner the player who owns the card
     * @return true if there is at least one card in the discard of the game
     */
    @Override
    public boolean isHuntEffectUsable(Player cardOwner) {
        return !cardOwner.getGame().getDiscardedCards().isEmpty();
    }

    /**
     * {@inheritDoc}
     * @return "BlackCat"
     */
    @Override
    public String toString() {
        return "BlackCat";
    }
}
