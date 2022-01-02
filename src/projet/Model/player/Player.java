package projet.Model.player;

import projet.Model.Game;
import projet.Model.cards.Identity;
import projet.Model.cards.IdentityCard;
import projet.Model.cards.RumourCard;

import java.util.ArrayList;

/**
 * The abstract class representing a player.
 * The classes {@link ComputerPlayer} and {@link HumanPlayer} inherit of this class
 * @see HumanPlayer
 * @see ComputerPlayer
 * @author Thomas Girod
 */
public abstract class Player {
    /**
     * The rumours card the player has in his hand
     */
    protected final ArrayList<RumourCard> rumourCards;
    /**
     * The rumour cards the player possesses
     */
    protected final ArrayList<RumourCard> revealedCards;
    /**
     * The identity of this player (witch or villager)
     * @see IdentityCard
     * @see Identity
     */
    private final IdentityCard identity;
    /**
     * a String with the name of the player
     */
    private final String name;
    /**
     * A boolean describing if the identity of this player has been revealed during the current round
     */
    private boolean isRevealed;
    /**
     * The points this player currently has
     */
    private int points = 0;
    /**
     * the game in which the player participates
     * @see Game
     */
    Game game; // game is not private cause HumanPlayer and ComputerPlayer must access it
    /**
     * the last player who has accused this player
     */
    private Player accuser = null;

    /**
     * Constructor
     * @param name a String with the name of this player
     * @param game the Game in which this player participates
     */
    public Player(String name, Game game) {
        this.revealedCards = new ArrayList<>();
        this.name = name;
        this.identity = new IdentityCard(Identity.WITCH);
        this.rumourCards = new ArrayList<>();
        this.game = game;
    }

    /**
     * getter of the game of this player
     * @return the Game instance of the game in which this player participates
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * method to execute a turn of the player
     * @return the player who is next to play, according to the events occurring during this turn
     */
    public abstract Player playerTurn();

    /**
     * getter of the name of this player
     * @return a String containing the name of this player
     */
    public String getName() {
        return name;
    }

    /**
     * getter of the rumour cards of this player
     * @return an ArrayList with the non revealed card of this player
     */
    public ArrayList<RumourCard> getCards() {
        return rumourCards;
    }

    /**
     * method to get the toString() value of the identity card of this player
     * @return a String with the toString() value of the identity card of this player
     * @see IdentityCard
     */
    public String printIdentity() {
        return identity.toString();
    }

    /**
     * method to know if this player is a human or a computer
     * @return true if this player is an instance of HumanPlayer, else false
     */
    public abstract boolean isHuman();

    /**
     * method to add points to the current player
     * @param points the number of points to add (can be a negative value)
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * getter of the number
     * @return an int with the number of points this player has
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Method to add a rumour card in the hand of this player
     * When using this method, be careful to remove the card from the place it previously was,
     * in order to not have the same rumour car in two different places, which will for sure break the game
     * @param card the rumour card to give
     */
    public void giveCard(RumourCard card) {
        this.rumourCards.add(card);
    }

    /**
     * getter of whether this player is revealed or not
     * @return true if the identity this player has been revealed, else false
     */
    public boolean isRevealed() {
        return this.isRevealed;
    }

    /**
     * reveal the identity of this player
     */
    public void revealIdentity() {
        this.isRevealed = true;
        this.game.getController().addLog(this + " révèle son identité : " + this.printIdentity());
    }

    /**
     * hide the identity of this player
     */
    public void hideIdentity() {
        this.isRevealed = false;
    }

    /**
     * method to choose a player to accuse, during the first phase of the turn
     * @param toExclude a player that can not be chosen
     * @return the player to accuse, according to the choice of the player
     */
    public abstract Player getPlayerToAccuse(Player toExclude);

    /**
     * method to choose a player to accuse, during the first phase of the turn
     * @return the player to accuse, according to the choice of the player
     */
    public Player getPlayerToAccuse() {
        return this.getPlayerToAccuse(null);
    }

    /**
     * method to view the identity of another player
     * @param lookedPlayer the player to view the identity of
     */
    public abstract void lookAtIdentity(Player lookedPlayer);

    /**
     * accuse another player
     * @param accusedPlayer the player to accuse
     * @return the player who is next to play, according to the reaction of the accused player
     */
    public Player accuse(Player accusedPlayer) {
        this.game.getController().addLog(this + " accuse " + accusedPlayer);
        accusedPlayer.setAccuser(this);
        return accusedPlayer.defendAgainstAccusation(this);
    }

    /**
     * defend against the accusation of another player
     * @param accuser the player who accused this player
     * @return the player who is next to play according to the performed actions during the defense
     */
    public abstract Player defendAgainstAccusation(Player accuser);

    /**
     * getter of whether this player is a witch or not
     * @return true if this player is a witch, else false
     */
    public boolean isWitch() {
        return this.identity.getIdentity() == Identity.WITCH;
    }

    /**
     * setter of the identity of this player
     * @param newIdentity an {@link Identity} value corresponding to new identity
     * @see Identity
     */
    public void setIdentity(Identity newIdentity) {
        this.identity.setIdentity(newIdentity);
    }

    /**
     * make a choice to choose an identity
     * @see Identity
     */
    public abstract void chooseIdentity();

    /**
     * the getter of the revealed cards of this player
     * @return an ArrayList with the revealedCard of this player
     */
    public ArrayList<RumourCard> getRevealedCards() {
        return this.revealedCards;
    }

    /**
     * the getter of the discarded cards of this player
     * @return an ArrayList with the discarded cards of this player
     */
    public ArrayList<RumourCard> getDiscardedCards() {
        return this.game.getDiscardedCards();
    }

    /**
     * Reveal a card from the hand of this player, if he has this card
     * @param card the card to reveal
     */
    public void revealCard(RumourCard card) {
        try {
            this.revealedCards.add(this.rumourCards.remove(this.rumourCards.indexOf(card)));
        } catch (Exception e) {
            System.out.println(this.name + " n'a pas cette carte en main");
        }
    }

    /**
     * put a revealed card into the hand of this player, if this card is in the list of revealed cards
     * @param card the card to hide
     */
    public void hideCard(RumourCard card) {
        try {
            // if exist, remove the card from the rumourCard list and add it in the revealedCard list
            this.rumourCards.add(this.revealedCards.remove(this.revealedCards.indexOf(card)));
        } catch (Exception e) {
            System.out.println("La carte n'appartient pas au joueur");
        }
    }

    /**
     * put a revealed card into the hand of this player, if this card is in the list of revealed cards
     * @param cardIndex the index of the card to hide in the List of revealed cards
     */
    public void hideCard(int cardIndex) {
        try {
            // if exist, remove the card from the rumourCard list and add it in the revealedCard list
            this.rumourCards.add(this.revealedCards.remove(cardIndex));
        } catch (Exception e) {
            System.out.println("La carte n'appartient pas au joueur");
        }
    }

    /**
     * remove a card from the hand of this player and puts it in the discard
     * of the game in which the player participates, if this card is in his hand
     * @param card the card to discard
     */
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

    /**
     * getter of the string value of the strategy of this player
     * @return a String with the string value of the strategy if the player has a strategy, else an empty String
     */
    public String strategyString() {
        return "";
    }

    /**
     * discard all the cards (both hidden and revealed cards) of this player
     * method use at the end of each Round
     */
    public void discardAllCards() {
        while (!this.rumourCards.isEmpty()) {
            this.discardCard(this.rumourCards.remove(0));
        }
        while (!this.revealedCards.isEmpty()) {
            this.discardCard(this.revealedCards.remove(0));
        }
    }

    /**
     * creates and return an ArrayList of all cards owned by this player whose hunt effect can be used
     * @return an ArrayList of all cards owned by this player whose hunt effect can be used
     */
    protected ArrayList<RumourCard> getCardsUsableForHunt() {
        ArrayList<RumourCard> usable = new ArrayList<>(this.rumourCards.size());
        for (RumourCard card : this.rumourCards) {
            if (card.isHuntEffectUsable(this)) {
                usable.add(card);
            }
        }
        return usable;
    }
    /**
     * creates and return an ArrayList of all cards owned by this player whose witch effect can be used
     * @return an ArrayList of all cards owned by this player whose witch effect can be used
     */
    protected ArrayList<RumourCard> getCardsUsableForWitch() {
        ArrayList<RumourCard> usable = new ArrayList<>(this.rumourCards.size());
        for (RumourCard card : this.rumourCards) {
            if (card.isWitchEffectUsable(this)) {
                usable.add(card);
            }
        }
        return usable;
    }

    /**
     * Reveal the identity of this player.
     * Method to use when this player is accused and choose to reveal his identity
     * @param accuser the player who accused this player
     * @return the player who is next to play, according to the identity
     * of this player : if he is a villager, he takes the next turn,
     * if he is a witch, the accuser does
     */
    public Player revealIdentityAfterAccusation(Player accuser) {
        this.revealIdentity();
        if (this.isWitch()) {
            accuser.addPoints(1);
            return accuser;
        } else {
            return this;
        }
    }

    /**
     * getter of whether this player is the current player who is to make an action
     * @return true is this player is the current player, else false
     */
    public boolean isCurrentPlayer() {
        return this == this.game.getCurrentPlayer();
    }

    /**
     * make the choice to select the next player, when a rumour card asks for it
     * @param selectablePlayers a list of players from which the next player can be selected
     * @return the player who is next to play, according to the choice of this player
     */
    public abstract Player selectNextPlayer(ArrayList<Player> selectablePlayers);

    /**
     * getter of the last player who accused this player
     * @return the last player who accused this one
     */
    public Player getAccuser() {
        return accuser;
    }

    /**
     * setter of the last player who accused this player
     * @param accuser the last player who accused this one
     */
    public void setAccuser(Player accuser) {
        this.accuser = accuser;
    }

    /**
     * getter of whether this player can take the next turn or not
     * @return true if this player can take the nect turn, else false
     */
    public boolean canBeNextPlayer() {
        return !this.isWitch() || !this.isRevealed;
    }

    /**
     * {@inheritDoc}
     * @return a String with the name of this player
     */
    @Override
    public String toString() {
        return name;
    }
}
