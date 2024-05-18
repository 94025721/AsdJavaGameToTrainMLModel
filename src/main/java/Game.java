import entities.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Game implements ActionListener {

    private Timer timer;

    @Getter
    private Player player;

    private GameObserver gameObserver;

    private ArrayList<Level> levels;
    private int currentLevelIndex;

    @Setter @Getter
    private GameState gameState;

    private final CollisionManager collisionManager;

    public Game(GameObserver gameObserver) {
        this.levels = new ArrayList<>();
        this.gameObserver = gameObserver;
        this.player = new Player();
        this.currentLevelIndex = 0;
        this.gameState = GameState.RUNNING;
        this.collisionManager = new CollisionManager();
        setupGame();

        this.timer = new Timer(60, this);
        this.timer.start();
    }

    private void setupGame() {
        try {
            List<Level> levels = LevelLoader.loadAll();
            for (Level level : levels) {
                addLevel(level);
            }
            if (!levels.isEmpty()) {
                player.respawn(levels.get(currentLevelIndex).getSpawnX(), levels.get(currentLevelIndex).getSpawnY());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (gameState == GameState.RUNNING) {
            collisionManager.handlePlayerMovement(player, levels.get(currentLevelIndex));
            collisionManager.handleEnemyMovement(player, levels.get(currentLevelIndex));
            collisionManager.handleCoinCollection(player, levels.get(currentLevelIndex));
            gameObserver.update();
        }
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public void nextLevel() {
        if (currentLevelIndex < levels.size() - 1) {
            currentLevelIndex++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        gameObserver.update();
    }

    public ArrayList<Enemy> getEnemies() {
        return levels.get(currentLevelIndex).getEnemies();
    }

    public ArrayList<Wall> getWalls() {
        return levels.get(currentLevelIndex).getWalls();
    }

    public ArrayList<Coin> getCoins() {
        return levels.get(currentLevelIndex).getCoins();
    }

    public enum GameState {
        RUNNING,
        PAUSED,
        GAME_OVER
    }
}
