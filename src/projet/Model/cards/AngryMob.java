package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;

public final class AngryMob extends AbstractRumourCard implements RumourCard {

    ///////////////////////////////////////////////////////////////////////////
    //Redéfinition (héritage) ?
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return cardOwner;//For the witch effect, we return the cardowner and he becomes next player
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        ArrayList<Player> revealable = WitchHuntUtils.getRevealablePlayers(cardOwner, allPlayers);
        // if the Broomstick card has been revealed, remove its owner from the list of revealable players
        outerLoop:
        for (Player p : revealable) {
            for (RumourCard card : p.getRevealedCards()) {
                if (card instanceof Broomstick) {
                    revealable.remove(p);
                    break outerLoop;
                }
            }
        }
        Player toReveal;
        if (cardOwner.isHuman()) { //If the player is human, then,
            toReveal = WitchHuntUtils.consoleSelectPlayer(revealable); //the game tells him the
        //revealable players,
        } else {//else (if the player is an ia) then,
            // TODO : implémenter le comportement de l'IA
            toReveal = revealable.get(0);
        }
        toReveal.revealIdentity();
        if (toReveal.isWitch()) { //If the player chosen is a witch, then the card owner
            cardOwner.addPoints(2); //gains two points
            return cardOwner; //and he's next to play
        } else {
            cardOwner.addPoints(-2); //else he looses two
            return toReveal; //and the accused one is next to play
        }
    }

    @Override
    public boolean isWitchEffectUsable(Player cardOwner) {
        // effect usable if the card has already revealed a rumour card
        return (cardOwner.isRevealed() && !cardOwner.isWitch());
    }

    @Override
    public String toString() {
        return "AngryMob";
    }
}
