package projet.Model.player;

import projet.Model.cards.RumourCard;

import java.util.List;

public interface AIStrategy {
    Player getPlayerToAccuse(ComputerPlayer strategyUser, Player toExclude);

    /**
     * Determines if the computer player using the strategy shall accuse a player or reveal a rumour card
     * @return 1 if the action is to accuse a player, 2 if the action is to use the hunt effect of a card
     * @param strategyOwner : the computer player who own the strategy
     */
    int getAttackAction(ComputerPlayer strategyOwner);

    Player selectNextPlayer(ComputerPlayer strategyOwner, List<Player> selectablePlayers);
}
