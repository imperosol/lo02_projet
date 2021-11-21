package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;
import java.util.Random;

public final class TheInquisition extends AbstractRumourCard implements RumourCard {

    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        ArrayList<RumourCard> cards = cardOwner.getCards();
        RumourCard toDiscard;
        if (cardOwner.isHuman()) {
            System.out.println("Défaussez une carte parmi :");
            toDiscard = WitchHuntUtils.consoleSelectCard(cards);
        } else {
            int cardIndex = new Random().nextInt(cards.size());
            toDiscard = cards.get(cardIndex);
        }
        cardOwner.discardCard(toDiscard);
        return cardOwner;
    }

    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        Player nextPlayer = this.chooseNextPlayer(cardOwner, allPlayers);
        cardOwner.lookAtIdentity(nextPlayer);
        return nextPlayer;
    }

    @Override
    public boolean isWitchEffectUsable(Player cardOwner) {
        // effect usable if the card has already revealed a rumour card
        return (cardOwner.isRevealed() && !cardOwner.isWitch());
    }

    @Override
    public String toString() {
        return "Nobody expects";
    }

    //    Nobody expects the spanish inquisition
}
