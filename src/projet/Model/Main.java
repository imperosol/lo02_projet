package projet.Model;

import projet.Controller.MainController;
import projet.View.GUIView;

/**
 * Class containing the method main() of this program
 */
public class Main {

    /*    public static void main(String[] args) {
            System.out.print("Nombre de joueurs (entre 3 et 6) : ");
            int nbr_players = WitchHuntUtils.consoleIntegerChoice(0, 6);
            System.out.print("Nombre de joueurs humains (entre 0 et " + nbr_players + ") : ");
            int nbr_humans = WitchHuntUtils.consoleIntegerChoice(0, nbr_players);
            int nbr_ia = nbr_players - nbr_humans;
            Game game = new Game(nbr_humans, nbr_ia);
            game.makeGame();
        }*/

    /**
     * main function
     * @param args the arguments of the program
     */
    public static void main(String[] args) {
        Game game = new Game(4, 0);
        GUIView guiView = new GUIView(game);
        MainController controller = guiView.getMainController();
        game.setController(controller);
        game.newRound();
        controller.newTurn();
    }
}