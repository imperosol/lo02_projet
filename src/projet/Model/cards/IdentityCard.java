package projet.Model.cards;

////////////////////////////////////////////////////////////
//Je met quoi là ? C'est des utils pour tout ce qui touche à l'identity c'est ça ?

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
