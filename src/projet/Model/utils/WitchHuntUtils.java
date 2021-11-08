package projet.Model.utils;

import org.jetbrains.annotations.NotNull;
import projet.Model.cards.RumourCard;
import projet.Model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//class that gather all the useful functions which are used several times through the code

public class WitchHuntUtils {

    /*ConsoleIntegerChoice is a function that ask the user for one numbers in
    order to use them somewhere : for example, when a player is attacked, he can choose
    either to reveal if he types 1 or use one of his witch card if he types 2.
    If the user types something that is not a choice, a message is sent to him
    to try again*/

    public static int consoleIntegerChoice(final int min, final int max) {

        /*When used, the code implement the min and max value that the user
        can choose*/

        Scanner scan = new Scanner(System.in); //read what the user types
        int indexChoice = scan.nextInt(); //store what he types
        while (indexChoice < min || indexChoice > max) {

            /*While the user types something outside the range,
            he is asked to try again*/

            System.out.print("Choix invalide, recommencez : ");
            indexChoice = scan.nextInt();
        }
        return indexChoice; //When the user finally types something good, we return it
    }

    public static Player consoleSelectPlayer(@NotNull List<Player> playerList) {

        /*This function is used when a player needs to choose a player :
        for example when it's the turn of someone, they can choose to
        attack another player*/

        System.out.println("Choisissez un joueur :");
        for (int i = 0; i < playerList.size(); ++i) { //For each player
            System.out.println(i+1 + " : " + playerList.get(i).getName());
            //print its number and its name
        }
        int indexChoice = WitchHuntUtils.consoleIntegerChoice(1, playerList.size());
        //Read the choice of the user which must be a number of the list of player


        return playerList.get(indexChoice - 1);
        //why -1 ?
        //////////////////////////////////////////////////////////////////////////////////////////////

    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //Selectable when not from user apart witch ?
    public static int consoleSelectCardIndex(List<RumourCard> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+1 + " : " + list.get(i));
        }
        return WitchHuntUtils.consoleIntegerChoice(1, list.size()) - 1;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    //Selectable when from user
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
        //This function return the list of the selectable players (can still play)
        ArrayList<Player> selectablePlayers = new ArrayList<>(); //Create a new list
        for (Player p : allPlayers) { //For all players
            if (p != cardOwner && !(p.isRevealed() && p.isWitch())) {
                //If the player is not the card owner, nor is already revealed as a witch
                selectablePlayers.add(p); //it is add to the list of selectable players
            }
        }
        return selectablePlayers; //return the list of selectable players
    }

    @NotNull
    public static ArrayList<Player> getRevealablePlayers(Player cardOwner, @NotNull List<Player> allPlayers) {
        //This function return the list of the revealable players (not already revealed)
        ArrayList<Player> revealablePlayers = new ArrayList<>(); //Create a new list
        for (Player p : allPlayers) { //For all players
            if (p != cardOwner && !p.isRevealed()) {
                //If the player is not the card owner, nor is already revealed
                revealablePlayers.add(p); //It is add to the list of revealable players
            }
        }
        return revealablePlayers;//Return the list of revealable players
    }
}
