package entities;

import java.util.ArrayList;

import settings.Settings;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Level {
    private ArrayList<Coin> coins;
    private ArrayList<Enemy> enemies;
    private ArrayList<Wall> walls;
    private TargetZone targetZone;
    private int spawnX, spawnY;

    public Level() {
        this.spawnX = 50;
        this.spawnY = 50;
        this.enemies = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.coins = new ArrayList<>();
        createSurroundingWalls();
    }
    
    private void createSurroundingWalls() {
        int gameWidth = Settings.getInstance().getGAME_WIDTH();
        int gameHeight = Settings.getInstance().getGAME_HEIGHT();

        walls.add(new Wall(0, 0, gameWidth, 20));
        walls.add(new Wall(0, 0, 20, gameHeight));
        walls.add(new Wall(0, gameHeight - 20, gameWidth, 20));
        walls.add(new Wall(gameWidth - 20, 0, 20, gameHeight));
    }

    public void addCoin(Coin coin) {
        coins.add(coin);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addWall(Wall wall) {
        walls.add(wall);
    }
}