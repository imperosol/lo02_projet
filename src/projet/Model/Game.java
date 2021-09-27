package projet.Model;

import org.jetbrains.annotations.NotNull;
import projet.Model.cards.*;
import projet.Model.player.ComputerPlayer;
import projet.Model.player.HumanPlayer;
import projet.Model.player.Identity;
import projet.Model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


public class Game {
    private final ArrayList<Player> players;
    private final ArrayList<RumourCard> rumourCards;
    private Player nextPlayer;
    private final int cardPerPlayer;
    private Round currentRound;

    public Game(int nbr_players, int nbr_ia) {
        this.cardPerPlayer = getCardPerPlayer(nbr_players);
        this.rumourCards = getRumourCards();
        this.players = getPlayers(nbr_players, nbr_ia, this.cardPerPlayer);
        Random randomSeed = new Random();
        this.nextPlayer = this.players.get(randomSeed.nextInt(nbr_players));
        this.currentRound = null;
    }

    public void startRound() {
        this.distributeRumourCards();
        this.assignRoles();
        this.currentRound = new Round();
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

    private @NotNull ArrayList<Player> getPlayers(int nbr_players, int nbr_ia, int card_per_player) {
        final ArrayList<Player> newPlayers = new ArrayList<>(nbr_players);

        for (int i = 0; i < nbr_ia; i++) {
            newPlayers.add(new ComputerPlayer(card_per_player, "Joueur " + i + " (IA)"));
        }
        for (int i = nbr_ia; i < nbr_players; i++) {
            newPlayers.add(new HumanPlayer(card_per_player, "Joueur " + i + " (humain)"));
        }
        return newPlayers;
    }

    private @NotNull ArrayList<RumourCard> getRumourCards() {
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

    private void assignRoles() {
        for (Player p : this.players) {
            Scanner scan = new Scanner(System.in);
            System.out.println(p.getName() + ", voulez-vous être :\n" +
                    "1 : Villageois\n" +
                    "2 : Sorcière");
            int choice = scan.nextInt();
            while (choice != 1 && choice != 2) {
                System.out.print("Choix invalide, recommencez : ");
                choice = scan.nextInt();
            }
            if (choice == 1) {
                p.setIdentity(Identity.VILLAGER);
            } else {
                p.setIdentity(Identity.WITCH);
            }
        }
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public boolean isCurrentRoundEnded() {
        if (this.currentRound == null) {  // no round atm
            return true;
        } else {
            return this.currentRound.isRoundEnded();
        }
    }

    public void makeTurn() {
        if (this.currentRound != null) {
            this.currentRound.makeTurn();
        }
    }

    private class Round {
        private ArrayList<Player> unrevealedPlayers;
        private ArrayList<RumourCard> discard;

        public Round() {
            this.unrevealedPlayers = new ArrayList<>(players);
            this.discard = rumourCards;
        }

        public boolean isRoundEnded() {
            return this.unrevealedPlayers.size() <= 1;
        }

        public void makeTurn() {
            System.out.println("yo");
        }
    }
}
