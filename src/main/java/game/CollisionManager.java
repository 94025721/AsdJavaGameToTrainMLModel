package game;

import entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import settings.Settings;

public class CollisionManager {

    public void handlePlayerMovement(Player player, Level currentLevel) {
        player.move();
        currentLevel.getWalls().stream()
                .filter(wall -> player.getBounds().intersects(wall.getBounds()))
                .findFirst()
                .ifPresent(wall -> player.undoMove());
    }

    public void handleEnemyMovement(Player player, Level currentLevel) {
        QuadTree enemyQuadTree = buildQuadTree(currentLevel.getEnemies(), currentLevel.getWalls());

        List<Rectangle> returnObjects = new ArrayList<>();
        for (Enemy enemy : currentLevel.getEnemies()) {
            enemy.move();
            if (checkCollisionWithWalls(enemy, currentLevel.getWalls()) ||
                    checkCollisionWithEnemies(enemy, enemyQuadTree, returnObjects) ||
                    enemy.getBounds().intersects(player.getBounds())) {
                enemy.undoMove();
                if (enemy.getBounds().intersects(player.getBounds())) {
                    handlePlayerDeath(player, currentLevel);
                    break;
                }
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
            if (returnObjects.stream().anyMatch(rect -> rect.intersects(player.getBounds()) && rect.equals(coin.getBounds()))) {
                player.incrementCoinsCollected();
                coinsToRemove.add(coin);
            }
        }
        currentLevel.getCoins().removeAll(coinsToRemove);
    }

    private QuadTree buildQuadTree(List<? extends Entity> entities, List<Wall> walls) {
        QuadTree quadTree = new QuadTree(0, new Rectangle(0, 0, Settings.getInstance().getGameWidth(), Settings.getInstance().getGameHeight()));
        entities.forEach(entity -> quadTree.insert(entity.getBounds()));
        if (walls != null) {
            walls.forEach(wall -> quadTree.insert(wall.getBounds()));
        }
        return quadTree;
    }

    private boolean checkCollisionWithWalls(Entity entity, List<Wall> walls) {
        return walls.stream().anyMatch(wall -> entity.getBounds().intersects(wall.getBounds()));
    }

    private boolean checkCollisionWithEnemies(Enemy enemy, QuadTree enemyQuadTree, List<Rectangle> returnObjects) {
        enemyQuadTree.retrieve(returnObjects, enemy.getBounds());
        return returnObjects.stream().anyMatch(rect -> !rect.equals(enemy.getBounds()) && rect.intersects(enemy.getBounds()));
    }

    private void handlePlayerDeath(Player player, Level currentLevel) {
        player.respawn(currentLevel.getSpawnX(), currentLevel.getSpawnY());
        player.incrementPlayerDeaths();
    }
}
