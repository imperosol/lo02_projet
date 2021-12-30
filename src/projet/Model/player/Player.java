package projet.Model.player;

import projet.Model.Game;
import projet.Model.cards.Identity;
import projet.Model.cards.IdentityCard;
import projet.Model.cards.RumourCard;

import java.util.ArrayList;

public abstract class Player {
    protected final ArrayList<RumourCard> rumourCards;
    protected final ArrayList<RumourCard> revealedCards;
    protected Thread wait;
    private final IdentityCard identity;
    private final String name;
    private boolean isRevealed;
    private int points;
    Game game; // game is not private cause HumanPlayer and ComputerPlayer must access it
    private Player accuser = null;

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
        this.game.getController().addLog(this + " révèle son identité : " + this.printIdentity());
    }

    public void hideIdentity() {
        this.isRevealed = false;
    }

    public abstract Player getPlayerToAccuse(Player toExclude);

    public abstract void lookAtIdentity(Player lookedPlayer);

    public Player accuse(Player accusedPlayer) {
        this.game.getController().addLog(this + " accuse " + accusedPlayer);
        accusedPlayer.setAccuser(this);
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
            this.rumourCards.remove(card);
            this.game.discardRumourCard(card);
        } catch (Exception e) {
            System.out.println("La carte n'est pas dans la main du joueur");
        }
    }

    public String strategyString() {
        return "";
    }

    public void discardAllCards() {
        while (this.rumourCards.size() > 0) {
            this.discardCard(this.rumourCards.remove(0));
        }
        while (this.revealedCards.size() > 0) {
            this.discardCard(this.revealedCards.remove(0));
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

    public Player revealIdentityAfterAccusation(Player accuser) {
        this.revealIdentity();
        if (this.isWitch()) {
            accuser.addPoints(1);
            return accuser;
        } else {
            return this;
        }
    }

    public boolean isCurrentPlayer() {
        return this == this.game.getCurrentPlayer();
    }

    public abstract Player selectNextPlayer(ArrayList<Player> selectablePlayers);

    public void resumeExecution() {
        this.wait.interrupt();
    }

    public Player getAccuser() {
        return accuser;
    }

    public void setAccuser(Player accuser) {
        this.accuser = accuser;
    }

    public boolean canBeNextPlayer() {
        return !this.isWitch() || !this.isRevealed;
    }

    @Override
    public String toString() {
        return name;
    }
}
