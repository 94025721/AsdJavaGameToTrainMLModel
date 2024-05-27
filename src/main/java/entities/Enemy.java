package entities;

import entities.enemyMovement.MovementStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Getter @Setter
@NoArgsConstructor
public class Enemy implements Entity {

    private final int height = 15;
    private final int width = 15;
    private int x;
    private int y;
    private int speed;
    private int moves;
    private int moveCount = 0;
    private MovementStrategy movementStrategy;

    public Enemy(int x, int y, MovementStrategy movementStrategy, int speed, int moves) {
        this.movementStrategy = movementStrategy;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.moves = moves;
    }

    public void move() {
        movementStrategy.move(this);
        this.moveCount++;
        if (this.moveCount == this.moves) {
            this.speed *= -1;
            this.moveCount = 0;
        }
    }

    public void undoMove() {
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
