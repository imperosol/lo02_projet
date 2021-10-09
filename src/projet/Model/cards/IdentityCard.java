package projet.Model.cards;

public class IdentityCard extends Card {
    Identity identity;

    public IdentityCard(Identity identity) {
        this.identity = identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Identity getIdentity() {
        return identity;
    }

    @Override
    public String toString() {
        return "" + identity;
    }
}
