package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;

@Getter
@NoArgsConstructor
public class Coin implements Entity {
    private int x, y;

    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }
}
