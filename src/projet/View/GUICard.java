package projet.View;

/**
 * Tuple class to represent the place of a card in the gui
 * All attributes are public and immutable
 */
public class GUICard {
    public final int type;
    public final int player;
    public final int index;

    /**
     * Constructor
     * @param player the index of the player who owns the card in the list of players
     * @param index the index of the card in the hand of the player
     * @param type whether the card is in the hand of a player (RUMOUR_CARD=1), revealed (REVEALED_CARD=2) or discarded (DISCARDED_CARD=3)
     */
    public GUICard(int player, int index, int type) {
        this.type = type;
        this.player = player;
        this.index = index;
    }
}
