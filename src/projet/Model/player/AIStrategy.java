package projet.Model.player;

import java.util.List;

/**
 * The interface for the strategies of the AI
 */
public interface AIStrategy {
    Player getPlayerToAccuse(ComputerPlayer strategyUser, Player toExclude);

    /**
     * Determines if the computer player using the strategy shall accuse a player or reveal a rumour card
     * @return 1 if the action is to accuse a player, 2 if the action is to use the hunt effect of a card
     * @param strategyOwner : the computer player who own the strategy
     */
    int getAttackAction(ComputerPlayer strategyOwner);

    /**
     * Select the next player. Function to use when a rumour card asks to select the next player
     * @param strategyOwner the player who uses this strategy
     * @param selectablePlayers a list of player who can be selected
     * @return the selected player
     */
    Player selectNextPlayer(ComputerPlayer strategyOwner, List<Player> selectablePlayers);
}
