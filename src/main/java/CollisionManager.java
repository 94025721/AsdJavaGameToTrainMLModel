import entities.*;

import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

    public void handlePlayerMovement(Player player, Level currentLevel) {
        player.move();
        for (Wall wall : currentLevel.getWalls()) {
            if (player.getBounds().intersects(wall.getBounds())) {
                player.undoMove();
            }
        }
    }

    public void handleEnemyMovement(Player player, Level currentLevel) {
        QuadTree enemyQuadTree = buildQuadTree(currentLevel.getEnemies(), currentLevel.getWalls());

        List<Rectangle> returnObjects = new ArrayList<>();
        for (Enemy enemy : currentLevel.getEnemies()) {
            enemy.move();
            if (checkCollisionWithWalls(enemy, currentLevel.getWalls())) {
                enemy.undoMove();
            }
            if (checkCollisionWithEnemies(enemy, enemyQuadTree, returnObjects)) {
                enemy.undoMove();
            }
            if (enemy.getBounds().intersects(player.getBounds())) {
                handlePlayerDeath(player, currentLevel);
                break;
            }
        }
    }

    public void handleCoinCollection(Player player, Level currentLevel) {
        QuadTree coinQuadTree = buildQuadTree(currentLevel.getCoins(), null);

        List<Rectangle> returnObjects = new ArrayList<>();
        List<Coin> coinsToRemove = new ArrayList<>();
        for (Coin coin : currentLevel.getCoins()) {
            returnObjects.clear();
            coinQuadTree.retrieve(returnObjects, coin.getBounds());

            for (Rectangle rect : returnObjects) {
                if (rect.intersects(player.getBounds()) && rect.equals(coin.getBounds())) {
                    player.incrementCoinsCollected();
                    coinsToRemove.add(coin);
                    break;
                }
            }
        }
        currentLevel.getCoins().removeAll(coinsToRemove);
    }

    private QuadTree buildQuadTree(ArrayList<? extends Entity> entities, ArrayList<Wall> walls) {
        QuadTree quadTree = new QuadTree(0, new Rectangle(0, 0, Settings.getInstance().getGameWidth(), Settings.getInstance().getGameHeight()));
        for (Entity entity : entities) {
            quadTree.insert(entity.getBounds());
        }
        if (walls != null) {
            for (Wall wall : walls) {
                quadTree.insert(wall.getBounds());
            }
        }
        return quadTree;
    }

    private boolean checkCollisionWithWalls(Entity entity, List<Wall> walls) {
        for (Wall wall : walls) {
            if (entity.getBounds().intersects(wall.getBounds())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCollisionWithEnemies(Enemy enemy, QuadTree enemyQuadTree, List<Rectangle> returnObjects) {
        enemyQuadTree.retrieve(returnObjects, enemy.getBounds());
        for (Rectangle rect : returnObjects) {
            if (!rect.equals(enemy.getBounds()) && rect.intersects(enemy.getBounds())) {
                return true;
            }
        }
        return false;
    }

    private void handlePlayerDeath(Player player, Level currentLevel) {
        player.respawn(currentLevel.getSpawnX(), currentLevel.getSpawnY());
        player.incrementPlayerDeaths();
    }
}
