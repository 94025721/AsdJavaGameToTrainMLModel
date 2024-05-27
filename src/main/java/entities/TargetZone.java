package entities;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter @Setter
public class TargetZone implements Entity {

    private final int x, y;
    private final int width, height;

    public TargetZone(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
