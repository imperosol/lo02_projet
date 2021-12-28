package projet.Controller;

import projet.Model.Game;
import projet.Model.cards.*;
import projet.Model.player.Player;
import projet.View.GUICard;
import projet.View.GUIView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public final class MainController {
    public static final int RUMOUR_CARD = 1;
    public static final int REVEALED_CARD = 2;
    public static final int DISCARD = 3;

    private GamePhase currentPhase = GamePhase.ATTACK;
    private GUICard focusedCard = null;
    private RumourCard usingHunt = null;
    private RumourCard usingWitch = null;
    private int focusedPlayer = -1;
    private final Game game;
    private final GUIView guiView;
    private final HashMap<String, ImageIcon> cardImages = new HashMap<>();

    public MainController(Game game, GUIView gui) {
        this.game = game;
        this.guiView = gui;
        String[] cardNames = new String[]{
                "AngryMob", "BlackCat", "Broomstick", "Cauldron", "DuckingStool", "EvilEye",
                "HookedNose", "PetNewt", "PointedHat", "TheInquisition", "Toad", "Wart"
        };
        for (String cardName : cardNames) {
            String path = "D:/documents/travail/LO02/projet/img/" + cardName + ".PNG";
            try {
                // TODO : make images relative to path
                BufferedImage image = ImageIO.read(new File(path));
                this.cardImages.put(
                        cardName,
                        new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(113, 150, Image.SCALE_SMOOTH))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedImage image = ImageIO.read(new File("D:/documents/travail/LO02/projet/img/HiddenCard.PNG"));
            this.cardImages.put(
                    "HiddenCard",
                    new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(113, 90, Image.SCALE_SMOOTH))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCardsGUI() {
        ArrayList<Player> players = this.game.getPlayers();
        int index = 0;
        for (Player p : players) {
            ArrayList<RumourCard> rumourCards = p.getCards();
            ArrayList<RumourCard> revealedCards = p.getRevealedCards();
            ArrayList<ImageIcon> rumourCardsImages = new ArrayList<>(rumourCards.size());
            for (RumourCard card : rumourCards) {
                if (this.game.getCurrentPlayer() == p) {
                    rumourCardsImages.add(this.cardImages.get(card.toString()));
                } else {
                    rumourCardsImages.add(this.cardImages.get("HiddenCard"));
                }
            }
            this.guiView.setRumourCards(index, rumourCardsImages);
            ArrayList<ImageIcon> revealedCardsImages = new ArrayList<>(revealedCards.size());
            for (RumourCard card : revealedCards) {
                revealedCardsImages.add(this.cardImages.get(card.toString()));
            }
            this.guiView.setRevealedCards(index, revealedCardsImages);
            index++;
        }
    }

    public void focusCard(int player, int index, int type) {
        int currentPlayerIndex = this.game.getPlayers().indexOf(this.game.getCurrentPlayer());
        if (this.focusedCard != null) {
            this.guiView.unHighlightCard(this.focusedCard);
        }
        this.focusedCard = new GUICard(player, index, type);
        this.guiView.highlightCard(this.focusedCard);
        this.guiView.setUseHuntButton(
                this.focusedCard != null
                        && this.focusedCard.player == currentPlayerIndex
        );
    }

    public void focusPlayer(int player) {
        int currentPlayerIndex = this.game.getCurrentPlayerIndex();
        if (this.focusedPlayer != -1) {
            this.guiView.unHighlightPlayer(this.focusedPlayer);
        }
        this.focusedPlayer = player;
        this.guiView.highlightPlayer(player);
        if (this.currentPhase == GamePhase.ATTACK) {
            this.guiView.setAccusePlayerButton(player != -1
                    && currentPlayerIndex != player
                    && !this.game.getPlayers().get(player).isRevealed()
            );
        }
    }

    public void useHunt() {
        Player current = this.game.getCurrentPlayer();
        this.usingHunt = current.getCards().get(this.focusedCard.index);
        this.addLog(current + " utilise le Hunt de la carte " + this.usingHunt);
        current.revealCard(this.usingHunt);
        this.guiView.setHuntValidationGUI();
        this.setCardsGUI();
        if (this.usingHunt instanceof Toad) {
            this.guiView.setWitchValidation(true);
        }
    }

    public void accusePlayer() {
        Player accused = this.game.getPlayers().get(this.focusedPlayer);
        Player current = this.game.getCurrentPlayer();
        this.currentPhase = GamePhase.DEFENSE;
        this.guiView.setDefenseGui();
        this.game.setCurrentPlayer(accused);
        current.accuse(accused);
    }

    public void useWitch() {
        this.guiView.setWitchValidationGUI();
        Player current = this.game.getCurrentPlayer();
        this.usingWitch = current.getCards().get(this.focusedCard.index);
        this.addLog(current + " utilise le Witch de la carte " + this.usingWitch);
        current.revealCard(this.usingWitch);
        this.setCardsGUI();
    }

    public void revealIdentity() {
        Player current = this.game.getCurrentPlayer();
        Player next = current.revealIdentityAfterAccusation(current.getAccuser());
        game.setCurrentPlayer(next);
        this.newTurn();
    }

    public void validateWitch() {
        Player current = this.game.getCurrentPlayer();
        Player accuser = current.getAccuser();
        Random random = new Random();
        if (this.usingWitch instanceof HookedNose) {
            current.giveCard(accuser.getCards().remove(random.nextInt(accuser.getCards().size())));
        } else if (this.usingWitch instanceof Cauldron) {
            accuser.discardCard(accuser.getCards().get(random.nextInt(accuser.getCards().size())));
        }

    }

    public void validateHunt() {

    }

    public void newTurn() {
        this.addLog("C'est au tour de " + this.game.getCurrentPlayer() + "\n");
        this.currentPhase = GamePhase.ATTACK;
        this.guiView.setAttackGui();
        this.changePlayer();
    }

    public void addLog(String log) {
        this.guiView.addLog(log);
    }

    public void changePlayer() {
        this.setCardsGUI();
        if (this.focusedCard != null) {
            this.guiView.unHighlightCard(this.focusedCard);
            this.focusedCard = null;
        }
        if (this.focusedPlayer != -1) {
            this.guiView.unHighlightPlayer(this.focusedPlayer);
            this.focusedPlayer = -1;
        }
    }

    public void checkWitchValidity() {
        if (this.usingWitch instanceof TheInquisition) {
            if (this.focusedCard == null) {
                this.guiView.setWitchValidation(false);
            } else {
                this.guiView.setWitchValidation(
                        this.focusedCard.type == MainController.RUMOUR_CARD
                                && this.focusedCard.player == this.game.getCurrentPlayerIndex()
                );
            }
        } else if (this.usingWitch instanceof PointedHat) {
            if (this.focusedCard == null) {
                this.guiView.setWitchValidation(false);
            } else {
                this.guiView.setWitchValidation(
                        this.focusedCard.type == MainController.REVEALED_CARD
                                && this.focusedCard.player == this.game.getCurrentPlayerIndex()
                );
            }
        } else {
            this.guiView.setWitchValidation(true);
        }
    }

    public void checkHuntValidity() {
        if (this.usingHunt instanceof Toad || this.usingHunt instanceof Cauldron) {
            this.guiView.setWitchValidation(true);
            return;
        }
        int currentPlayerIndex = this.game.getCurrentPlayerIndex();
        if (this.usingHunt instanceof BlackCat) {
//            this.guiView.setWitchValidation();
            this.guiView.setWitchValidation(
                    this.focusedPlayer != -1
                            && currentPlayerIndex != this.focusedPlayer
                            && !this.game.getPlayers().get(this.focusedPlayer).isRevealed()
            );
        }
    }

}
