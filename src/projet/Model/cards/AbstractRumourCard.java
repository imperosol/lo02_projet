package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRumourCard {
    public Player chooseNextPlayer(Player cardOwner, @NotNull List<Player> allPlayers) {
        ArrayList<Player> selectablePlayers = WitchHuntUtils.getSelectablePlayers(cardOwner, allPlayers);
        System.out.println(selectablePlayers);
        Player p = cardOwner.selectNextPlayer(selectablePlayers);
        System.out.println(p);
        return p;
    }

    public Player huntRevealOwnIdentity(Player cardOwner, Player nextPlayer) {
        cardOwner.revealIdentity();
        List<Player> allPlayers = cardOwner.getGame().getPlayers();
        if (cardOwner.isWitch()) {
            int playerIndex = allPlayers.indexOf(cardOwner);
            return allPlayers.get((playerIndex + 1) % allPlayers.size());
        } else {
            return nextPlayer;
        }
    }
}
