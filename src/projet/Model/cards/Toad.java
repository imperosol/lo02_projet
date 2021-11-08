package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;

public final class Toad extends AbstractRumourCard implements RumourCard{
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return cardOwner; //Card owner is the next player
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        cardOwner.revealIdentity(); //reveal card owner identity
        if (cardOwner.isWitch()) { //if card owner is a witch
            // return the player to the left of the card owner
            int playerIndex = allPlayers.indexOf(cardOwner);
            return allPlayers.get(playerIndex + 1);
        } else { //if card owner is a villager
            Player nextPlayer;
            ArrayList<Player> revealablePlayers = WitchHuntUtils.getSelectablePlayers(cardOwner, allPlayers);
            if (cardOwner.isHuman()) { //he chooses next player if he's human
                nextPlayer = WitchHuntUtils.consoleSelectPlayer(revealablePlayers);
            } else {//and if it's an ia, it ...
                // TODO : implémenter comportement IA
                nextPlayer = revealablePlayers.get(0);
            }
            return nextPlayer;
        }
    }

    @Override
    public String toString() {
        return "Toad";
    }
}
