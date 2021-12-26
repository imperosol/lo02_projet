package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.View.CLIView;

import java.util.ArrayList;
import java.util.Random;

public final class BlackCat extends AbstractRumourCard implements RumourCard {
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
            choice = CLIView.consoleSelectCardIndex(discard);
        } else {
            choice = new Random().nextInt(discard.size());
        }
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
