package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.View.CLIView;

import java.util.ArrayList;
import java.util.Random;

public final class PointedHat extends AbstractRumourCard implements RumourCard{
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        this.takeOneOfOwnRevealedCards(cardOwner);
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        this.takeOneOfOwnRevealedCards(cardOwner);
        return this.chooseNextPlayer(cardOwner, allPlayers);
    }

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


    @Override
    public boolean isHuntEffectUsable(Player cardOwner) {
        // effect usable if the card has already revealed a rumour card
        return cardOwner.getRevealedCards().size() > 0;
    }


    @Override
    public boolean isWitchEffectUsable(Player cardOwner) {
        // effect usable if the card has already revealed a rumour card
        return cardOwner.getRevealedCards().size() > 0;
    }


    @Override
    public String toString() {
        return "PointedHat";
    }
}
