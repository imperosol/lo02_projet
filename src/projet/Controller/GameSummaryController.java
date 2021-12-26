package projet.Controller;

import projet.Model.Game;
import projet.Model.player.Player;

import java.util.ArrayList;

public class GameSummaryController {
    private final ArrayList<Player> gameSummary;

    public GameSummaryController(Game game) {
        gameSummary = new ArrayList<>();
        gameSummary.addAll(game.getPlayers());
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == 0) {
            return new String[]{"Joueurs", "identité", "points"}[columnIndex];
        }
        return switch (columnIndex) {
            case 0 -> this.gameSummary.get(rowIndex - 1).getName();
            case 1 -> this.gameSummary.get(rowIndex - 1).isRevealed() ? this.gameSummary.get(rowIndex - 1).printIdentity() : "?";
            case 2 -> this.gameSummary.get(rowIndex - 1).getPoints();
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    public int getLines() {
        return this.gameSummary.size();
    }
}
