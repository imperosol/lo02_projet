package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRumourCard {

    Player chooseNextPlayer(Player cardOwner, @NotNull List<Player> allPlayers) {
        ArrayList<Player> selectablePlayers = WitchHuntUtils.getSelectablePlayers(cardOwner, allPlayers);
        return cardOwner.selectNextPlayer(selectablePlayers);
    }
}
