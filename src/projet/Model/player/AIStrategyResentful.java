package projet.Model.player;

import projet.Model.cards.RumourCard;

import java.util.List;
import java.util.Map;

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

    public Player applyWitchEffect(Player cardOwner, List<RumourCard> rumourCards) {
        return null;
    }

}
