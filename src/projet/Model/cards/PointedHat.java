package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;

public final class PointedHat extends AbstractRumourCard implements RumourCard{
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        this.takeOneOfOwnRevealedCards(cardOwner); //take one of his own revealed cards into his hand again
        return cardOwner; //return the card owner and he becomes next player
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        this.takeOneOfOwnRevealedCards(cardOwner); //take one of his own revealed cards into his hand again
        return this.chooseNextPlayer(cardOwner, allPlayers); //The card owner choose the next player
    }

    private void takeOneOfOwnRevealedCards(Player cardOwner) {
        ArrayList<RumourCard> cards = cardOwner.getRevealedCards(); //get the revealed cards of the card owner
        int choice;
        if (cardOwner.isHuman()) { //If player is human then he chooses one
            System.out.println("Récupérez une carte parmi les suivantes :");
            choice = WitchHuntUtils.consoleSelectCardIndex(cards);
        } else { //else (it's an ia) it ...
            choice = 0;
            // TODO : implement AI behaviour for getting a revealed rumour card
        }
        cardOwner.hideCard(choice);
        ////////////////////////////////////////////////////////////////////////////
        //késecé au dessus ?
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
