package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;

public final class AngryMob extends AbstractRumourCard implements RumourCard {

    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        ArrayList<Player> revealable = WitchHuntUtils.getRevealablePlayers(cardOwner, allPlayers);
        Player toExclude = null;
        // if the Broomstick card has been revealed, remove its owner from the list of revealable players
        outerLoop:
        for (Player p : revealable) {
            for (RumourCard card : p.getRevealedCards()) {
                if (card instanceof Broomstick) {
                    toExclude = p;
                    break outerLoop;
                }
            }
        }
        Player toReveal = cardOwner.getPlayerToAccuse(toExclude);
        toReveal.revealIdentity();
        if (toReveal.isWitch()) {
            cardOwner.addPoints(2);
            return cardOwner;
        } else {
            cardOwner.addPoints(-2);
            return toReveal;
        }
    }

    @Override
    public boolean isHuntEffectUsable(Player cardOwner) {
        /* The effect is not usable if there is only one other player
        * and this player has revealed Broomstick.
        * Moreover, the card owner must have previously revealed one of his rumour cards */
        ArrayList<Player> revealable = WitchHuntUtils.getRevealablePlayers(cardOwner, cardOwner.getGame().getPlayers());
        for (Player p : revealable) {
            for (RumourCard card : p.getRevealedCards()) {
                if (!(card instanceof Broomstick)) {
                    return (cardOwner.isRevealed() && !cardOwner.isWitch());
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AngryMob";
    }
}
