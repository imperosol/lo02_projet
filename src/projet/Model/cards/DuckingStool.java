package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;
import java.util.Random;

public final class DuckingStool extends AbstractRumourCard implements RumourCard {
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        ArrayList<Player> selectablePlayers = WitchHuntUtils.getSelectablePlayers(cardOwner, allPlayers);
        //For the witch effect, the card owner choose the next player if he is human
        if (cardOwner.isHuman()) {
            return WitchHuntUtils.consoleSelectPlayer(selectablePlayers);
        } else { //And ... if it's an ia
            // TODO : implémenter comportement IA
            return cardOwner;
        }
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        ArrayList<Player> revealablePlayer = WitchHuntUtils.getRevealablePlayers(cardOwner, allPlayers);

        // if the Wart card has been revealed, remove its owner from the list of revealable players
        outerLoop:
        for (Player p : revealablePlayer) {
            for (RumourCard card : p.getRevealedCards()) {
                if (card instanceof Wart) {
                    revealablePlayer.remove(p);
                    break outerLoop;
                }
            }
        }
        Player selectedPlayer;
        int choice;
        if (cardOwner.isHuman()) {//If the card owner is human, he gets to choose a player to reveal
            selectedPlayer = WitchHuntUtils.consoleSelectPlayer(revealablePlayer);
            System.out.println(selectedPlayer.getName() + ", " + selectedPlayer.getName()
                    + " vient d'utiliser Ducking Stool.\nVous pouvez :\n" +
                    "1 : Vous défausser d'une carte et prendre le tour\n" +
                    "2 : Révéler votre identité (+1 point à l'accusateur si vous êtes sorcière, -1 sinon)\n" +
                    "\t-> "); /*The accused one got two choice, reveal is identity (if he's a witch the card
            owner wins a point, else he looses one) or discard one of his cards*/
            choice = WitchHuntUtils.consoleIntegerChoice(1, 2);
        } else { //if the card owner is an IA then ...
            // TODO : implémenter comportement IA
            choice = 2;
            selectedPlayer = cardOwner; // lignes temporaire, à retirer impérativement
        }
        if (choice == 1) {  // The player discards a card
            int cardIndex;
            ArrayList<RumourCard> playerCards = selectedPlayer.getCards();
            if (selectedPlayer.isHuman()) { //If he's human he chooses a card
                System.out.println("Choisissez une carte à défausser : \n");
                cardIndex = WitchHuntUtils.consoleSelectCardIndex(playerCards);
            } else { //if it's an IA it discards a random card
                Random random = new Random();
                cardIndex = random.nextInt(playerCards.size());
            }
            selectedPlayer.discardCard(playerCards.get(cardIndex)); //discard of the card
            return selectedPlayer; //if he discards he's next to play
        } else {  // The player reveal his identity
            if (selectedPlayer.isWitch()) {
                cardOwner.addPoints(1);
                return cardOwner; //If he's a witch then the card owner wins a point and he's next to play
            } else {
                cardOwner.addPoints(-1);
                return selectedPlayer;
            }//If he's a villager then the card owner looses a point and the accused is next to play
        }
    }

    @Override
    public String toString() {
        return "DuckingStool";
    }
}
