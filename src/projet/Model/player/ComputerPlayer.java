package projet.Model.player;

import org.jetbrains.annotations.NotNull;
import projet.Model.Game;
import projet.Model.cards.Identity;
import projet.Model.cards.RumourCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author thgir
 */
public class ComputerPlayer extends Player {
    private Map<Player, Integer> nbrOfAccusers;
    AIStrategy strategy;

    public ComputerPlayer(int nbr_of_cards, String name, Game game) {
        super(nbr_of_cards, name, game);
        this.nbrOfAccusers = new HashMap<>();
        this.strategy = new AIStrategyResentful();
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    private void rememberAccusation(Player accuser) {
        if (accuser.isRevealed()) {
            return;
        }
        if (nbrOfAccusers.containsKey(accuser)) {
            nbrOfAccusers.put(accuser, nbrOfAccusers.get(accuser) + 1);
        } else {
            nbrOfAccusers.put(accuser, 1);
        }
    }

    public Map<Player, Integer> getAccusers() {
        return nbrOfAccusers;
    }

    @Override
    public Player defendAgainstAccusation(Player accuser) {
        // TODO : implement method to defend against accusation for AI
        this.rememberAccusation(accuser); // remember who accused the player, in order to implement strategies
        ArrayList<RumourCard> usableWitch = this.getCardsUsableForWitch();
        Player nextPlayer;
        if (usableWitch.size() == 0) {
            nextPlayer = this.revealIdentityAfterAccusation(accuser);
        } else if (!this.isWitch()) {
            Random random = new Random();
            int choice = random.nextInt(5);
            if (choice >= this.getCards().size()) {
                nextPlayer = this.strategy.applyWitchEffect(this, usableWitch);
            } else {
                nextPlayer = this.revealIdentityAfterAccusation(accuser);
            }
        } else {
            nextPlayer = this.strategy.applyWitchEffect(this, usableWitch);
        }
        return nextPlayer;
    }

    @NotNull
    private Player revealIdentityAfterAccusation(Player accuser) {
        Player nextPlayer;
        this.revealIdentity();
        if (this.isWitch()) {
            accuser.addPoints(1);
            nextPlayer = accuser;
        } else {
            nextPlayer = this;
        }
        return nextPlayer;
    }

    @Override
    public void chooseIdentity() {
        Random random = new Random();
        int choice = random.nextInt(3);
        // 1 chance out of 3 for the IA to be a witch
        // 2 chances out of 3 to be a villager
        // Those numbers are totally arbitrary
        if (choice == 1) {
            this.setIdentity(Identity.WITCH);
        } else {
            this.setIdentity(Identity.VILLAGER);
        }
    }

    @Override
    public Player playerTurn() {
        // TODO : implémenter comportement IA pour un tour de jeu
        Random random = new Random();
        int choice = random.nextInt(2);
        if (choice == 1) {
            Player toAccuse = this.strategy.getPlayerToAccuse(this);
            return this.denounce(toAccuse);
        } else {
            return this;
        }
    }
}
