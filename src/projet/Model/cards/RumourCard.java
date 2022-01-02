package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;

/**
 * The interface of a rumour card
 * The methods of this interface are aimed to implement the witch effect and the hunt effect of a card
 * @author Thomas Girod
 */
public interface RumourCard {

    /**
     * method to execute the witch effect of the rumour card
     * @param cardOwner the player who owns this card
     * @param allPlayers a list of all players participating in the game
     * @param accuser the player who accused the owner of this card
     * @return the player who is to take the next turn according to the effect of this card
     */
    Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser);

    /**
     * method to execute the hunt effect of the rumour card
     * @param cardOwner the player who owns this card
     * @param allPlayers a list of all players participating in the game
     * @return the player who is to take the next turn according to the effect of this card
     */
    Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers);

    /**
     * method to check is the witch effect of this card can be used
     * @param cardOwner the player who owns the card
     * @return true if the witch effect is usable, else false
     */
    default boolean isWitchEffectUsable(Player cardOwner) {
        return true;
    }

    /**
     * method to check is the hunt effect of this card can be used
     * @param cardOwner the player who owns the card
     * @return true if the hunt effect is usable, else false
     */
    default boolean isHuntEffectUsable(Player cardOwner) {
        return true;
    }

//    Yami no chikara o himeshi kagi yo,
//    Shin no sugata o ware no mae ni shimese.
//    Keiyaku no moto Sakura ga meijiru.

}
