package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;

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
        ArrayList<RumourCard> cards = cardOwner.getRevealedCards();
        int choice;
        if (cardOwner.isHuman()) {
            System.out.println("Récupérez une carte parmi les suivantes :");
            choice = WitchHuntUtils.consoleSelectCardIndex(cards);
        } else {
            choice = 0;
            // TODO : implement AI behaviour for getting a revealed rumour card
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
