package entities;

import java.util.ArrayList;
import Settings.Settings;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Level {
    private ArrayList<Coin> coins;
    private ArrayList<Enemy> enemies;
    private ArrayList<Wall> walls;
    private int spawnX, spawnY;

    public Level() {
        this.spawnX = 50;
        this.spawnY = 50;
        this.enemies = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.coins = new ArrayList<>();
        createSurroundingWalls();
    }
    
    public Level(int spawnX, int spawnY) {
        this();
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }
    
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public void addWall(Wall wall) {
        walls.add(wall);
    }

    public void removeWall(Wall wall) {
        walls.remove(wall);
    }

    private void createSurroundingWalls() {
        int gameWidth = Settings.getInstance().getGameWidth();
        int gameHeight = Settings.getInstance().getGameHeight();
        System.out.println("gameWidth: " + gameWidth + " gameHeight: " + gameHeight);


        walls.add(new Wall(0, 0, gameWidth, 20));
        walls.add(new Wall(0, 0, 20, gameHeight));
        walls.add(new Wall(0, gameHeight - 20, gameWidth, 20));
        walls.add(new Wall(gameWidth - 20, 0, 20, gameHeight));
    }

    public void addCoin(Coin coin) {
        coins.add(coin);
    }
}