package projet.View;

import org.jetbrains.annotations.NotNull;
import projet.Model.cards.RumourCard;
import projet.Model.player.Player;

import java.util.List;
import java.util.Scanner;

public class CLIView {
    public static RumourCard consoleSelectCard(List<RumourCard> list) {
        int choice;
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+1 + " : " + list.get(i));
        }
        choice = consoleIntegerChoice(1, list.size()) - 1;
        return list.get(choice);
    }

    public static int consoleSelectCardIndex(List<RumourCard> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+1 + " : " + list.get(i));
        }
        System.out.print("\t-> ");
        return consoleIntegerChoice(1, list.size()) - 1;
    }

    public static Player consoleSelectPlayer(@NotNull List<Player> playerList) {
        System.out.println("Choisissez un joueur :");
        for (int i = 0; i < playerList.size(); ++i) {
            System.out.println(i+1 + " : " + playerList.get(i).getName());
        }
        int indexChoice = consoleIntegerChoice(1, playerList.size());
        return playerList.get(indexChoice - 1);
    }

    public static int consoleIntegerChoice(final int min, final int max) {
        Scanner scan = new Scanner(System.in);
        int indexChoice = scan.nextInt();
        while (indexChoice < min || indexChoice > max) {
            System.out.print("Choix invalide, recommencez : ");
            indexChoice = scan.nextInt();
        }
        return indexChoice;
    }
}
