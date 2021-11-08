package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;
import java.util.Random;

public final class Cauldron extends AbstractRumourCard implements RumourCard {
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        // the accuser discards a random card
        Random rand = new Random();
        ArrayList<RumourCard> accuserCards = accuser.getCards();
        RumourCard toDiscard = accuserCards.get(rand.nextInt(accuserCards.size()));
        cardOwner.revealCard(toDiscard);
        return cardOwner; //then the card owner becomes next player
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        cardOwner.revealIdentity();
        if (cardOwner.isWitch()) {//If the card owner is a witch, then
            // return the player to the left of the card owner and he is next player
            int playerIndex = allPlayers.indexOf(cardOwner);
            return allPlayers.get(playerIndex + 1);
        } else { //else if the player isn't a witch
            Player nextPlayer;
            ///////////////////////////////////////////////////////////////////////////////////
            //elle sert à quoi la array list ?
            ArrayList<Player> revealablePlayers = WitchHuntUtils.getSelectablePlayers(cardOwner, allPlayers);
            if (cardOwner.isHuman()) { //if the player is human
                nextPlayer = WitchHuntUtils.consoleSelectPlayer(revealablePlayers); //He chooses the next player
            } else {//If it is an ia,
                // TODO : implémenter comportement IA
                nextPlayer = revealablePlayers.get(0);
            }
            return nextPlayer;
        }
    }

    @Override
    public String toString() {
        return "Cauldron";
    }
}
