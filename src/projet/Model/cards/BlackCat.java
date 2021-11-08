package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;

public final class BlackCat extends AbstractRumourCard implements RumourCard{
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return cardOwner; //For the witch effect, we return the cardowner and he becomes next player
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        cardOwner.discardCard(this); //For the hunt effect, discard this card
        ArrayList<RumourCard> discard = cardOwner.getDiscardedCards(); //then among the discarded cards
        int choice;
        if (cardOwner.isHuman()) { //Let him choose one (if he is a human)
            System.out.println("Récupérez une carte parmi les suivantes :");
            choice = WitchHuntUtils.consoleSelectCardIndex(discard);
            ////////////////////////////////////////////////////////////////////////////////////////
        } else { choice = 0; } //Else (it's an ia) choose the first discarded card ?
        RumourCard takenCard = discard.remove(choice); //The discarded card chosen is remove from
        //the discarded cards
        cardOwner.giveCard(takenCard); //and add to the card owner hand
        return cardOwner; //and finally, the card owner takes next turn
    }

    @Override
    public boolean isHuntEffectUsable(Player cardOwner) {
        return cardOwner.getGame().getDiscardedCards().size() > 0;
    }

    @Override
    public String toString() {
        return "BlackCat";
    }
}
