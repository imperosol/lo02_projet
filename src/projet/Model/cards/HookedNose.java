package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;
import java.util.Random;

public final class HookedNose extends AbstractRumourCard implements RumourCard{
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        // Take a card from the accuser and give it to the owner of this card
        ArrayList<RumourCard> accuserCards = accuser.getCards();
        System.out.println("Récupérez une carte parmi les suivantes :");
        int choice = WitchHuntUtils.consoleSelectCardIndex(accuserCards);
        RumourCard card = accuserCards.remove(choice);
        cardOwner.giveCard(card);
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        // Choose a player, then take a random card from his hand and give it to the owner of this card
        Player nextPlayer = this.chooseNextPlayer(cardOwner, allPlayers);
        Random random = new Random();
        ArrayList<RumourCard> nextPlayerCards = nextPlayer.getCards();
        RumourCard card = nextPlayerCards.remove(random.nextInt(nextPlayerCards.size()));
        cardOwner.giveCard(card);
        return nextPlayer;
    }

    @Override
    public String toString() {
        return "HookedNose";
    }
}
