package game.dqn;

import entities.Level;
import entities.Player;
import game.Game;

public class GameEnvironment {
    private Game game;
    private Player player;
    private Level level;

    public GameEnvironment(Game game) {
        this.game = game;
        this.player = game.getPlayer();
        this.level = game.getCurrentLevel();
    }

    // Method to reset the environment to the initial state
    public void reset() {
        game.reset();
    }

    // Method to get the current state of the environment
    public double[] getState() {
        // Normalize state (player position, enemies positions, target position)
        double playerX = player.getX() / (double) Settings.getInstance().getGAME_WIDTH();
        double playerY = player.getY() / (double) Settings.getInstance().getGAME_HEIGHT();
        // Add more state variables as needed

        return new double[] { playerX, playerY }; // Extend this array
    }

    // Method to take an action and return the reward and next state
    public double[] step(int action) {
        // Map action to player movement
        switch(action) {
            case 0: player.setDx(-2); player.setDy(0); break; // Move left
            case 1: player.setDx(2); player.setDy(0); break; // Move right
            case 2: player.setDx(0); player.setDy(-2); break; // Move up
            case 3: player.setDx(0); player.setDy(2); break; // Move down
        }

        game.update();

        // Calculate reward
        double reward = -0.01; // Small negative reward for each step
        if (player.isFinished()) {
            reward = 1.0; // Positive reward for reaching the target
        } else if (player.hasCollidedWithEnemy() || player.hasCollidedWithWall()) {
            reward = -1.0; // Negative reward for collision
        }

        return new double[] { reward, getState() };
    }

    // Method to check if the game is finished
    public boolean isFinished() {
        return player.isFinished();
    }
}
