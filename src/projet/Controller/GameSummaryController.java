package projet.Controller;

import projet.Model.Game;
import projet.Model.player.Player;

import java.util.ArrayList;

public final class GameSummaryController {
    private final ArrayList<Player> gameSummary;

    public GameSummaryController(Game game) {
        gameSummary = new ArrayList<>();
        gameSummary.addAll(game.getPlayers());
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == 0) {
            return new String[]{"Joueurs", "identité", "points"}[columnIndex];
        }
        Player p = this.gameSummary.get(rowIndex - 1);
        return switch (columnIndex) {
            case 0 -> p.getName();
            case 1 -> p.isRevealed() || p.isCurrentPlayer() ? p.printIdentity() : "?";
            case 2 -> p.getPoints();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    public int getLines() {
        return this.gameSummary.size() + 1;
    }
}
