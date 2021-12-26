package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.View.CLIView;

import java.util.ArrayList;
import java.util.Random;

public final class PetNewt extends AbstractRumourCard implements RumourCard {
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        ArrayList<RumourCard> revealedCards = this.getAllRevealedCards(cardOwner, allPlayers);
        int choice;
        if (revealedCards.size() == 0) {
            System.out.println("Aucune carte révélée, vous ne pouvez rien récupérer.");
        } else {
            if (cardOwner.isHuman()) {
                System.out.println("Récupérez une carte parmi les suivantes :");
                choice = CLIView.consoleSelectCardIndex(revealedCards);
            } else {
                choice = new Random().nextInt(revealedCards.size());
            }
            RumourCard card = revealedCards.get(choice);
            Player opponent = this.getCardOwner(card, allPlayers);
            if (opponent != null) {
                opponent.getRevealedCards().remove(card);
                cardOwner.giveCard(card);
            }
        }
        return this.chooseNextPlayer(cardOwner, allPlayers);
    }

    private Player getCardOwner(RumourCard card, @NotNull ArrayList<Player> allPlayers) {
        for (Player p : allPlayers) {
            for (RumourCard rumourCard : p.getRevealedCards()) {
                if (rumourCard == card) {
                    return p;
                }
            }
        }
        return null;
    }

    private ArrayList<RumourCard> getAllRevealedCards(Player cardOwner, ArrayList<Player> allPlayers) {
        ArrayList<RumourCard> revealedCards = new ArrayList<>();
        for (Player p : allPlayers) {
            if (p != cardOwner) {
                revealedCards.addAll(p.getRevealedCards());
            }
        }
        return revealedCards;
    }

    @Override
    public String toString() {
        return "PetNewt";
    }
}
