package projet.Model;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Nombre de joueurs (entre 3 et 6) : ");
        int nbr_players = scan.nextInt();
        while (nbr_players < 3 || nbr_players > 6) {
            System.out.print("Nombre de joueurs invalide : ");
            nbr_players = scan.nextInt();
        }
        int nbr_ia = 0;
        if (nbr_players < 6) {
            System.out.print("Nombre de joueurs IA (entre 0 et " + (6 - nbr_players) + ") : ");
            nbr_ia = scan.nextInt();
            while (nbr_ia < 0 || nbr_ia > 6 - nbr_players) {
                System.out.print("Nombre de joueurs IA invalide : ");
                nbr_ia = scan.nextInt();
            }
        }
        Game game = new Game(nbr_players, nbr_ia);
        game.makeGame();
//        while (!game.isCurrentRoundEnded()) {
//            game.makeTurn();
//        }

    }
}
