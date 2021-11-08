package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;

public final class TheInquisition extends AbstractRumourCard implements RumourCard{
    private int cardWeight = 2;

    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        ArrayList<RumourCard> cards = cardOwner.getCards(); //Get the cards from the card owner
        if (cardOwner.isHuman()) { //If he's human
            System.out.println("Défaussez une carte parmi :"); //then he discards a card among his
            for (int i = 0; i < cards.size(); i++) {
                System.out.println(i+1 + " : " + cards.get(i));
            }
            int choice = WitchHuntUtils.consoleIntegerChoice(1, cards.size());
            cardOwner.discardCard(cards.get(choice - 1)); //discard the card
        }
        return cardOwner; //and finally he's the next player
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        Player nextPlayer = this.chooseNextPlayer(cardOwner, allPlayers);
        if (cardOwner.isHuman()) { //if human, choose the next player
            System.out.println("Identité de " + nextPlayer + " : " + nextPlayer.printIdentity());
        } //And look at his identity
        // TODO : implémenter le fait de connaitre l'identité du joueur choisi
        return nextPlayer;
    }

    @Override
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
