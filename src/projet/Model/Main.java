package projet.Model;

import projet.Model.utils.WitchHuntUtils;

public class Main {
    public static void main(String[] args) {
        System.out.print("Nombre de joueurs (entre 3 et 6) : ");
        int nbr_players = WitchHuntUtils.consoleIntegerChoice(0, 6);
        System.out.print("Nombre de joueurs humains (entre 0 et " + nbr_players + ") : ");
        int nbr_humans = WitchHuntUtils.consoleIntegerChoice(0, nbr_players);
        int nbr_ia = nbr_players - nbr_humans;
//        int nbr_ia = 0;
//        if (nbr_humans < nbr_players) {
//            System.out.print("Nombre de joueurs IA (entre 0 et " + (nbr_players - nbr_humans) + ") : ");
//            nbr_ia = WitchHuntUtils.consoleIntegerChoice(0, nbr_players - nbr_humans);
//        }
        Game game = new Game(nbr_humans, nbr_ia);
        game.makeGame();
    }
}