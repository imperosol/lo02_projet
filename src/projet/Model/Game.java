package projet.Model;

import org.jetbrains.annotations.NotNull;
import projet.Controller.MainController;
import projet.Model.cards.*;
import projet.Model.player.ComputerPlayer;
import projet.Model.player.HumanPlayer;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A class which correspond to the Game itself :
 * - ask the number of player and AI
 * - start a new round
 * - ask everybody its role
 * - initializing the rumour cards and giving them to all players
 * - end round
 * - count the points of each player and end the game when a player get 5 pts
 */
public class Game {
    private final ArrayList<Player> players;
    private final ArrayList<RumourCard> rumourCards;
    private Player nextPlayer;
    private Player currentPlayer;
    private final int cardPerPlayer;
    private MainController controller = null;
    private Round currentRound;
    private int nbrRounds = 0;

    /**
     * constructor of the class, creates the players and gives them their cards.
     * Finally, it chooses a player randomly to be the player to start the first round
     *
     * @param nbr_humans the number of human players
     * @param nbr_ia     the number of AI players
     */
    public Game(int nbr_humans, int nbr_ia) {
        this.cardPerPlayer = getCardPerPlayer(nbr_humans + nbr_ia);
        this.rumourCards = createRumourCards();
        this.players = createPlayers(nbr_humans, nbr_ia, this.cardPerPlayer);
        Random random = new Random();
        this.nextPlayer = this.players.get(random.nextInt(this.players.size()));
        this.currentPlayer = nextPlayer;
    }

    /**
     * set the controller of the game (for the MVC)
     *
     * @param controller the controller
     */
    public void setController(MainController controller) {
        this.controller = controller;
    }

    /**
     * This method compute the number of card per player knowing the number of player
     *
     * @param nbr_players the number of players
     * @return the number of card per players
     */
    private int getCardPerPlayer(int nbr_players) {
        int card_per_player;
        if (nbr_players == 6) {
            card_per_player = 2;
        } else {
            card_per_player = 7 - nbr_players;
        }
        return card_per_player;
    }

    /**
     * getter of the current player of the round
     *
     * @return current player of the round
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * getter of the index of the current player of the round
     *
     * @return the index of the player in the list of the players who play the game
     */
    public int getCurrentPlayerIndex() {
        return this.players.indexOf(currentPlayer);
    }

    /**
     * Setter of the current player of the round
     *
     * @param player address to the current player of the round
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    /**
     * Creation of an ArrayList containing all the players and the AI
     *
     * @param nbr_humans      address to the number of human players
     * @param nbr_ia          address to the number of AI players
     * @param card_per_player address to the number card per player
     * @return the array list of players
     */
    private @NotNull ArrayList<Player> createPlayers(int nbr_humans, int nbr_ia, int card_per_player) {
        final ArrayList<Player> newPlayers = new ArrayList<>(nbr_humans + nbr_ia);
        for (int i = 0; i < nbr_humans; i++) {
            newPlayers.add(new HumanPlayer("Joueur " + (i + 1) + " (humain)", this));
        }
        for (int i = 0; i < nbr_ia; i++) {
            newPlayers.add(new ComputerPlayer("Joueur " + (i + nbr_humans + 1) + " (IA)", this));
        }
        return newPlayers;
    }

    /**
     * Creation of an array list containing all the rumour cards and shuffle the deck
     *
     * @return the array list of cards shuffled
     */
    private @NotNull ArrayList<RumourCard> createRumourCards() {
        final ArrayList<RumourCard> cards = new ArrayList<>(12);
        Collections.addAll(
                cards, new AngryMob(), new TheInquisition(), new PointedHat(),
                new HookedNose(), new Broomstick(), new Wart(), new DuckingStool(),
                new Cauldron(), new EvilEye(), new Toad(), new BlackCat(), new PetNewt()
        );
        Collections.shuffle(cards);
        return cards;
    }

    /**
     * Distribution of the rumour cards to the players
     * Method called one time at the beginning of each round
     */
    private void distributeRumourCards() {
        for (int i = 0; i < this.cardPerPlayer; ++i) {
            for (Player p : this.players) {
                p.giveCard(this.rumourCards.remove(this.rumourCards.size() - 1));
            }
        }
        this.rumourCards.trimToSize();
    }

    /**
     * Method used to add a rumour card to the discard of the game
     *
     * @param card the card to discard
     */
    public void discardRumourCard(RumourCard card) {
        this.rumourCards.add(card);
    }

    /**
     * Method used for all player to choose either they're villager or witch
     */
    private void assignRoles() {
        for (Player p : this.players) {
            p.chooseIdentity();
        }
    }

    /**
     * getter of the list of players
     *
     * @return all the players in the game
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Function to launch the game and make it run until the end
     */
    public void makeGame() {
        while (!this.isGameEnded()) {
            for (Player p : this.players) {
                if (!p.isHuman()) {
                    System.out.println(p.getGame() + " : stratégie " + p.strategyString());
                }
            }
            this.newRound();
            currentRound.makeRound();
        }
        WitchHuntUtils.displayNotlikethis();
        System.out.println("Le vainqueur est : " + this.getPlayerWithMaxPoints());
    }

    /**
     * assign roles to players, distribute cards and start a new round
     */
    public void newRound() {
        this.currentRound = new Round();
        nbrRounds++;
        this.assignRoles();
        this.distributeRumourCards();
    }

    /**
     * get the discarded cards
     *
     * @return the discarded cards
     */
    public ArrayList<RumourCard> getDiscardedCards() {
        return this.rumourCards;
    }

    /**
     * get the player who has the higher number of points
     *
     * @return the player with the highest number of points
     */
    public Player getPlayerWithMaxPoints() {
        Player maxPlayer = this.players.get(0);
        for (Player p : this.players) {
            if (p.getPoints() > maxPlayer.getPoints()) {
                maxPlayer = p;
            }
        }
        return maxPlayer;
    }

    /**
     * check if the game is ended
     *
     * @return true is the game is finished, else false
     */
    public boolean isGameEnded() {
        for (Player p : this.players) {
            if (p.getPoints() >= 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * get the index of a player in the list of players in the game
     *
     * @param player the player we want to know the index
     * @return the index of this player in the list of players
     */
    public int getPlayerIndex(Player player) {
        return this.players.indexOf(player);
    }

    /**
     * Get the controller of the game (for MVC gui)
     *
     * @return the controller of the game
     */
    public MainController getController() {
        return controller;
    }

    public boolean isCurrentRoundEnded() {
        if (this.currentRound != null)
            return this.currentRound.isRoundEnded();
        else
            return false;
    }

    /**
     * Inner class representing a round
     */
    private class Round {

        /**
         * Check wether the round is finished or not
         *
         * @return true is the round is finished else false
         */
        public boolean isRoundEnded() {
            int revealedPlayers = 0;
            for (Player p : players) {
                if (!p.isRevealed()) {
                    if (revealedPlayers == 0) {
                        revealedPlayers++;
                    } else {
                        return false;
                    }
                }
            }
            return true;
        }

        /**
         * function to launch the execution of a round
         */
        public void makeRound() {
            System.out.println(this.isRoundEnded());
            while (!this.isRoundEnded()) {
                this.makeTurn();
            }
            System.out.println("Fin du round");
            this.endRound();
        }

        /**
         * function to launch a new turn
         */
        public void makeTurn() {
            System.out.println("C'est au tour de " + nextPlayer.getName());
            controller.addLog("C'est au tour de " + nextPlayer.getName());
            controller.setCardsGUI();
            nextPlayer = nextPlayer.playerTurn();
            currentPlayer = nextPlayer;
        }

        /**
         * function to end the round. All the players hide their identity
         * and all cards are returned to the discard
         */
        public void endRound() {
            for (Player p : players) {
                p.discardAllCards();
                p.hideIdentity();
            }
        }
    }
}
