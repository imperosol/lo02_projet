package projet.Model.player;

import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class which correspond to an aggressive posture in which the AI always accuse the player
 * who has the fewer cards in hand and therefore has the less ability to defend himself.
 * The players whose identity is known are prioritized over those who are not known
 */
public class AIStrategyAggressive implements AIStrategy {

    /**
     * Getter of the player to accuse
     * @param strategyUser parameter which contains the strategy used
     * @param toExclude parameter which contain the AI player which is to exclude of the strategy
     * @return the player chosen to be the victim of the strategy
     */
    @Override
    public Player getPlayerToAccuse(ComputerPlayer strategyUser, Player toExclude) {
        /* the aggressive AI always accuse the player who has the less cards in hand
         * and therefore has the less ability to defend himself.
         * The players whose identity is known are prioritized over those who are not known */
        int minCard = 10;
        Player toChoose = null;
        ArrayList<Player> secretlyKnown = strategyUser.getSecretlyKnownPlayer();
        for (Player p : secretlyKnown) {
            if (p.isWitch() && p.getCards().size() < minCard && p != toExclude) {
                minCard = p.getCards().size();
                toChoose = p;
            }
        }
        if (toChoose != null) {
            return toChoose;
        }

        // this part is reached only is there is no known revealable player who is a witch
        ArrayList<Player> revealable = WitchHuntUtils.getRevealablePlayers(strategyUser, strategyUser.game.getPlayers());
        for (Player p : revealable) {
            if (p.getCards().size() < minCard && !secretlyKnown.contains(p)) {
                // if this code is reached, that means the secretlyKnown list
                // either is empty or contains only villagers players
                minCard = p.getCards().size();
                toChoose = p;
            }
        }
        if (toChoose != null) {
            return toChoose;
        } else {
            return revealable.get(new Random().nextInt(revealable.size()));
        }
    }

    /**
     * getter of the attack action.
     * If the player has no more cards, he must accuse, else, he's got 1/3 chance to use a rumour card
     * and 2/3 chance to accuse a player
     * @param strategyOwner : the computer player who own the strategy
     * @return the accused player
     */
    @Override
    public int getAttackAction(ComputerPlayer strategyOwner) {
        final int ACCUSE_PLAYER = 1, REVEAL_CARD = 2;
        // If the player has no more cards, he must accuse
        if (strategyOwner.rumourCards.size() == 0) {
            return ACCUSE_PLAYER;
        }
        /* 1 chance out of 3 to use a rumour card
         * 2 chances out of 3 to accuse a player*/
        final int choice = new Random().nextInt(3);
        if (choice == 0) {
            return REVEAL_CARD;
        } else {
            return ACCUSE_PLAYER;
        }
    }

    /**
     * select randomly the next player when a card asks for this effect
     * @param strategyOwner the player who uses this strategy
     * @param selectablePlayers a list of players who can be selected
     * @return the selected player
     */
    @Override
    public Player selectNextPlayer(ComputerPlayer strategyOwner, List<Player> selectablePlayers) {
        // select a random player
        Random random = new Random();
        int index = random.nextInt(selectablePlayers.size());
        return selectablePlayers.get(index);
    }
}
