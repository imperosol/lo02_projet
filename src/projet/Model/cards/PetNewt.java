package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Controller.MainController;
import projet.Model.player.Player;
import projet.View.CLIView;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class representing the Pet Newt rumour card
 * @author Thomas Girod
 */
public final class PetNewt extends AbstractRumourCard implements RumourCard {
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
        ArrayList<RumourCard> revealedCards = this.getAllRevealedCards(cardOwner, allPlayers);
        int choice;
        if (revealedCards.size() == 0) {
            System.out.println("Aucune carte révélée, vous ne pouvez rien récupérer.");
        } else {
            if (cardOwner.isHuman()) {
                System.out.println("Récupérez une carte parmi les suivantes :");
                choice = CLIView.consoleSelectCardIndex(revealedCards);
            } else {
                choice = new Random().nextInt(revealedCards.size());
            }
            RumourCard card = revealedCards.get(choice);
            Player opponent = this.getCardOwner(card, allPlayers);
            if (opponent != null) {
                opponent.getRevealedCards().remove(card);
                cardOwner.giveCard(card);
            }
        }
        return this.chooseNextPlayer(cardOwner, allPlayers);
    }

    /**
     * custom hunt effect used by the gui
     * @param cardOwner the player who owns this card
     * @param toTake the card that the card owner wants to take
     * @param selected the player that the card owner has selected
     * @return the player that is to take the next turn (here, the selected player)
     */
    public Player huntEffect(Player cardOwner, RumourCard toTake, Player selected) {
        if (toTake != null) {
            Player opponent = this.getCardOwner(toTake, cardOwner.getGame().getPlayers());
            if (opponent != null) {
                opponent.getRevealedCards().remove(toTake);
                cardOwner.giveCard(toTake);
            }
        }
        return selected;
    }

    /**
     * Method to find the owner of a specific rumour card
     * @param card the card from which the owner is to be found
     * @param allPlayers an ArrayList with all the players in the game
     * @return the player who owns the card
     */
    private Player getCardOwner(RumourCard card, @NotNull ArrayList<Player> allPlayers) {
        for (Player p : allPlayers) {
            for (RumourCard rumourCard : p.getRevealedCards()) {
                if (rumourCard == card) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * method to get all the revealed cards of all the players but the owner of this card
     * @param cardOwner the player who owns the card
     * @param allPlayers the arraylist of all players in the game
     * @return an ArrayList with all the revealed rumour cards in the game, excepting those of the current player
     */
    private ArrayList<RumourCard> getAllRevealedCards(Player cardOwner, ArrayList<Player> allPlayers) {
        ArrayList<RumourCard> revealedCards = new ArrayList<>();
        for (Player p : allPlayers) {
            if (p != cardOwner) {
                revealedCards.addAll(p.getRevealedCards());
            }
        }
        return revealedCards;
    }

    /**
     * {@inheritDoc}
     * @return "PetNewt"
     */
    @Override
    public String toString() {
        return "PetNewt";
    }
}
