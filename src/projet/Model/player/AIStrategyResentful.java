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

    private Player getPlayerFromKnownWithResent(ArrayList<Player> secretlyKnown, Map<Player, Integer> previousAccusers) {
        int maxAccusation = -1;
        Player toChoose = null;
        for (Player p : secretlyKnown) {
            if (p.isWitch() && previousAccusers.containsKey(p)) {
                if (previousAccusers.get(p) > maxAccusation) {
                    maxAccusation = previousAccusers.get(p);
                    toChoose = p;
                }
            }
        }
        return toChoose;
    }

    private Player getPlayerFromUnknownWithResent(ArrayList<Player> secretlyKnown, Map<Player, Integer> previousAccusers) {
        int maxAccusation = -1;
        Player toChoose = null;
        for (Player p : previousAccusers.keySet()) {
            if (previousAccusers.get(p) > maxAccusation && !secretlyKnown.contains(p)) {
                maxAccusation = previousAccusers.get(p);
                toChoose = p;
            }
        }
        return toChoose;
    }

    private Player getPlayerFromUnknownWithoutResent(ArrayList<Player> revealable, ArrayList<Player> secretlyKnown) {
        ArrayList<Player> accuseList = new ArrayList<>();
        for (Player p : revealable) {
            if (!secretlyKnown.contains(p)) {
                accuseList.add(p);
            }
        }
        if (accuseList.size() > 0) {
            return accuseList.get(new Random().nextInt(accuseList.size()));
        } else {
            return null;
        }
    }

    @Override
    public Player getPlayerToAccuse(ComputerPlayer strategyUser, Player toExclude) {
        /* the resentful AI always accuse the player who has accused him the most.
         * The players whose identity is known are prioritized over those who are not known */
        ArrayList<Player> revealable = WitchHuntUtils.getRevealablePlayers(strategyUser, strategyUser.game.getPlayers());
        revealable.remove(toExclude);
        ArrayList<Player> secretlyKnown = strategyUser.getSecretlyKnownPlayer();
        Map<Player, Integer> previousAccusers = strategyUser.getAccusers();
        Player toChoose = this.getPlayerFromKnownWithResent(secretlyKnown, previousAccusers);
        if (toChoose == null) {
            toChoose = this.getPlayerFromKnownWithoutResent(secretlyKnown);
            if (toChoose == null) {
                toChoose = this.getPlayerFromUnknownWithResent(secretlyKnown, previousAccusers);
                if (toChoose == null) {
                    toChoose = this.getPlayerFromUnknownWithoutResent(revealable, secretlyKnown);
                    if (toChoose == null) {
                        toChoose = revealable.get(new Random().nextInt(revealable.size()));
                    }
                }
            }
        }
        return toChoose;
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
            if (previousAccusers.containsKey(p)) {
                if (previousAccusers.get(p) < min) {
                    min = previousAccusers.get(p);
                    chosenPlayer = p;
                }
            }
        }
        return chosenPlayer;
    }

}
