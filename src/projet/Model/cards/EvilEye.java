package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;
///////////////////////////////////////////////////////////////////////////////

public final class EvilEye extends AbstractRumourCard implements RumourCard {
    // TODO : implémenter l'obligation de dénoncer un autre joueur que le possesseur de la carte en mettant des restrictions directement dans la classe Game

    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return this.chooseNextPlayer(cardOwner, allPlayers);
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        return this.chooseNextPlayer(cardOwner, allPlayers);
    }

    @Override
    public String toString() {
        return "EvilEye";
    }
}
