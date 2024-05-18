package entities.enemyMovement;

import entities.Enemy;

public class DiagonalMovementStrategy implements MovementStrategy {

    @Override
    public void move(Enemy enemy ) {
        int speed = enemy.getSpeed();
        enemy.setX(enemy.getX() + speed);
        enemy.setY(enemy.getY() + speed);
    }
}
