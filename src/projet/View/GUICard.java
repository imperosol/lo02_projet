package projet.View;

/**
 * Tuple class to represent the place of a card in the gui
 */
public class GUICard {
    public final int type;
    public final int player;
    public final int index;

    public GUICard(int player, int index, int type) {
        this.type = type;
        this.player = player;
        this.index = index;
    }
}
