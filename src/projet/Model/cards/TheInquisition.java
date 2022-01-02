package projet.Model.cards;

import org.jetbrains.annotations.NotNull;
import projet.Model.player.Player;
import projet.View.CLIView;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class representing the Inquisition rumour card
 * @author Thomas Girod
 */
public final class TheInquisition extends AbstractRumourCard implements RumourCard {

    /**
     * {@inheritDoc}
     */
    @Override
    public Player witchEffect(Player cardOwner, @NotNull ArrayList<Player> allPlayers, Player accuser) {
        ArrayList<RumourCard> cards = cardOwner.getCards();
        RumourCard toDiscard;
        if (cardOwner.isHuman()) {
            System.out.println("Défaussez une carte parmi :");
            toDiscard = CLIView.consoleSelectCard(cards);
        } else {
            int cardIndex = new Random().nextInt(cards.size());
            toDiscard = cards.get(cardIndex);
        }
        cardOwner.discardCard(toDiscard);
        return cardOwner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player huntEffect(Player cardOwner, ArrayList<Player> allPlayers) {
        Player nextPlayer = this.chooseNextPlayer(cardOwner, allPlayers);
        cardOwner.lookAtIdentity(nextPlayer);
        return nextPlayer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWitchEffectUsable(Player cardOwner) {
        // effect usable if the card has already revealed a rumour card
        return (cardOwner.isRevealed() && !cardOwner.isWitch());
    }

    /**
     * {@inheritDoc}
     * @return "TheInquisition"
     */
    @Override
    public String toString() {
        return "TheInquisition";
    }

    //    Nobody expects the spanish inquisition
}
