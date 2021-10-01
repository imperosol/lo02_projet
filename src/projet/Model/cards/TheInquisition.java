package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;

import java.util.ArrayList;
import java.util.Scanner;

public final class TheInquisition extends AbstractRumourCard implements RumourCard{
    private int cardWeight = 2;

    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        ArrayList<RumourCard> cards = cardOwner.getCards();
        if (cardOwner.isHuman()) {
            System.out.println("Défaussez une carte parmi :");
            for (int i = 0; i < cards.size(); i++) {
                System.out.println(i+1 + " : " + cards.get(i));
            }
            Scanner scan = new Scanner(System.in);
            int choice = scan.nextInt();
            while (choice < 1 || choice > cards.size()) {
                System.out.print("Choix invalide : recommencez : ");
                choice = scan.nextInt();
            }
            cardOwner.discardCard(cards.get(choice - 1));
        }
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        Player nextPlayer = this.chooseNextPlayer(cardOwner, allPlayers);
        if (cardOwner.isHuman()) {
            System.out.println("Identité de " + nextPlayer + " : " + nextPlayer.getIdentity());
        }
        // TODO : implémenter le fait de connaitre l'identité du joueur choisi
        return nextPlayer;
    }

    public boolean isWitchEffectUsable(Player cardOwner) {
        // effect usable if the card has already revealed a rumour card
        return (cardOwner.isRevealed() && !cardOwner.isWitch());
    }

    @Override
    public String toString() {
        return "TheInquisition";
    }

    //    Nobody expects the spanish inquisition
}
