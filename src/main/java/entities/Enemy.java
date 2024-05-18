package entities;

import entities.enemyMovement.MovementStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Getter @Setter
@NoArgsConstructor
public class Enemy implements Entity {

    private int x, y;
    private int speed;
    private int moveCount = 0;
    private MovementStrategy movementStrategy;

    public Enemy(int x, int y, MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
        this.x = x;
        this.y = y;
        this.speed = (int) (Math.random() * 5) + 1;
    }

    public void move() {
        movementStrategy.move(this);
        moveCount++;
        if (moveCount == 50) {
            speed *= -1;
            moveCount = 0;
        }
    }

    public void undoMove() {
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }
}
