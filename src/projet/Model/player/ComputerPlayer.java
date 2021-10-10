package projet.Model.player;

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
        int strat = new Random().nextInt(2);
        if (strat == 0)
            this.strategy = new AIStrategyResentful();
        else
            this.strategy = new AIStrategyAggressive();
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
        this.rememberAccusation(accuser); // remember who accused the player, in order to implement strategies
        ArrayList<RumourCard> usableWitch = this.getCardsUsableForWitch();
        Player nextPlayer;
        if (usableWitch.size() == 0) {
            // if the player cannot use a card, he reveals his identity
            nextPlayer = this.revealIdentityAfterAccusation(accuser);
        } else if (!this.isWitch()) {
            /* If the player is a villager a die 5 is launched.
            * The result determines if he reveals his identity or defends with a rumour card
            * The less the player has cards in hand, the more likely he is to reveal his identity */
            int choice = new Random().nextInt(5);
            if (choice >= this.getCards().size()) {
                nextPlayer = this.strategy.applyWitchEffect(this, usableWitch);
            } else {
                nextPlayer = this.revealIdentityAfterAccusation(accuser);
            }
        } else {
            /* If the player is a witch and has at least one usable card, he has no choice
            * but to defend himself against his accuser by revealing a rumour card */
            nextPlayer = this.strategy.applyWitchEffect(this, usableWitch);
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
        int choice = this.strategy.getAttackAction(this);
        if (choice == 1) { // accuse player
            Player toAccuse = this.strategy.getPlayerToAccuse(this);
            return this.denounce(toAccuse);
        } else { // reveal card
            return this;
        }
    }
}
