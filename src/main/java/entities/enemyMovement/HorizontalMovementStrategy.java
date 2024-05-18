package entities.enemyMovement;

import entities.Enemy;

public class HorizontalMovementStrategy implements MovementStrategy{

    @Override
    public void move(Enemy enemy) {
        enemy.setX(enemy.getX() + enemy.getSpeed());
    }
}
