package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;

public final class PetNewt extends AbstractRumourCard implements RumourCard {
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        return cardOwner; //For the witch effect, we return the card owner and he becomes next player
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        ArrayList<RumourCard> revealedCards = this.getAllRevealedCards(cardOwner, allPlayers);
        //among all the revealed rumour cards
        int choice;
        if (cardOwner.isHuman()) { //if the player is human, he can choose one and add it to his hand
            System.out.println("Récupérez une carte parmi les suivantes :");
            choice = WitchHuntUtils.consoleSelectCardIndex(revealedCards);
        } else { //and if he's an IA, ...
            // TODO : implémenter comportement IA
            choice = 0;
        }
        RumourCard card = revealedCards.get(choice); //get the rumour card chosen
        ////////////////////////////////////////////////////////////////////////////////////////////
        Player opponent = this.getCardOwner(card, allPlayers);//Pas compris ça
        if (opponent != null) {//Check if the opponent is existing
            opponent.getRevealedCards().remove(card); //Remove the chosen card from his revealed cards
            cardOwner.giveCard(card); //give the card to the card owner
        }
        return this.chooseNextPlayer(cardOwner, allPlayers); //The card owner chooses the next player
    }
/////////////////////////////////////////////////////////////////////////////////////////////////
    //ça renvoi les cartes révélées de chacun ?
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
        ArrayList<RumourCard> revealedCards = new ArrayList<>(); //return all the revealed card on the table
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
