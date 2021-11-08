package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;
/////////////////////////////////////////////////////////////////////////////////////:
//Je mets quoi là ?
public interface RumourCard {

    Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser);

    Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers);

    default boolean isWitchEffectUsable(Player cardOwner) {
        return true;
    }

    default boolean isHuntEffectUsable(Player cardOwner) {
        return true;
    }

//    Yami no chikara o himeshi kagi yo,
//    Shin no sugata o ware no mae ni shimese.
//    Keiyaku no moto Sakura ga meijiru.

}
