package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;
import java.util.Random;

public final class HookedNose extends AbstractRumourCard implements RumourCard{
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        // Take a card from the accuser and give it to the owner of this card
        takeRandomCardFromPlayer(cardOwner, accuser);
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        // Choose a player, then take a random card from his hand and give it
        // to the owner of this card
        Player nextPlayer = this.chooseNextPlayer(cardOwner, allPlayers);
        takeRandomCardFromPlayer(cardOwner, nextPlayer);
        return nextPlayer;
    }

    private void takeRandomCardFromPlayer(Player cardOwner, Player nextPlayer) {
        ArrayList<RumourCard> nextPlayerCards = nextPlayer.getCards();
        final int handSize = nextPlayerCards.size();
        if (handSize > 0) {
            int cardIndex = new Random().nextInt(handSize);
            RumourCard card = nextPlayerCards.remove(cardIndex);
            cardOwner.giveCard(card);
            if (cardOwner.isHuman()) {
                System.out.println(cardOwner.printIdentity() + ", vous avez récupéré la carte : " + card);
            }
        } else {
            System.out.println(cardOwner.printIdentity() + " n'a pas de carte, vous ne récupérez rien.");
        }
    }

    public boolean witchUserTakesTurn() {
        return true;
    }

    @Override
    public boolean witchNeedsInteraction() {
        return true;
    }

    @Override
    public String toString() {
        return "HookedNose";
    }
}
