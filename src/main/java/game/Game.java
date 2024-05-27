package game;

import entities.*;
import entities.TargetZone;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Game implements ActionListener {

    private final Timer timer;
    private final CollisionManager collisionManager;
    private final GameObserver gameObserver;
    private boolean finished = false;

    private final ArrayList<Level> levels = new ArrayList<>();
    private int currentLevelIndex;

    @Getter
    private final Player player;

    @Setter @Getter
    private GameState gameState;

    public Game(GameObserver gameObserver) {
        this.gameObserver = gameObserver;
        this.player = new Player();
        this.currentLevelIndex = 0;
        this.gameState = GameState.RUNNING;
        this.collisionManager = new CollisionManager();
        setupGame();

        this.timer = new Timer(16, this);
        this.timer.start();
    }

    private void setupGame() {
        try {
            List<Level> loadedLevels = LevelLoader.loadAll();
            levels.addAll(loadedLevels);
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
            if(player.isFinished()) {
                gameState = GameState.GAME_OVER;
            }
            gameObserver.update();
        } else if (gameState == GameState.GAME_OVER) {
            timer.stop();
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

    public TargetZone getTargetZone() {
        return levels.get(currentLevelIndex).getTargetZone();
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public enum GameState {
        RUNNING,
        PAUSED,
        GAME_OVER
    }
}
