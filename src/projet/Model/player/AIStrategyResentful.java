package projet.Model.player;

import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AIStrategyResentful implements AIStrategy {

    private Player getPlayerFromKnownWithoutResent(ArrayList<Player> secretlyKnown) {
        ArrayList<Player> revealableKnown = new ArrayList<>();
        for (Player p : secretlyKnown) {
            if (p.isWitch()) {
                revealableKnown.add(p);
            }
        }
        if (revealableKnown.size() == 0) {
            return null;
        } else {
            return revealableKnown.get(new Random().nextInt(revealableKnown.size()));
        }
    }

    /**
     * Method used to choose to attack a previous accuser of itself whom he know he's a witch
     *
     * @param secretlyKnown    List of the player that we now they're witch (using a rumour card)
     * @param previousAccusers list of players who previously accused us
     * @return the player chosen if we know a witch among the previous accuser, else null
     */
    private Player getPlayerFromKnownWithResent(ArrayList<Player> secretlyKnown, Map<Player, Integer> previousAccusers) {
        int maxAccusation = -1;
        Player toChoose = null;
        for (Player p : secretlyKnown) {
            if (p.isWitch() && previousAccusers.containsKey(p) && !p.isRevealed()) {
                if (previousAccusers.get(p) > maxAccusation) {
                    maxAccusation = previousAccusers.get(p);
                    toChoose = p;
                }
            }
        }
        return toChoose;
    }

    /**
     * Method used to choose to attack a previous accuser of itself among those who aren't known as witches
     *
     * @param secretlyKnown    List of players that we now they're witch (using a rumour card)
     * @param previousAccusers list of players that previously accuse us
     * @return the player chosen if there's a previous accuser who's not revealed, else null
     */
    private Player getPlayerFromUnknownWithResent(ArrayList<Player> secretlyKnown, Map<Player, Integer> previousAccusers) {
        int maxAccusation = -1;
        Player toChoose = null;
        for (Player p : previousAccusers.keySet()) {
            if (previousAccusers.get(p) > maxAccusation && !secretlyKnown.contains(p) && !p.isRevealed()) {
                maxAccusation = previousAccusers.get(p);
                toChoose = p;
            }
        }
        return toChoose;
    }

    /**
     * Method used to accuse a player if the 2 first options didn't work. This time, it checks
     * frst if there's a player whom we now he's a witch, else it chooses randomly
     *
     * @param revealable    List of the players who are revealable (not revealed yet)
     * @param secretlyKnown List of the player that we now they're witch (using a rumour card)
     * @return the player chosen if a player is accusable else null
     */
    private Player getPlayerFromUnknownWithoutResent(ArrayList<Player> revealable, ArrayList<Player> secretlyKnown) {
        ArrayList<Player> accuseList = new ArrayList<>();
        for (Player p : revealable) {
            if (!secretlyKnown.contains(p)) {
                accuseList.add(p);
            }
        }
        if (!accuseList.isEmpty()) {
            return accuseList.get(new Random().nextInt(accuseList.size()));
        } else {
            return null;
        }
    }

    /**
     * The resentful AI always accuse the player who has accused him the most.
     * The players whose identity is known are prioritized over those who are not known
     *
     * @param strategyUser Strategy used by the AI player
     * @param toExclude    parameter which contain the AI player which is to exclude of the strategy
     * @return the player chosen to be the victim of the resentful AI
     */
    @Override
    public Player getPlayerToAccuse(ComputerPlayer strategyUser, Player toExclude) {
        /* the resentful AI always accuse the player who has accused him the most.
         * The players whose identity is known are prioritized over those who are not known */
        ArrayList<Player> revealable = WitchHuntUtils.getRevealablePlayers(strategyUser, strategyUser.game.getPlayers());
        revealable.remove(toExclude);
        ArrayList<Player> secretlyKnown = strategyUser.getSecretlyKnownPlayer();
        Map<Player, Integer> previousAccusers = strategyUser.getAccusers();
        System.out.println("Choix de l'accusation rancunière. Joueur exclu : " + toExclude);
        System.out.println("Connus et rancune");
        Player toChoose = this.getPlayerFromKnownWithResent(secretlyKnown, previousAccusers);
        if (toChoose == null) {
            System.out.println("Connus sans rancune");
            toChoose = this.getPlayerFromKnownWithoutResent(secretlyKnown);
            if (toChoose == null) {
                System.out.println("Inconnus avec rancune");
                toChoose = this.getPlayerFromUnknownWithResent(secretlyKnown, previousAccusers);
                if (toChoose == null) {
                    System.out.println("Inconnus sans rancune");
                    toChoose = this.getPlayerFromUnknownWithoutResent(revealable, secretlyKnown);
                    if (toChoose == null) {
                        System.out.println("Hasard");
                        toChoose = revealable.get(new Random().nextInt(revealable.size()));
                    }
                }
            }
        }
        return toChoose;
    }

    /**
     * If the player has no more cards, he must accuse, else, he's got 1/2 chance to use a rumour card
     * and 1/2 chance to accuse a player
     *
     * @param strategyOwner : the computer player who owns the strategy
     * @return 1 is the action is to accuse, 2 if the action is to use a card
     */
    @Override
    public int getAttackAction(ComputerPlayer strategyOwner) {
        final int ACCUSE_PLAYER = 1, REVEAL_CARD = 2;
        // If the player has no more cards, he must accuse
        if (strategyOwner.getCardsUsableForHunt().isEmpty()) {
            return ACCUSE_PLAYER;
        }
        /* 1 chance out of 2 to use a rumour card
         * 1 chance out of 2 to accuse a player*/
        Random random = new Random();
        int choice = random.nextInt(2);
        if (choice == 0) {
            return REVEAL_CARD;
        } else {
            return ACCUSE_PLAYER;
        }
    }

    /**
     * This method is used by the AI to select the next player
     * The player selected is the one who has the less accused the current player
     *
     * @param strategyOwner     the player who uses this strategy
     * @param selectablePlayers List of players who are selectable
     * @return the chosen player to be the next to play
     */
    @Override
    public Player selectNextPlayer(ComputerPlayer strategyOwner, List<Player> selectablePlayers) {
        // Select the player which has the less accused the current player
        Map<Player, Integer> previousAccusers = strategyOwner.getAccusers();
        ArrayList<Player> secretlyKnown = strategyOwner.getSecretlyKnownPlayer();
        if (previousAccusers.isEmpty() && secretlyKnown.isEmpty()) {
            return selectablePlayers.get(new Random().nextInt(selectablePlayers.size()));
        }
        int min = 100; // arbitrary high value
        Player chosenPlayer = null;
        for (Player p : selectablePlayers) {
            if (previousAccusers.containsKey(p)) {
                if (previousAccusers.get(p) < min) {
                    min = previousAccusers.get(p);
                    chosenPlayer = p;
                }
            }
        }
        if (chosenPlayer == null) {
            return selectablePlayers.get(new Random().nextInt(selectablePlayers.size()));
        } else {
            return chosenPlayer;
        }
    }

}
