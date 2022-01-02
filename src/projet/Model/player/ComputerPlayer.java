package projet.Model.player;

import projet.Model.Game;
import projet.Model.cards.Identity;
import projet.Model.cards.RumourCard;

import java.util.*;

/**
 * Class representing an AI player
 * Inherits from {@link Player}
 * @see Player
 * @author Thomas Girod
 */
public final class ComputerPlayer extends Player {
    private final Map<Player, Integer> nbrOfAccusers;
    private final ArrayList<Player> secretlyKnownPlayer;
    AIStrategy strategy;


    /**
     * Constructor
     * @param name the name of this player
     * @param game the {@link Game} instance of the game in which this player participates
     */
    public ComputerPlayer(String name, Game game) {
        super(name, game);
        this.nbrOfAccusers = new HashMap<>();
        this.secretlyKnownPlayer = new ArrayList<>();
        int strat = new Random().nextInt(2);
        if (strat == 0)
            this.strategy = new AIStrategyResentful();
        else
            this.strategy = new AIStrategyAggressive();
    }

    /**
     * {@inheritDoc}
     * @return false
     */
    @Override
    public boolean isHuman() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayerToAccuse(Player toExclude) {
        return this.strategy.getPlayerToAccuse(this, toExclude);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lookAtIdentity(Player lookedPlayer) {
        System.out.println(lookedPlayer + ", " + this + " connait à présent votre identité");
        this.secretlyKnownPlayer.add(lookedPlayer);
    }

    /**
     * get a String describing the strategy of this AI (aggressive or resentful).
     * @return "Aggressive" if this AI is aggressive, "Resentful" if this AI is resentful
     */
    public String strategyString() {
        if (this.strategy instanceof AIStrategyAggressive) {
            return "Aggressive";
        } else {
            return "Resentful";
        }
    }

    /**
     * Store a player in a Map of player who previously accused this one with the number of accusations
     * @param accuser the player to remember
     */
    private void rememberAccusation(Player accuser) {
        if (accuser.isRevealed()) {
            return;
        }
        if (nbrOfAccusers.containsKey(accuser)) {
            nbrOfAccusers.put(accuser, nbrOfAccusers.get(accuser) + 1);
        } else {
            nbrOfAccusers.put(accuser, 1);
        }
    }

    /**
     * update the List of players whom this AI knows the identity by removing
     * the players whose identity has been revealed since their identity was discovered
     */
    private void updateKnownPlayerList() {
        // if players were known secretly but have been revealed in the mean time
        // then they are no more secret
        this.secretlyKnownPlayer.removeIf(Player::isRevealed);
    }

    /**
     * getter of the Map of the previous accusers of this AI
     * @return the Map of the previous accusers of this AI
     */
    public Map<Player, Integer> getAccusers() {
        return nbrOfAccusers;
    }

    /**
     * getter of the ArrayList of players whom identity is known
     * @return the ArrayList of players whom identity is known
     */
    public ArrayList<Player> getSecretlyKnownPlayer() {
        return secretlyKnownPlayer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player defendAgainstAccusation(Player accuser) {
        this.rememberAccusation(accuser); // remember who accused the player, in order to implement strategies
        ArrayList<RumourCard> usableWitch = this.getCardsUsableForWitch();
        Player nextPlayer;
        if (usableWitch.size() == 0) {
            // if the player cannot use a card, he reveals his identity
            nextPlayer = this.revealIdentityAfterAccusation(accuser);
            System.out.println(this.getName() + " révèle son identité : " + this.printIdentity());
        } else if (!this.isWitch()) {
            /* If the player is a villager a die 0-4 is launched.
             * If the result is greater than the number of cards, he reveals his identity
             * Else he reveals a card and use its witch effect
             * Example : the player has 3 cards in hand and the randomly generated number is 4 : reveal identity
             *           the player has 2 cards in hand and the randomly generated number is 1 : reveal card
             *           the player has 3 cards in hand and the randomly generated number is 3 : reveal card */
            int choice = new Random().nextInt(5);
            if (choice >= this.rumourCards.size()) {
                nextPlayer = this.defendWithWitch(usableWitch, accuser);
            } else {
                nextPlayer = this.revealIdentityAfterAccusation(accuser);
                System.out.println(this.getName() + " révèle son identité : " + this.printIdentity());
            }
        } else {
            /* If the player is a witch and has at least one usable card, he has no choice
            * but to defend himself against his accuser by revealing a rumour card */
            nextPlayer = this.defendWithWitch(usableWitch, accuser);
        }
        return nextPlayer;
    }

    /**
     * Choose a witch card and use it to defend against an accusation
     * @param usableWitch an ArrayList containing the cards this player can use
     * @param accuser the Player who accused this AI
     * @return the player who is next to play according to the effect of the used witch card
     */
    private Player defendWithWitch(ArrayList<RumourCard> usableWitch, Player accuser) {
        // TODO : résolution aléatoire, à améliorer
        int cardIndex = new Random().nextInt(usableWitch.size());
        RumourCard chosenCard = usableWitch.get(cardIndex);
        this.revealCard(chosenCard);
        System.out.println(this.getName() + " se défend avec le witch de  " + chosenCard);
        return chosenCard.witchEffect(this, this.game.getPlayers(), accuser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player selectNextPlayer(ArrayList<Player> selectablePlayers) {
        return this.strategy.selectNextPlayer(this, selectablePlayers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chooseIdentity() {
        Random random = new Random();
        int choice = random.nextInt(3);
        // 1 chance out of 3 for the IA to be a witch
        // 2 chances out of 3 to be a villager
        // Those numbers are totally arbitrary
        if (choice == 1) {
            this.setIdentity(Identity.WITCH);
        } else {
            this.setIdentity(Identity.VILLAGER);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Player playerTurn() {
        this.updateKnownPlayerList();
        int choice = this.strategy.getAttackAction(this);
        ArrayList<RumourCard> usableCards = this.getCardsUsableForHunt();
        if (choice == 1 || usableCards.size() == 0) { // accuse player
            Player toAccuse = this.getPlayerToAccuse(null);
            System.out.println(this.getName() + " accuse " + toAccuse.getName());
            return this.accuse(toAccuse);
        } else { // reveal card
            // TODO : entièrement aléatoire, à améliorer
            int cardIndex = new Random().nextInt(usableCards.size());
            RumourCard card = usableCards.get(cardIndex);
            this.revealCard(card);
            System.out.println(this.getName() + " utilise le hunt de " + card);
            return card.huntEffect(this, this.game.getPlayers());
        }
    }
}
