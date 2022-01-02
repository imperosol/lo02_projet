package projet.Model.player;

import projet.Model.Game;
import projet.Model.cards.Identity;
import projet.Model.cards.RumourCard;
import projet.Model.utils.WitchHuntUtils;
import projet.View.CLIView;

import java.util.ArrayList;

/**
 * a class representing a human player.
 * Inherits from {@link Player}
 * @see Player
 * @author Thomas Girod
 */
public final class HumanPlayer extends Player {
    /**
     * {@inheritDoc}
     */
    public HumanPlayer(String name, Game game) {
        super(name, game);
    }

    /**
     * {@inheritDoc}
     * @return true
     */
    @Override
    public boolean isHuman() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chooseIdentity() {
        System.out.println(this.getName() + ", voulez-vous être :\n" +
                "1 : Villageois\n" +
                "2 : Sorcière");
        int choice = CLIView.consoleIntegerChoice(1, 2);
        if (choice == 1) {
            this.setIdentity(Identity.VILLAGER);
        } else {
            this.setIdentity(Identity.WITCH);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player selectNextPlayer(ArrayList<Player> selectablePlayers) {
        return CLIView.consoleSelectPlayer(selectablePlayers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player defendAgainstAccusation(Player accuser) {
        this.game.setCurrentPlayer(this);
        this.game.getController().changePlayer();
        this.setAccuser(accuser);
        System.out.println(this.getName() + ", vous venez d'être accusé.");
        ArrayList<RumourCard> usableCards = this.getCardsUsableForWitch();
        System.out.println("Vous pouvez utiliser les cartes suivantes : ");
        for (RumourCard card : usableCards) {
            System.out.println("\t- " + card);
        }
//        System.out.print("""
//                Voulez vous :\s
//                1 : Révéler votre identité
//                2 : Révéler une carte et appliquer son effet Witch
//                \t->\040""");
//        int choice = CLIView.consoleIntegerChoice(1, 2);
//        Player nextPlayer;
//        if (choice == 1) {
//            nextPlayer = this.revealIdentityAfterAccusation(accuser);
//        } else {
//            int cardIndex = CLIView.consoleSelectCardIndex(usableCards);
//            RumourCard card = usableCards.get(cardIndex);
//            nextPlayer = card.witchEffect(this, this.game.getPlayers(), accuser);
//            this.revealCard(card);
//        }
//        return nextPlayer;
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Player getPlayerToAccuse(Player toExclude) {
        ArrayList<Player> revealable = WitchHuntUtils.getRevealablePlayers(
                this,
                this.game.getPlayers()
        );
        revealable.remove(toExclude);
        return CLIView.consoleSelectPlayer(revealable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lookAtIdentity(Player lookedPlayer) {
        System.out.println("Identité de " + lookedPlayer + " : " + lookedPlayer.printIdentity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player playerTurn() {
        System.out.println("Les cartes de votre main sont : ");
        for (RumourCard card : this.getCards()) {
            System.out.println("\t- " + card
                    + (card.isHuntEffectUsable(this) ? "" : "(Hunt inutilisable)"));
        }
        System.out.print("""
                Voulez vous :\s
                1 : dénoncer un joueur
                2 : révéler une carte et appliquer son effet Hunt
                \t->\040""");
        // TODO reintegrate this part when gui is finished
//        int choice = CLIView.consoleIntegerChoice(1, 2);
//        Player nextPlayer;
//        if (attackAction == AttackAction.ACCUSE_PLAYER) {
//            Player toDenounce = this.getPlayerToAccuse(null);
//            nextPlayer = this.accuse(toDenounce);
//        } else {
//            ArrayList<RumourCard> usable = this.getCardsUsableForHunt();
//            System.out.println("Choisissez une carte pour appliquer son effet Hunt : ");
//            RumourCard card = CLIView.consoleSelectCard(usable);
//            this.revealCard(card);
//            nextPlayer = card.huntEffect(this, this.game.getPlayers());
//        }
//        return nextPlayer;
        return null;
    }
}
