package projet.Model.utils;

import org.jetbrains.annotations.NotNull;
import projet.Model.cards.RumourCard;
import projet.Model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WitchHuntUtils {
    public static int consoleIntegerChoice(final int min, final int max) {
        Scanner scan = new Scanner(System.in);
        int indexChoice = scan.nextInt();
        while (indexChoice < min || indexChoice > max) {
            System.out.print("Choix invalide, recommencez : ");
            indexChoice = scan.nextInt();
        }
        return indexChoice;
    }

    public static Player consoleSelectPlayer(@NotNull List<Player> playerList) {
        System.out.println("Choisissez un joueur :");
        for (int i = 0; i < playerList.size(); ++i) {
            System.out.println(i+1 + " : " + playerList.get(i).getName());
        }
        int indexChoice = WitchHuntUtils.consoleIntegerChoice(1, playerList.size());
        return playerList.get(indexChoice - 1);
    }

    public static int consoleSelectCardIndex(List<RumourCard> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+1 + " : " + list.get(i));
        }
        return WitchHuntUtils.consoleIntegerChoice(1, list.size()) - 1;
    }

    public static RumourCard consoleSelectCard(List<RumourCard> list) {
        int choice;
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+1 + " : " + list.get(i));
        }
        choice = WitchHuntUtils.consoleIntegerChoice(1, list.size()) - 1;
        return list.get(choice);
    }

    @NotNull
    public static ArrayList<Player> getSelectablePlayers(Player cardOwner, @NotNull List<Player> allPlayers) {
        ArrayList<Player> selectablePlayers = new ArrayList<>();
        for (Player p : allPlayers) {
            if (p != cardOwner && !(p.isRevealed() && p.isWitch())) {
                selectablePlayers.add(p);
            }
        }
        return selectablePlayers;
    }

    @NotNull
    public static ArrayList<Player> getRevealablePlayers(Player cardOwner, @NotNull List<Player> allPlayers) {
        ArrayList<Player> revealablePlayers = new ArrayList<>();
        for (Player p : allPlayers) {
            if (p != cardOwner && !p.isRevealed()) {
                revealablePlayers.add(p);
            }
        }
        return revealablePlayers;
    }
}
