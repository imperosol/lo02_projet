package projet.Model.player;

import projet.Model.cards.RumourCard;

import java.util.List;

public class AIStrategyAggressive implements AIStrategy {
    @Override
    public Player getPlayerToAccuse(ComputerPlayer strategyUser) {
        int maxCard = 10;
        Player toChoose = null;
        for (Player p : strategyUser.game.getPlayers()) {
            if (p.getCards().size() < maxCard) {
                maxCard = p.getCards().size();
                toChoose = p;
            }
        }
        return toChoose;
    }

    @Override
    public Player applyWitchEffect(Player cardOwner, List<RumourCard> rumourCards) {
        return null;
    }
}
