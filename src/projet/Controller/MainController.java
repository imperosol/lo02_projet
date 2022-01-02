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
import java.util.Objects;

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
        ArrayList<RumourCard> discard = this.game.getDiscardedCards();
        ArrayList<ImageIcon> discardImages = new ArrayList<>(discard.size());
        for (RumourCard card : discard) {
            discardImages.add(this.cardImages.get(card.toString()));
        }
        this.guiView.setDiscardededCards(discardImages);
    }

    public void focusCard(int player, int index, int type) {
        if (this.focusedCard != null) {
            this.guiView.unHighlightCard(this.focusedCard);
        }
        this.focusedCard = new GUICard(player, index, type);
        this.guiView.highlightCard(this.focusedCard);
        boolean isCardOfCurrent = this.focusedCard != null && this.focusedCard.player == this.game.getCurrentPlayerIndex();
        if (this.currentPhase == GamePhase.ATTACK_COMPLEMENTARY) {
            this.checkHuntValidity();
        } else if (this.currentPhase == GamePhase.DEFENSE_COMPLEMENTARY) {
            this.checkWitchValidity();
        } else if (this.currentPhase == GamePhase.DUCKING_STOOL) {
            if (this.focusedCard != null) {
                this.guiView.setDuckingStoolDiscardValidation(this.focusedCard.type == RUMOUR_CARD && this.focusedCard.player == this.game.getCurrentPlayerIndex());
            } else {
                this.guiView.setDuckingStoolDiscardValidation(false);
            }
        } else {
            if (isCardOfCurrent && this.focusedCard.type == RUMOUR_CARD) {
                RumourCard card = this.game.getCurrentPlayer().getCards().get(this.focusedCard.index);
                if (this.currentPhase == GamePhase.ATTACK) {
                    this.guiView.setUseHuntButton(card.isHuntEffectUsable(this.game.getCurrentPlayer()));
                } else if (this.currentPhase == GamePhase.DEFENSE) {
                    this.guiView.setUseWitchButton(card.isWitchEffectUsable(this.game.getCurrentPlayer()));
                }
            } else {
                if (this.currentPhase == GamePhase.ATTACK) {
                    this.guiView.setUseHuntButton(false);
                } else if (this.currentPhase == GamePhase.DEFENSE) {
                    this.guiView.setUseWitchButton(false);
                }
            }
        }
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
        this.currentPhase = GamePhase.ATTACK_COMPLEMENTARY;
        this.guiView.setHuntValidationGUI();
        Player current = this.game.getCurrentPlayer();
        this.usingHunt = current.getCards().get(this.focusedCard.index);
        this.addLog(current + " utilise le Hunt de la carte " + this.usingHunt);
        current.revealCard(this.usingHunt);
        this.focusedCard = null;
        this.setCardsGUI();
        this.validateHunt();
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
        this.currentPhase = GamePhase.DEFENSE_COMPLEMENTARY;
        this.guiView.setWitchValidationGUI();
        Player current = this.game.getCurrentPlayer();
        this.usingWitch = current.getCards().get(this.focusedCard.index);
        this.addLog(current + " utilise le Witch de la carte " + this.usingWitch);
        current.revealCard(this.usingWitch);
        this.guiView.unHighlightCard(this.focusedCard);
        this.focusedCard = null;
        this.validateWitch();
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
        if (this.usingWitch instanceof TheInquisition) {
            RumourCard toDiscard = current.getCards().get(this.focusedCard.index);
            current.discardCard(toDiscard);
        } else if (this.usingWitch instanceof PointedHat) {
            current.hideCard(this.focusedCard.index);
        } else if (this.usingWitch instanceof DuckingStool | this.usingWitch instanceof EvilEye) {
            this.game.setCurrentPlayer(this.game.getPlayers().get(this.focusedPlayer));
        } else {
            this.usingWitch.witchEffect(current, this.game.getPlayers(), accuser);
        }
        this.newTurn();
    }

    public void validateHunt() {
        Player current = this.game.getCurrentPlayer();
        Player selected;
        Player nextPlayer;
        if (this.focusedPlayer != -1) {
            selected = this.game.getPlayers().get(this.focusedPlayer);
        } else {
            selected = null;
        }
        if (this.usingHunt instanceof BlackCat) {
            RumourCard toDiscard = this.game.getDiscardedCards().get(this.focusedCard.index);
            nextPlayer = ((BlackCat) this.usingHunt).huntEffect(current, toDiscard);
        } else if (this.usingHunt instanceof Toad || this.usingHunt instanceof Cauldron) {
            nextPlayer = ((AbstractRumourCard) this.usingHunt).huntRevealOwnIdentity(current, selected);
        } else if (this.usingHunt instanceof AngryMob) {
            nextPlayer = ((AngryMob) this.usingHunt).huntEffect(current, Objects.requireNonNull(selected));
        } else if (this.usingHunt instanceof PointedHat) {
            current.hideCard(this.focusedCard.index);
            nextPlayer = this.game.getPlayers().get(this.focusedPlayer);
        } else if (this.usingHunt instanceof TheInquisition) {
            nextPlayer = current;
        } else if (this.usingHunt instanceof HookedNose) {
            nextPlayer = ((HookedNose) this.usingHunt).huntEffect(current, selected);
        } else if (this.usingHunt instanceof PetNewt) {
            RumourCard toTake;
            if (this.focusedCard != null) {
                toTake = this.game.getPlayers().get(this.focusedCard.player).getRevealedCards().get(this.focusedCard.index);
            } else {
                toTake = null;
            }
            nextPlayer = ((PetNewt) this.usingHunt).huntEffect(current, toTake, Objects.requireNonNull(selected));
        } else if (this.usingHunt instanceof DuckingStool) {
            this.currentPhase = GamePhase.DUCKING_STOOL;
            this.DuckingStoolHuntFinish();
            return;
        } else {
            nextPlayer = this.game.getPlayers().get(this.focusedPlayer);
        }
        this.game.setCurrentPlayer(nextPlayer);
        this.newTurn();
    }

    public void newTurn() {
        if (this.game.isCurrentRoundEnded()) {
            this.game.newRound();
        }
        this.guiView.setAttackGui();
        while (!this.game.getCurrentPlayer().isHuman()) {
            this.addLog("C'est au tour de " + this.game.getCurrentPlayer() + "\n");
            this.game.setCurrentPlayer(this.game.getCurrentPlayer().playerTurn());
        }
        this.addLog("C'est au tour de " + this.game.getCurrentPlayer() + "\n");
        this.currentPhase = GamePhase.ATTACK;
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
        if (!this.usingWitch.isWitchEffectUsable(this.game.getCurrentPlayer())) {
            this.guiView.setWitchValidation(false);
        }
    }

    public void checkHuntValidity() {
        final int current = this.game.getCurrentPlayerIndex();
        final RumourCard wantedCard = switch (this.focusedCard.type) {
            case DISCARD -> this.game.getDiscardedCards().get(this.focusedCard.index);
            case REVEALED_CARD -> this.game.getPlayers().get(this.focusedCard.player).getRevealedCards().get(this.focusedCard.index);
            case RUMOUR_CARD -> this.game.getPlayers().get(this.focusedCard.player).getCards().get(this.focusedCard.index);
            default -> throw new IllegalStateException("Unexpected value: " + this.focusedCard.type);
        };
        if (!wantedCard.isHuntEffectUsable(this.game.getCurrentPlayer())) {
            this.guiView.setHuntValidation(false);
            return;
        }
        if (wantedCard instanceof Toad || wantedCard instanceof Cauldron) {
            if (!this.game.getCurrentPlayer().isWitch()) {
                this.guiView.setHuntValidation(this.focusedPlayer == current || this.focusedPlayer == -1);
            }
            this.guiView.setHuntValidation(true);
        } else if (wantedCard instanceof BlackCat) {
            if (this.focusedCard == null) {
                this.guiView.setHuntValidation(false);
            } else if (this.game.getDiscardedCards().isEmpty()) {
                this.guiView.setHuntValidation(true);
            } else {
                this.guiView.setHuntValidation(this.focusedCard.type == DISCARD);
            }
        } else if (wantedCard instanceof PetNewt) {
            if (this.focusedCard == null || this.focusedPlayer == current || this.focusedPlayer == -1) {
                this.guiView.setHuntValidation(false);
            } else {
                this.guiView.setHuntValidation(
                        this.focusedCard.type == REVEALED_CARD
                                && this.focusedCard.player != current
                );
            }
        } else if (wantedCard instanceof PointedHat) {
            if (this.game.getCurrentPlayer().getRevealedCards().isEmpty()) {
                this.guiView.setHuntValidation(true);
            } else if (this.focusedCard == null || this.focusedPlayer == current || this.focusedPlayer == -1) {
                this.guiView.setHuntValidation(false);
            } else {
                this.guiView.setHuntValidation(this.focusedCard.type == REVEALED_CARD && this.focusedCard.player == current);
            }
        } else {
            this.guiView.setHuntValidation(this.focusedPlayer != current && this.focusedPlayer != -1);
        }
    }

    public void DuckingStoolHuntFinish() {
        Player attackedPlayer = this.game.getPlayers().get(this.focusedPlayer);
        this.addLog(attackedPlayer + ", vous devez révéler votre identité ou défausser une carte");
        attackedPlayer.setAccuser(this.game.getCurrentPlayer());
        this.game.setCurrentPlayer(attackedPlayer);
        setCardsGUI();
        this.guiView.setDuckingStoolGui();
    }

    public void duckingStoolDiscardCard() {
        RumourCard toDiscard = this.game.getCurrentPlayer().getCards().get(this.focusedCard.index);
        Player current = this.game.getCurrentPlayer();
        current.discardCard(toDiscard);
        this.newTurn();
    }

    public void DuckingStoolRevealIdentity() {
        Player current = this.game.getCurrentPlayer();
        Player accuser = current.getAccuser();
        current.revealIdentity();
        if (current.isWitch()) {
            accuser.addPoints(1);
            this.game.setCurrentPlayer(accuser);
        } else {
            accuser.addPoints(-1);
        }
        this.newTurn();
    }
}
