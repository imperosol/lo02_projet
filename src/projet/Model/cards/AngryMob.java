package projet.Model.cards;

import projet.Model.player.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class AngryMob extends AbstractRumourCard implements RumourCard {

    @Override
    public Player witchEffect(Player cardOwner) {
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        ArrayList<Player> revealable = this.getRevealablePlayers(cardOwner, allPlayers);
        outerLoop:
        for (Player p : revealable) {
            for (RumourCard card : p.getRevealedCards()) {
                if (card instanceof Broomstick) {
                    revealable.remove(p);
                    break outerLoop;
                }
            }
        }
        Player toReveal = this.shellPlayerSelection(revealable);
        toReveal.revealIdentity();
        if (toReveal.isWitch()) {
            cardOwner.addPoints(2);
            return cardOwner;
        } else {
            cardOwner.addPoints(-2);
            return toReveal;
        }
    }


}
