package projet.Model;

import projet.Model.utils.WitchHuntUtils;

public class Main {
    public static void main(String[] args) {
        System.out.print("Nombre de joueurs (entre 3 et 6) : ");
        int nbr_players = WitchHuntUtils.consoleIntegerChoice(3, 6);
        int nbr_ia = 0;
        if (nbr_players < 6) {
            System.out.print("Nombre de joueurs IA (entre 0 et " + (6 - nbr_players) + ") : ");
            nbr_ia = WitchHuntUtils.consoleIntegerChoice(0, 6 - nbr_players);
        }
        Game game = new Game(nbr_players, nbr_ia);
        game.makeGame();

    }
}
