package projet.Model;

import org.jetbrains.annotations.NotNull;
import projet.Model.cards.*;
import projet.Model.player.ComputerPlayer;
import projet.Model.player.HumanPlayer;
import projet.Model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Game {
    private final ArrayList<Player> players;//Initialisation of the list of player
    private final ArrayList<RumourCard> rumourCards;//Initialisation of the list of rumour cards
    private Player nextPlayer; //Initialisation of a var containing the nextPlayer
    private final int cardPerPlayer; //Initialisation of the int containing the number of card per player

    public Game(int nbr_players, int nbr_ia) {
        //construction of the Game class with the number of physical players and the number of IA players
        this.cardPerPlayer = getCardPerPlayer(nbr_players); /*calculation of the number of card required
        according to the number of players*/
        this.rumourCards = createRumourCards(); /*Randomisation of the rumour cards that will be given to
        the players*/
        this.players = createPlayers(nbr_players, nbr_ia, this.cardPerPlayer);
        //Giving the cards to each player
        Random randomSeed = new Random();
        ////////////////////////////////////////////////////////////////////////////////
        //Random seed ?
        this.nextPlayer = this.players.get(randomSeed.nextInt(nbr_players));
        //First player chosen randomly
    }


    private int getCardPerPlayer(int nbr_players) {
        //Calculation of the number of card to give to each player
        int card_per_player;
        if (nbr_players == 6) { //If there's 6 players, give 2 cards to each of them
            card_per_player = 2;
        } else { //Else, give them a number of cards equal to 7 - the number of players
            card_per_player = 7 - nbr_players;
        }
        return card_per_player;
    }

    private @NotNull ArrayList<Player> createPlayers(int nbr_players, int nbr_ia, int card_per_player) {
        final ArrayList<Player> newPlayers = new ArrayList<>(nbr_players);
        //Creation of a list containing all the players and the number of cards that they need to
        //have at the beginning of the game
        for (int i = 0; i < nbr_ia; i++) {
            newPlayers.add(new ComputerPlayer(card_per_player, "Joueur " + i + " (IA)", this));
        }
        for (int i = nbr_ia; i < nbr_players; i++) {
            newPlayers.add(new HumanPlayer(card_per_player, "Joueur " + i + " (humain)", this));
        }
        return newPlayers;
    }

    private @NotNull ArrayList<RumourCard> createRumourCards() {
        //Creation of a list containing all the cards and shuffle them
        final ArrayList<RumourCard> cards = new ArrayList<>(12);
        Collections.addAll(
                cards, new AngryMob(), new TheInquisition(), new PointedHat(),
                new HookedNose(), new Broomstick(), new Wart(), new DuckingStool(),
                new Cauldron(), new EvilEye(), new Toad(), new BlackCat(), new PetNewt()
        );
        Collections.shuffle(cards);
        return cards;
    }

    private void distributeRumourCards() {
        //distribution of the Rumour Cards to each players
        for (int i = 0; i < this.cardPerPlayer; ++i) {
            for (Player p : this.players) {
                p.giveCard(this.rumourCards.remove(this.rumourCards.size() - 1));
            }
        }
        this.rumourCards.trimToSize();
        //////////////////////////////////////////////////////////////////////
        //TrimToSize ?
    }

    public void discardRumourCard(RumourCard card) {
        //Function used when a card needs to be discarded
        this.rumourCards.add(card);
    }

    private void assignRoles() {
        //Function used when the players need to choose their identity
        for (Player p : this.players) {
            p.chooseIdentity();
        }
    }

    ////////////////////////////////////////////////////////////////////
    //Pas compris ce que ça fait en dessous
    public ArrayList<Player> getPlayers() {
        return this.players;
    }


    public void makeGame() {
        while (!this.isGameEnded()) { //While the game is not finish (= no one win)
            this.distributeRumourCards(); //Distribute the Rumour Cards
            this.assignRoles(); //Ask each player their identity
            Round currentRound = new Round(); /////////////////////////////////////////////////////////
            currentRound.makeRound(); //Start a round
        }
    }

    /////////////////////////////////////////////////////////////////////////
    //Une autre liste contenant les discarded cards ?
    public ArrayList<RumourCard> getDiscardedCards() {
        return this.rumourCards;
    }

    public boolean isGameEnded() {
        for (Player p : this.players) { //The game is ended when a player has 5 points
            if (p.getPoints() >= 5) {
                return true; //return of a boolean when a player reaches 5
            }
        }
        return false;
        ////////////////////////////////////////////////////////////////////
        //Pourquoi tu return false à la fin ? C'est pas juste si il y a pas eu de true avant ?
    }

    private class Round {
        private final ArrayList<RumourCard> discard;
/////////////////////////////////////////////////////////////////////////////////
        //On discard les card au début du round ?
        public Round() {
            this.discard = rumourCards;
        }
//////////////////////////////////////////////////////////////////////////////
        public boolean isRoundEnded() { //le round est fini quand au moins 2 joueurs sont révélés ?
            int revealedPlayers = 0;
            for (Player p : players) {
                if (p.isRevealed()) {
                    if (revealedPlayers == 0) {
                        revealedPlayers++;
                    } else {
                        return true;
                    }
                }
            }//Pareil, pourquoi un false à la fin d'un for ?
            return false;
        }

        public void makeRound() {
            while (!this.isRoundEnded()) { //While the round is not over,
                this.makeTurn();//let another player play
            }
            this.endRound();
        }

        public void makeTurn() {
            //When it's the turn of player A, the console tells all the players that it's the turn of player A
            System.out.println("Yo");
            System.out.println("C'est au tour de " + nextPlayer.getName()); //recapture of the name of the
            //next player
            nextPlayer = nextPlayer.playerTurn();
        }
//////////////////////////////////////////////////////////////////////////////
        //pas compris en dessous
        public void endRound() {
            for (Player p : players) {
                p.hideIdentity();
                for (RumourCard hiddenCard : p.getCards()) {
                    p.discardCard(hiddenCard);
                }
                for (RumourCard revealedCard : p.getRevealedCards()) {
                    p.discardCard(revealedCard);
                }
            }
        }
    }
}
