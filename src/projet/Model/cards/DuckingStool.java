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
        return cardOwner.selectNextPlayer(selectablePlayers);
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
        if (cardOwner.isHuman()) {
            selectedPlayer = WitchHuntUtils.consoleSelectPlayer(revealablePlayer);
            System.out.println(selectedPlayer.getName() + ", " + selectedPlayer.getName()
                    + " vient d'utiliser Ducking Stool.\nVous pouvez :\n" +
                    "1 : Vous défausser d'une carte et prendre le tour\n" +
                    "2 : Révéler votre identité (+1 point à l'accusateur si vous êtes sorcière, -1 sinon)\n" +
                    "\t-> ");
            choice = WitchHuntUtils.consoleIntegerChoice(1, 2);
        } else {
            // TODO : implémenter comportement IA
            choice = 2;
            selectedPlayer = cardOwner; // lignes temporaire, à retirer impérativement
        }
        if (choice == 1) {  // The player discards a card
            int cardIndex;
            ArrayList<RumourCard> playerCards = selectedPlayer.getCards();
            if (selectedPlayer.isHuman()) {
                System.out.println("Choisissez une carte à défausser : \n");
                cardIndex = WitchHuntUtils.consoleSelectCardIndex(playerCards);
            } else {
                Random random = new Random();
                cardIndex = random.nextInt(playerCards.size());
            }
            selectedPlayer.discardCard(playerCards.get(cardIndex));
            return selectedPlayer;
        } else {  // The player reveal his identity
            if (selectedPlayer.isWitch()) {
                cardOwner.addPoints(1);
                return cardOwner;
            } else {
                cardOwner.addPoints(-1);
                return selectedPlayer;
            }
        }
    }

    @Override
    public String toString() {
        return "DuckingStool";
    }
}
