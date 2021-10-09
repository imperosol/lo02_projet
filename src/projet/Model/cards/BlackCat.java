package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;

public final class BlackCat extends AbstractRumourCard implements RumourCard{
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        cardOwner.discardCard(this);
        ArrayList<RumourCard> discard = cardOwner.getDiscardedCards();
        int choice;
        if (cardOwner.isHuman()) {
            System.out.println("Récupérez une carte parmi les suivantes :");
            choice = WitchHuntUtils.consoleSelectCardIndex(discard);
        } else { choice = 0; }
        RumourCard takenCard = discard.remove(choice);
        cardOwner.giveCard(takenCard);
        return cardOwner;
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
