package projet.Model.player;

import projet.Model.cards.RumourCard;

import java.util.List;
import java.util.Random;

//Strat to follow if we want the IA to be agressive


////////////////////////////////////////////////////////////////////////////
//pas compris ça
public class AIStrategyAggressive implements AIStrategy {
    @Override
    public Player getPlayerToAccuse(ComputerPlayer strategyUser) {
        int minCard = 10;
        Player toChoose = null;
        for (Player p : strategyUser.game.getPlayers()) {
            if (p.getCards().size() < minCard) {
                minCard = p.getCards().size();
                toChoose = p;
            }
        }
        return toChoose;
    }

    @Override
    public Player applyWitchEffect(Player cardOwner, List<RumourCard> rumourCards) {
        // TODO : implémenter cette méthode
        return null;
    }

    @Override
    public int getAttackAction(ComputerPlayer strategyOwner) {
        /* 1 chance out of 3 to use a rumour card
        * 2 chances out of 3 to accuse a player*/
        final int ACCUSE_PLAYER = 1, REVEAL_CARD = 2;
        final int choice = new Random().nextInt(3);
        if (choice == 0) {
            return REVEAL_CARD;
        } else {
            return ACCUSE_PLAYER;
        }
    }
}
