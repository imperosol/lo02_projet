package projet.Controller;

/**
 * the phase in which the game currently is
 */
public enum GamePhase {
    /**
     * when a player can either accuse another player or chose a card to use its hunt effect
     */
    ATTACK,
    /**
     * When a player has been accused and can either reveal its identity
     * or chose a card to use its witch effect
     */
    DEFENSE,
    /**
     * When a player makes the actions needed to perform the hunt effect of the card he chose
     */
    ATTACK_COMPLEMENTARY,
    /**
     * When a player makes the actions needed to perform the witch effect of the card he chose
     */
    DEFENSE_COMPLEMENTARY,
    /**
     * phase specific to the effect of the Ducking Stool card
     */
    DUCKING_STOOL
}
