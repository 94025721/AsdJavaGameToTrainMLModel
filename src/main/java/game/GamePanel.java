package game;

import entities.TargetZone;
import settings.Settings;
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

    public GamePanel() {
        setPreferredSize(new Dimension(Settings.getInstance().getGAME_WIDTH(), Settings.getInstance().getGAME_HEIGHT()));
        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocusInWindow();
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
        TargetZone targetZone = game.getTargetZone();

        g.setColor(Color.GREEN);
        g.fillRect(targetZone.getX(), targetZone.getY(), targetZone.getWidth(), targetZone.getHeight());

        g.setColor(Color.RED);
        g.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());

        g.setColor(Color.BLUE);
        for (Enemy enemy : enemies) {
            g.fillRect(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
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
