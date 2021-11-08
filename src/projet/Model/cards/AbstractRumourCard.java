package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;
import java.util.List;
/////////////////////////////////////////////////////////////////////////////////////////////:
//why abstract ?
public abstract class AbstractRumourCard {

    Player chooseNextPlayer(Player cardOwner, @NotNull List<Player> allPlayers) {
        //This function is used when a player needs to choose the next one
        //////////////////////////////////////////////////////////////////////////////
        P layer nextPlayer = null;
        ArrayList<Player> selectablePlayers = WitchHuntUtils.getSelectablePlayers(cardOwner, allPlayers);
        //Creation of a list of the selectable players among all the players without the card owner
        if (cardOwner.isHuman())  {
            //If the card owner is human, then let him choose using consoleSelectPlayer
            nextPlayer = WitchHuntUtils.consoleSelectPlayer(selectablePlayers);
        }
        //Else its an AI and then must do this
        // TODO : implémenter comportement IA
        return nextPlayer; //return the next player
    }
    public static void getAllCards() {
///////////////////////////////////////////////////////////////////////////////////////:
    }
}
