package projet.Model.cards;

/**
 * The identity card of a player
 * @author Thomas Girod
 */
public class IdentityCard extends Card {
    Identity identity;

    /**
     * Constructor
     * @param identity the identity of the player (witch or villager)
     */
    public IdentityCard(Identity identity) {
        this.identity = identity;
    }

    /**
     * setter of the identity
     * @param identity the new identity
     */
    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    /**
     * the getter of the identity
     * @return the identity
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return identity.toString();
    }
}
