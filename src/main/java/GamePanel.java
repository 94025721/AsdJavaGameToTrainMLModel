import Settings.Settings;
import entities.Coin;
import entities.Enemy;
import entities.Player;
import entities.Wall;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Setter
public class GamePanel extends JPanel implements GameObserver {
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(Settings.getInstance().getGameWidth(), Settings.getInstance().getGameHeight()));
        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocusInWindow();
    }

    public GamePanel() {
        this(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
    }

    private void drawGame(Graphics g) {
        Player player = game.getPlayer();
        ArrayList<Enemy> enemies = game.getEnemies();
        ArrayList<Wall> walls = game.getWalls();
        ArrayList<Coin> coins = game.getCoins();

        g.setColor(Color.RED);
        g.fillOval(player.getX(), player.getY(), 20, 20);

        g.setColor(Color.BLUE);
        for (Enemy enemy : enemies) {
            g.fillOval(enemy.getX(), enemy.getY(), 20, 20);
        }

        g.setColor(Color.GRAY);
        for (Wall wall : walls) {
            g.fillRect(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
        }

        g.setColor(Color.YELLOW);
        for (Coin coin : coins) {
            g.fillOval(coin.getX(), coin.getY(), 10, 10);
        }
    }

    @Override
    public void update() {
        repaint();
    }

}
