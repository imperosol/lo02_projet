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
    public void chooseIdentity() {//Function used to choose your identity at the beginning of a round
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
    public Player defendAgainstAccusation(Player accuser) { //Function used when a player is accused
        System.out.println(this.getName() + ", vous venez d'être accusé.");
        ArrayList<RumourCard> usableCards = this.getCardsUsableForWitch(); //check of the cards with a witch
        // effect usable
        System.out.println("Vous pouvez utiliser les cartes suivantes : ");
        for (RumourCard card : usableCards) {//Print out the usable cards
            System.out.println("\t- " + card);
        }
        System.out.print("""
                Voulez vous :\s 
                1 : Révéler votre identité
                2 : révéler une carte et appliquer son effet Witch
                \t->\040"""); //The player can choose to either reveal or use a witch effect
        int choice = WitchHuntUtils.consoleIntegerChoice(1, 2);
        Player nextPlayer;
        if (choice == 1) {
            nextPlayer = this.revealIdentityAfterAccusation(accuser); //Identity reveal
        } else {//Use a card among the selectable cards (whom the witch effect is usable)
            int cardIndex = WitchHuntUtils.consoleSelectCardIndex(usableCards);
            RumourCard card = usableCards.get(cardIndex);
            nextPlayer = card.witchEffect(this, this.game.getPlayers(), accuser);
            //Effect of the witch and check of the next player according to the effect of the card
        }
        return nextPlayer;
    }


    @Override
    public Player playerTurn() {
        System.out.println("Les cartes de votre main sont : ");
        for (RumourCard card : this.getCards()) { //At the beginning of a turn, a player has two choice
            System.out.println("\t- " + card //either attack someone, or use a hunt effect among the usable
                    + (card.isHuntEffectUsable(this) ? "" : "(Hunt inutilisable)"));
        } //If a hunt is unusable, the console tells the player
        System.out.print("""
                Voulez vous :\s
                1 : dénoncer un joueur
                2 : révéler une carte et appliquer son effet Hunt
                \t->\040""");
        int choice = WitchHuntUtils.consoleIntegerChoice(1, 2);
        Player nextPlayer;
        if (choice == 1) { //Attack
            ArrayList<Player> revealable = WitchHuntUtils.getRevealablePlayers( //Check of the list of players
                    this, //that their identity can be revealed
                    this.game.getPlayers()
            );
            ////////////////////////////////////////////////////////////////////////////////////////
            //Qu'est-ce que ça fait en dessous ?
            Player toDenounce = WitchHuntUtils.consoleSelectPlayer(revealable);
            nextPlayer = this.denounce(toDenounce);
        } else {//Use a hunt effect
            ArrayList <RumourCard> usable = this.getCardsUsableForHunt(); //Get the usable cards
            System.out.println("Choisisser une carte pour appliquer son effet Hunt : ");
            RumourCard card = WitchHuntUtils.consoleSelectCard(usable);
            this.revealCard(card);
            nextPlayer = card.huntEffect(this, this.game.getPlayers()); //Effect of the hunt and
        }// check of the next player according to the effect of the card
        return nextPlayer;
    }
}
