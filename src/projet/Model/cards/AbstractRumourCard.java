package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractRumourCard {

    @NotNull ArrayList<Player> getSelectablePlayers(Player cardOwner, @NotNull List<Player> allPlayers) {
        ArrayList<Player> selectablePlayers = new ArrayList<>();
        for (Player p : allPlayers) {
            if (p != cardOwner && !(p.isRevealed() && p.isWitch())) {
                selectablePlayers.add(p);
            }
        }
        return selectablePlayers;
    }

    @NotNull ArrayList<Player> getRevealablePlayers(Player cardOwner, @NotNull List<Player> allPlayers) {
        ArrayList<Player> revealablePlayers = new ArrayList<>();
        for (Player p : allPlayers) {
            if (p != cardOwner && !p.isRevealed()) {
                revealablePlayers.add(p);
            }
        }
        return revealablePlayers;
    }

    Player shellPlayerSelection(@NotNull List<Player> playerList) {
        System.out.println("Choisissez un joueur :");
        for (int i = 0; i < playerList.size(); ++i) {
            System.out.println(i+1 + " : " + playerList.get(i).getName());
        }
        Scanner scan = new Scanner(System.in);
        int indexChoice = scan.nextInt();
        while (indexChoice < 1 || indexChoice > playerList.size()) {
            System.out.print("Choix invalide, recommencez : ");
            indexChoice = scan.nextInt();
        }
        return playerList.get(indexChoice);
    }

    Player chooseNextPlayer(Player cardOwner, @NotNull List<Player> allPlayers) {
        Player nextPlayer = null;
        ArrayList<Player> choosePlayers = this.getSelectablePlayers(cardOwner, allPlayers);
        if (cardOwner.isHuman()) {
            nextPlayer = shellPlayerSelection(choosePlayers);
        }
        return nextPlayer;
    }

    public boolean isWitchEffectUsable(Player cardOwner) {
        return true;
    }

    public boolean isHuntEffectUsable(Player cardOwner) {
        return true;
    }
}
