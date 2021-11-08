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
    private final ArrayList<Player> players;
    private final ArrayList<RumourCard> rumourCards;
    private Player nextPlayer;
    private final int cardPerPlayer;

    public Game(int nbr_humans, int nbr_ia) {
        this.cardPerPlayer = getCardPerPlayer(nbr_humans + nbr_ia);
        this.rumourCards = createRumourCards();
        this.players = createPlayers(nbr_humans, nbr_ia, this.cardPerPlayer);
        Random random = new Random();
        this.nextPlayer = this.players.get(random.nextInt(this.players.size()));
    }


    private int getCardPerPlayer(int nbr_players) {
        int card_per_player;
        if (nbr_players == 6) {
            card_per_player = 2;
        } else {
            card_per_player = 7 - nbr_players;
        }
        return card_per_player;
    }

    private @NotNull ArrayList<Player> createPlayers(int nbr_humans, int nbr_ia, int card_per_player) {
        final ArrayList<Player> newPlayers = new ArrayList<>(nbr_humans + nbr_ia);
        for (int i = 0; i < nbr_humans; i++) {
            newPlayers.add(new HumanPlayer(card_per_player, "Joueur " + i + " (humain)", this));
        }
        for (int i = 0; i < nbr_ia; i++) {
            newPlayers.add(new ComputerPlayer(card_per_player, "Joueur " + i + " (IA)", this));
        }
        return newPlayers;
    }

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

    private void distributeRumourCards() {
        for (int i = 0; i < this.cardPerPlayer; ++i) {
            for (Player p : this.players) {
                p.giveCard(this.rumourCards.remove(this.rumourCards.size() - 1));
            }
        }
        this.rumourCards.trimToSize();
    }

    public void discardRumourCard(RumourCard card) {
        this.rumourCards.add(card);
    }

    private void assignRoles() {
        for (Player p : this.players) {
            p.chooseIdentity();
        }
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }


    public void makeGame() {
        while (!this.isGameEnded()) {
            for (Player p : this.players) {
                if (!p.isHuman()) {
                    System.out.println(p.getGame() + " : stratégie " + p.strategyString());
                }
            }
//            System.out.println("Nouveau round");
//            for (Player p : this.players) {
//                System.out.println(p.getName() + " (nombre de points : " + p.getPoints() + ")");
//            }
            this.distributeRumourCards();
            this.assignRoles();
            Round currentRound = new Round();
            currentRound.makeRound();
        }
    }

    public ArrayList<RumourCard> getDiscardedCards() {
        return this.rumourCards;
    }

    public boolean isGameEnded() {
        for (Player p : this.players) {
            if (p.getPoints() >= 5) {
                return true;
            }
        }
        return false;
    }

    private class Round {
        private final ArrayList<RumourCard> discard;

        public Round() {
            this.discard = rumourCards;
        }

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

        public void makeRound() {
            System.out.println(this.isRoundEnded());
            while (!this.isRoundEnded()) {
                this.makeTurn();
            }
            System.out.println("Fin du round");
            this.endRound();
        }

        public void makeTurn() {
            System.out.println("C'est au tour de " + nextPlayer.getName());
            nextPlayer = nextPlayer.playerTurn();
        }

        public void endRound() {
            for (Player p : players) {
                p.discardAllCards();
                p.hideIdentity();
            }
        }
    }
}
