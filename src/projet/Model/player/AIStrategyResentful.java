package projet.Model.player;

import projet.Model.cards.RumourCard;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class AIStrategyResentful implements AIStrategy {
    public Player getPlayerToAccuse(ComputerPlayer strategyUser) {
        Map<Player, Integer> previousAccusers = strategyUser.getAccusers();
        int maxAccusation = -1;
        Player accuserMax = null;
        for (Player key : previousAccusers.keySet()) {
            if (previousAccusers.get(key) > maxAccusation) {
                maxAccusation = previousAccusers.get(key);
                accuserMax = key;
            }
        }
        return accuserMax;
    }

    public Player applyWitchEffect(Player cardOwner, List<RumourCard> usableCards) {
        return null;
    }

    @Override
    public int getAttackAction(ComputerPlayer strategyOwner) {
        /* 1 chance out of 2 to use a rumour card
         * 1 chance out of 2 to accuse a player*/
        final int ACCUSE_PLAYER = 1, REVEAL_CARD = 2;
        Random random = new Random();
        int choice = random.nextInt(2);
        if (choice == 0) {
            return REVEAL_CARD;
        } else {
            return ACCUSE_PLAYER;
        }
    }

    @Override
    public Player selectNextPlayer(ComputerPlayer strategyOwner, List<Player> selectablePlayers) {
        // Select the player which has the less accused the current player
        Map<Player, Integer> previousAccusers = strategyOwner.getAccusers();
        int min = 100;
        Player chosenPlayer = null;
        for (Player p : selectablePlayers) {
            if (previousAccusers.get(p) < min) {
                min = previousAccusers.get(p);
                chosenPlayer = p;
            }
        }
        return chosenPlayer;
    }

}
