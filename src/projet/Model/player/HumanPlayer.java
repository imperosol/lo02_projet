package projet.Model.player;

import projet.Model.Game;
import projet.Model.cards.Identity;
import projet.Model.cards.RumourCard;
import projet.Model.utils.WitchHuntUtils;

import java.util.ArrayList;

public class HumanPlayer extends Player {
    public HumanPlayer(int nbr_of_cards, String name, Game game) {
        super(nbr_of_cards, name, game);
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public void chooseIdentity() {
        System.out.println(this.getName() + ", voulez-vous être :\n" +
                "1 : Villageois\n" +
                "2 : Sorcière");
        int choice = WitchHuntUtils.consoleIntegerChoice(1, 2);
        if (choice == 1) {
            this.setIdentity(Identity.VILLAGER);
        } else {
            this.setIdentity(Identity.WITCH);
        }
    }

    @Override
    public Player selectNextPlayer(ArrayList<Player> selectablePlayers) {
        return WitchHuntUtils.consoleSelectPlayer(selectablePlayers);
    }

    @Override
    public Player defendAgainstAccusation(Player accuser) {
        System.out.println(this.getName() + ", vous venez d'être accusé.");
        ArrayList<RumourCard> usableCards = this.getCardsUsableForWitch();
        System.out.println("Vous pouvez utiliser les cartes suivantes : ");
        for (RumourCard card : usableCards) {
            System.out.println("\t- " + card);
        }
        System.out.print("""
                Voulez vous :\s
                1 : Révéler votre identité
                2 : révéler une carte et appliquer son effet Witch
                \t->\040""");
        int choice = WitchHuntUtils.consoleIntegerChoice(1, 2);
        Player nextPlayer;
        if (choice == 1) {
            nextPlayer = this.revealIdentityAfterAccusation(accuser);
        } else {
            int cardIndex = WitchHuntUtils.consoleSelectCardIndex(usableCards);
            RumourCard card = usableCards.get(cardIndex);
            nextPlayer = card.witchEffect(this, this.game.getPlayers(), accuser);
        }
        return nextPlayer;
    }


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
        int choice = WitchHuntUtils.consoleIntegerChoice(1, 2);
        Player nextPlayer;
        if (choice == 1) {
            ArrayList<Player> revealable = WitchHuntUtils.getRevealablePlayers(
                    this,
                    this.game.getPlayers()
            );
            Player toDenounce = WitchHuntUtils.consoleSelectPlayer(revealable);
            nextPlayer = this.denounce(toDenounce);
        } else {
            ArrayList <RumourCard> usable = this.getCardsUsableForHunt();
            System.out.println("Choisisser une carte pour appliquer son effet Hunt : ");
            RumourCard card = WitchHuntUtils.consoleSelectCard(usable);
            this.revealCard(card);
            nextPlayer = card.huntEffect(this, this.game.getPlayers());
        }
        return nextPlayer;
    }
}
