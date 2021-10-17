package projet.Model.player;

import projet.Model.Game;
import projet.Model.cards.Identity;
import projet.Model.cards.IdentityCard;
import projet.Model.cards.RumourCard;

import java.util.ArrayList;

public abstract class Player {
    protected final ArrayList<RumourCard> rumourCards;
    protected final ArrayList<RumourCard> revealedCards;
    private final IdentityCard identity;
    private final String name;
    private boolean isRevealed;
    private int points;
    Game game; // game is not private cause HumanPlayer and ComputerPlayer must access it

    public Player(int nbr_of_cards, String name, Game game) {
        this.revealedCards = new ArrayList<>();
        this.name = name;
        this.points = 0;
        this.identity = new IdentityCard(Identity.WITCH);
        this.rumourCards = new ArrayList<>(nbr_of_cards);
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public abstract Player playerTurn();

    public String getName() {
        return name;
    }

    public ArrayList<RumourCard> getCards() {
        return rumourCards;
    }

    public String printIdentity() {
        return identity.toString();
    }

    public abstract boolean isHuman();

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return this.points;
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

    public Player denounce(Player accusedPlayer) {
        return accusedPlayer.defendAgainstAccusation(this);
    }

    public abstract Player defendAgainstAccusation(Player accuser);

    public boolean isWitch() {
        return this.identity.getIdentity() == Identity.WITCH;
    }

    public void setIdentity(Identity newIdentity) {
        this.identity.setIdentity(newIdentity);
    }

    public abstract void chooseIdentity();

    public ArrayList<RumourCard> getRevealedCards() {
        return this.revealedCards;
    }

    public ArrayList<RumourCard> getDiscardedCards() {
        return this.game.getDiscardedCards();
    }

    public void revealCard(RumourCard card) {
        try {
            this.revealedCards.add(this.rumourCards.remove(this.rumourCards.indexOf(card)));
        } catch (Exception e) {
            System.out.println(this.name + " n'a pas cette carte en main");
        }
    }

    public void revealCard(int cardIndex) {
        if (cardIndex < this.rumourCards.size() && cardIndex >= 0) {
            // if exist, remove the card from the rumourCard list and add it in the revealedCard list
            this.revealedCards.add(this.rumourCards.remove(cardIndex));
        } else {
            System.out.println("La carte n'est pas dans la main du joueur");
        }
    }

    public void hideCard(RumourCard card) {
        try {
            // if exist, remove the card from the rumourCard list and add it in the revealedCard list
            this.rumourCards.add(this.revealedCards.remove(this.revealedCards.indexOf(card)));
        } catch (Exception e) {
            System.out.println("La carte n'appartient pas au joueur");
        }
    }

    public void hideCard(int cardIndex) {
        try {
            // if exist, remove the card from the rumourCard list and add it in the revealedCard list
            this.rumourCards.add(this.revealedCards.remove(cardIndex));
        } catch (Exception e) {
            System.out.println("La carte n'appartient pas au joueur");
        }
    }

    public void discardCard(RumourCard card) {
        try {
            /* if exist, remove the card from the rumourCard list and add it
             in the discard of the game */
            RumourCard toRemove = this.rumourCards.remove(this.rumourCards.indexOf(card));
            this.game.discardRumourCard(toRemove);
        } catch (Exception e) {
            System.out.println("La carte n'est pas dans la main du joueur");
        }
    }

    protected ArrayList<RumourCard> getCardsUsableForHunt() {
        ArrayList<RumourCard> usable = new ArrayList<>(this.rumourCards.size());
        for (RumourCard card : this.rumourCards) {
            if (card.isHuntEffectUsable(this)) {
                usable.add(card);
            }
        }
        return usable;
    }

    protected ArrayList<RumourCard> getCardsUsableForWitch() {
        ArrayList<RumourCard> usable = new ArrayList<>(this.rumourCards.size());
        for (RumourCard card : this.rumourCards) {
            if (card.isWitchEffectUsable(this)) {
                usable.add(card);
            }
        }
        return usable;
    }

    protected Player revealIdentityAfterAccusation(Player accuser) {
        Player nextPlayer;
        this.revealIdentity();
        if (this.isWitch()) {
            accuser.addPoints(1);
            nextPlayer = accuser;
        } else {
            nextPlayer = this;
        }
        return nextPlayer;
    }

    public abstract Player selectNextPlayer(ArrayList<Player> selectablePlayers);
}
