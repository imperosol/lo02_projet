package projet.View;

import projet.Controller.GameSummaryController;
import projet.Controller.MainController;
import projet.Model.Game;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GUIView extends JFrame {
    private JPanel containerPanel;
    private JTextPane logJPane;
    private JTabbedPane playersSummaryPane;
    private JTable playersTable;
    private JPanel player1Panel;
    private JPanel player2Panel;
    private JPanel Player3Panel;
    private JPanel Player4Panel;
    private JPanel Player5Panel;
    private JPanel Player6Panel;
    private JLabel roundLabel;
    private JLabel player1Label;
    private JPanel player1RumourCards;
    private JPanel player1RevealedCards;
    private JPanel player3RumourCards;
    private JPanel player5RumourCards;
    private JPanel player2RumourCards;
    private JPanel player4RumourCards;
    private JPanel player6RumourCards;
    private JPanel player2RevealedCards;
    private JPanel player3RevealedCards;
    private JPanel player4RevealedCards;
    private JPanel player5RevealedCards;
    private JPanel player6RevealedCards;
    private JButton accusePlayerButton;
    private JButton useHuntButton;
    private JButton useWitchButton;
    private JButton revealIdentityButton;
    private JLabel joueur3Label;
    private JLabel joueur5Label;
    private JLabel joueur2Label;
    private JLabel joueur4Label;
    private JLabel joueur6Label;
    private JButton validateHuntButton;
    private JButton validateWitchButton;
    private JPanel discardPanel;
    private ArrayList<ArrayList<JLabel>> rumourCards;
    private ArrayList<ArrayList<JLabel>> revealedCards;
    private ArrayList<JLabel> discardLabels;
    private ArrayList<JLabel> playerLabels;
    private final GameSummaryController gameSummaryController;
    private final MainController mainController;

    public GUIView(Game game) {
        super("test");
        this.gameSummaryController = new GameSummaryController(game);
        this.mainController = new MainController(game, this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.containerPanel);
        this.pack();
        this.setVisible(true);
    }

    private void createUIComponents() {
        initGameSummary();
        initCardPanels();
        initPlayerLabels();
        initButtons();
    }

    private void initButtons() {
        this.useHuntButton = new JButton();
        this.accusePlayerButton = new JButton();
        this.useWitchButton = new JButton();
        this.revealIdentityButton = new JButton();
        this.validateHuntButton = new JButton();
        this.validateWitchButton = new JButton();
        this.useWitchButton.setVisible(false);
        this.revealIdentityButton.setVisible(false);
        this.useHuntButton.setEnabled(false);
        this.accusePlayerButton.setEnabled(false);
        this.validateWitchButton.setVisible(false);
        this.validateHuntButton.setVisible(false);
        this.useHuntButton.addActionListener(e -> mainController.useHunt());
        this.accusePlayerButton.addActionListener(e -> mainController.accusePlayer());
        this.useWitchButton.addActionListener(e -> mainController.useWitch());
        this.revealIdentityButton.addActionListener(e -> mainController.revealIdentity());
        this.validateWitchButton.addActionListener(e -> mainController.validateWitch());
        this.validateHuntButton.addActionListener(e -> mainController.validateHunt());
    }

    private void initPlayerLabels() {
        this.playerLabels = new ArrayList<>();
        this.player1Label = new JLabel();
        this.joueur2Label = new JLabel();
        this.joueur3Label = new JLabel();
        this.joueur4Label = new JLabel();
        this.joueur5Label = new JLabel();
        this.joueur6Label = new JLabel();
        Collections.addAll(
                this.playerLabels,
                this.player1Label, this.joueur2Label, this.joueur3Label,
                this.joueur4Label, this.joueur5Label, this.joueur6Label
        );
        for (JLabel player : playerLabels) {
            player.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mainController.focusPlayer(playerLabels.indexOf(player));
                    mainController.checkWitchValidity();
                    mainController.checkHuntValidity();
                }
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
        }
    }

    private void initGameSummary() {
        this.playersTable = new JTable(new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return gameSummaryController.getLines();
            }

            @Override
            public int getColumnCount() {
                return 3;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return gameSummaryController.getValueAt(rowIndex, columnIndex);
            }
        });
    }

    private void initCardPanels() {
        this.player1RumourCards = new JPanel();
        this.player2RumourCards = new JPanel();
        this.player3RumourCards = new JPanel();
        this.player4RumourCards = new JPanel();
        this.player5RumourCards = new JPanel();
        this.player6RumourCards = new JPanel();
        this.player1RevealedCards = new JPanel();
        this.player2RevealedCards = new JPanel();
        this.player3RevealedCards = new JPanel();
        this.player4RevealedCards = new JPanel();
        this.player5RevealedCards = new JPanel();
        this.player6RevealedCards = new JPanel();
        this.discardPanel = new JPanel();
        ArrayList<JPanel> rumourCardsPanel = new ArrayList<>(6);
        ArrayList<JPanel> revealedCardsPanel = new ArrayList<>(6);
        Collections.addAll(
                rumourCardsPanel,
                this.player1RumourCards, this.player2RumourCards, this.player3RumourCards,
                this.player4RumourCards, this.player5RumourCards, this.player6RumourCards
        );
        Collections.addAll(
                revealedCardsPanel,
                this.player1RevealedCards, this.player2RevealedCards, this.player3RevealedCards,
                this.player4RevealedCards, this.player5RevealedCards, this.player6RevealedCards
        );
        this.rumourCards = new ArrayList<>(6);
        this.revealedCards = new ArrayList<>(6);
        this.discardLabels = new ArrayList<>(12);
        for (int panel = 0; panel < 6; panel++) {
            ArrayList<JLabel> tmpCards = new ArrayList<>(4);
            ArrayList<JLabel> tmpRevealed = new ArrayList<>(4);
            for (int label = 0; label < 5; label++) {
                JLabel tmpLabel1 = new JLabel();
                JLabel tmpLabel2 = new JLabel();
                gerCardListener(tmpLabel1, label, panel, MainController.RUMOUR_CARD);
                gerCardListener(tmpLabel2, label, panel, MainController.REVEALED_CARD);
                tmpCards.add(tmpLabel1);
                rumourCardsPanel.get(panel).add(tmpLabel1);
                tmpRevealed.add(tmpLabel2);
                revealedCardsPanel.get(panel).add(tmpLabel2);

            }
            for (int i = 0; i < 2; i++) {
                JLabel tmpLabel = new JLabel();
                gerCardListener(tmpLabel, panel * 2 + i, 1, MainController.DISCARD);
                this.discardPanel.add(tmpLabel);
                this.discardLabels.add(tmpLabel);
            }
            this.rumourCards.add(tmpCards);
            this.revealedCards.add(tmpRevealed);
        }
    }

    private void gerCardListener(JLabel tmpLabel, int label, int panel, int type) {
        tmpLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainController.focusCard(panel, label, type);
                mainController.checkWitchValidity();
                mainController.checkHuntValidity();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void setRumourCards(int player, List<ImageIcon> imageIcons) {
        for (int i = 0; i < imageIcons.size(); i++) {
            this.rumourCards.get(player).get(i).setIcon(imageIcons.get(i));
        }
        this.rumourCards.get(player).get(imageIcons.size()).setIcon(null);
    }

    public void setRevealedCards(int player, List<ImageIcon> imageIcons) {
        for (int i = 0; i < imageIcons.size(); i++) {
            this.revealedCards.get(player).get(i).setIcon(imageIcons.get(i));
        }
        this.revealedCards.get(player).get(imageIcons.size()).setIcon(null);
    }

    public void highlightCard(int panel, int label, int type) {
        if (type == MainController.RUMOUR_CARD) {
            this.rumourCards.get(panel).get(label).setBorder(BorderFactory.createLineBorder(Color.RED));
        } else if (type == MainController.REVEALED_CARD) {
            this.revealedCards.get(panel).get(label).setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    public void highlightCard(GUICard card) {
        highlightCard(card.player, card.index, card.type);
    }

    public void unHighlightCard(int panel, int label, int type) {
        if (type == MainController.RUMOUR_CARD) {
            this.rumourCards.get(panel).get(label).setBorder(javax.swing.BorderFactory.createEmptyBorder());
        } else if (type == MainController.REVEALED_CARD) {
            this.revealedCards.get(panel).get(label).setBorder(javax.swing.BorderFactory.createEmptyBorder());
        }
    }

    public void unHighlightCard(GUICard card) {
        unHighlightCard(card.player, card.index, card.type);
    }

    public void highlightPlayer(int player) {
        this.playerLabels.get(player).setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    public void unHighlightPlayer(int player) {
        this.playerLabels.get(player).setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }

    public void setUseHuntButton(boolean enabled) {
        this.useHuntButton.setEnabled(enabled);
    }

    public void setAccusePlayerButton(boolean enabled) {
        this.accusePlayerButton.setEnabled(enabled);
    }

    public void setUseWitchButton(boolean enabled) {
        this.useWitchButton.setEnabled(enabled);
    }

    public void setRevealIdentityButton(boolean enabled) {
        this.revealIdentityButton.setEnabled(enabled);
    }

    public void setDefenseGui() {
        this.accusePlayerButton.setVisible(false);
        this.useHuntButton.setVisible(false);
        this.useWitchButton.setVisible(true);
        this.useWitchButton.setEnabled(false);
        this.revealIdentityButton.setVisible(true);
        this.validateWitchButton.setVisible(false);
        this.validateHuntButton.setVisible(false);
    }

    public void setAttackGui() {
        this.accusePlayerButton.setEnabled(false);
        this.accusePlayerButton.setVisible(true);
        this.useHuntButton.setEnabled(false);
        this.useHuntButton.setVisible(true);
        this.useWitchButton.setVisible(false);
        this.revealIdentityButton.setVisible(false);
        this.validateWitchButton.setVisible(false);
        this.validateHuntButton.setVisible(false);
    }

    public void setHuntValidationGUI() {
        this.accusePlayerButton.setVisible(false);
        this.useHuntButton.setVisible(false);
        this.useWitchButton.setVisible(false);
        this.revealIdentityButton.setVisible(false);
        this.validateWitchButton.setVisible(false);
        this.validateHuntButton.setVisible(true);
        this.validateHuntButton.setEnabled(false);
    }

    public void setWitchValidationGUI() {
        this.accusePlayerButton.setVisible(false);
        this.useHuntButton.setVisible(false);
        this.useWitchButton.setVisible(false);
        this.revealIdentityButton.setVisible(false);
        this.validateWitchButton.setVisible(true);
        this.validateHuntButton.setVisible(false);
        this.validateWitchButton.setEnabled(false);
    }

    public void setHuntValidation(boolean active) {
        this.validateHuntButton.setEnabled(active);
    }

    public void setWitchValidation(boolean active) {
        this.validateWitchButton.setEnabled(active);
    }

    public void addLog(String log) {
        this.logJPane.setText(this.logJPane.getText() + log + "\n");
    }

    public MainController getMainController() {
        return mainController;
    }
}
