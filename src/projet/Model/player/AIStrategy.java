package projet.Model.player;

import projet.Model.cards.RumourCard;

import java.util.List;

public interface AIStrategy {
    Player getPlayerToAccuse(ComputerPlayer strategyUser);

    Player applyWitchEffect(Player cardOwner, List<RumourCard> rumourCards);
}
