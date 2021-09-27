package projet.Model.player;

/**
 * @author thgir
 */
public class ComputerPlayer extends Player {
    public ComputerPlayer(int nbr_of_cards, String name) {
        super(nbr_of_cards, name);
    }

    @Override
    public boolean isHuman() {
        return false;
    }
}
