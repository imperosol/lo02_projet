package projet.Model.player;

import projet.Model.cards.RumourCard;

import java.util.ArrayList;

public abstract class Player {
    private final ArrayList<RumourCard> rumourCards;
    private ArrayList<RumourCard> revealedCards;
    private Identity identity;
    private final String name;
    private boolean isRevealed;
    private int points;

    public Player(int nbr_of_cards, String name) {
        this.revealedCards = new ArrayList<>();
        this.name = name;
        this.points = 0;
        this.identity = Identity.WITCH;
        this.rumourCards = new ArrayList<>(nbr_of_cards);
    }

    public String getName() {
        return name;
    }

    public ArrayList<RumourCard> getCard() {
        return rumourCards;
    }

    public Identity getIdentity() {
        return identity;
    }

    public abstract boolean isHuman();

    public void addPoints(int points) {
        this.points += points;
    }

    public void giveCard(RumourCard card) {
        this.rumourCards.add(card);
    }

    public boolean isRevealed() {
        return this.isRevealed;
    }

    public void revealIdentity() {
        this.isRevealed = true;
    }

    public void hideIdentity() {
        this.isRevealed = false;
    }

    public Player attack(ArrayList<Player> revealedPlayers, ArrayList<Player> allPlayers) {
        return this;
    }

    public boolean isWitch() {
        return this.identity == Identity.WITCH;
    }

    public void setIdentity(Identity newIdentity) {
        this.identity = newIdentity;
    }

    public ArrayList<RumourCard> getRevealedCards() {
        return this.revealedCards;
    }
}
