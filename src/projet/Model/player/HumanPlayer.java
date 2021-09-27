package projet.Model.player;

public class HumanPlayer extends Player {
    public HumanPlayer(int nbr_of_cards, String name) {
        super(nbr_of_cards, name);
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}
