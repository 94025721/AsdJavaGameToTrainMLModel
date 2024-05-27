package game.dqn;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GameState {
    private int playerX, playerY;
    private List<int[]> enemyPositions;
    private int targetX, targetY;
}
