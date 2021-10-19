package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;
import java.util.Random;

public final class DuckingStool extends AbstractRumourCard implements RumourCard {
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return this.chooseNextPlayer(cardOwner, allPlayers);
    }

    private Player getWartOwner(Player cardOwner, ArrayList<Player> allPlayers) {
        for (Player p : allPlayers) {
            if (p != cardOwner && !p.isRevealed()) {
                for (RumourCard card : p.getRevealedCards()) {
                    if (card instanceof Wart) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        // if the Wart card has been revealed, remove its owner from the list of revealable players
        Player toExclude = this.getWartOwner(cardOwner, allPlayers);
        Player selectedPlayer = cardOwner.getPlayerToAccuse(toExclude);
        Random random = new Random();
        int choice;
        if (selectedPlayer.isHuman()) {
            System.out.println(selectedPlayer.getName() + ", " + cardOwner.getName()
                    + " vient d'utiliser Ducking Stool.");
            if (selectedPlayer.getCards().size() > 0 && !selectedPlayer.isRevealed()) {
                System.out.print("""
                        Vous pouvez :
                        1 : Vous défausser d'une carte et prendre le tour
                        2 : Révéler votre identité (+1 point à l'accusateur si vous êtes sorcière, -1 sinon)
                        \t->\s""");
                choice = WitchHuntUtils.consoleIntegerChoice(1, 2);
            } else if (selectedPlayer.getCards().size() == 0 && !selectedPlayer.isRevealed()) {
                System.out.println("Vous devez révéler votre identité");
                choice = 2;
            } else if (selectedPlayer.getCards().size() > 0 && selectedPlayer.isRevealed()) {
                System.out.println("Vous devez défausser une carte");
                choice = 1;
            } else {
                System.out.println("Vous n'en subissez pas les effets");
                return selectedPlayer;
            }
        } else {
            // TODO : comportement purement aléatoire. Améliorer ça
            if (selectedPlayer.getCards().size() > 0 && !selectedPlayer.isRevealed()) {
                choice = random.nextInt(2) + 1;
            } else if (selectedPlayer.getCards().size() == 0 && !selectedPlayer.isRevealed()) {
                choice = 2;
            } else if (selectedPlayer.getCards().size() > 0 && selectedPlayer.isRevealed()) {
                choice = 1;
            } else {
                return selectedPlayer;
            }
        }
        if (choice == 1) {  // The player discards a card
            int cardIndex;
            ArrayList<RumourCard> playerCards = selectedPlayer.getCards();
            if (selectedPlayer.isHuman()) {
                System.out.println("Choisissez une carte à défausser : \n");
                cardIndex = WitchHuntUtils.consoleSelectCardIndex(playerCards);
            } else {
                cardIndex = random.nextInt(playerCards.size());
            }
            selectedPlayer.discardCard(playerCards.get(cardIndex));
            return selectedPlayer;
        } else {  // The player reveals his identity
            selectedPlayer.revealIdentity();
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
