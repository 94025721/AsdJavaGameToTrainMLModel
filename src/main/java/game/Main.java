package game;

import settings.Settings;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simple 2D game.Game");
            GamePanel gamePanel = new GamePanel();
            Game game = new Game(gamePanel);
            gamePanel.setGame(game);
            frame.add(gamePanel);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            gamePanel.addKeyListener(game.getPlayer());
            System.out.println("Game width = " + Settings.getInstance().getGAME_WIDTH() + "\n" + "Game height = " + Settings.getInstance().getGAME_HEIGHT());
        });
    }

}