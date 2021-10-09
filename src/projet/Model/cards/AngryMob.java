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
        if (cardOwner.isHuman()) {
            toReveal = WitchHuntUtils.consoleSelectPlayer(revealable);
        } else {
            // TODO : implémenter le comportement de l'IA
            toReveal = revealable.get(0);
        }
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
    public boolean isWitchEffectUsable(Player cardOwner) {
        // effect usable if the card has already revealed a rumour card
        return (cardOwner.isRevealed() && !cardOwner.isWitch());
    }

    @Override
    public String toString() {
        return "AngryMob";
    }
}
