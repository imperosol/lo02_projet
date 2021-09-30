package projet.Model.player;

import projet.Model.Game;
import projet.Model.utils.WitchHuntUtils;

import java.util.Random;

/**
 * @author thgir
 */
public class ComputerPlayer extends Player {
    public ComputerPlayer(int nbr_of_cards, String name, Game game) {
        super(nbr_of_cards, name, game);
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public Player defendAgainstAccusation(Player accuser) {
        // TODO : implement method to defend against accusation for AI
        return null;
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
        System.err.println("Pas encore implémenté");
        System.err.println("Le programme va s'arrêter");
        System.exit(0);
        return this;
    }
}
