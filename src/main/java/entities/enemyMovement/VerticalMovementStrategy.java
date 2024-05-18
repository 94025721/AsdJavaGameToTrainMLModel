package entities.enemyMovement;

import entities.Enemy;

public class VerticalMovementStrategy implements MovementStrategy {

    @Override
    public void move(Enemy enemy) {
        enemy.setY(enemy.getY() + enemy.getSpeed());
    }
}
