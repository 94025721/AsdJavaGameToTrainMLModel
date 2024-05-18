import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Coin;
import entities.Enemy;
import entities.Level;
import entities.Wall;
import entities.enemyMovement.DiagonalMovementStrategy;
import entities.enemyMovement.HorizontalMovementStrategy;
import entities.enemyMovement.MovementStrategy;
import entities.enemyMovement.VerticalMovementStrategy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LevelLoader {

    public static class EnemyData {
        public int x, y;
        public String movementStrategy;
    }

    public static class LevelData {
        public List<EnemyData> enemies;
        public List<Wall> walls;
        public List<Coin> coins;
        public int spawnX;
        public int spawnY;
    }


    public static List<Level> loadAll() throws IOException {
        List<Level> levels = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        URL url = LevelLoader.class.getResource("/levels/");
        if (url == null) {
            throw new IOException("Resource not found: /levels/");
        }

        try (Stream<Path> paths = Files.walk(Paths.get(url.toURI()))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        try {
                            LevelData data = mapper.readValue(path.toFile(), LevelData.class);
                            Level level = new Level();
                            level.setSpawnX(data.spawnX);
                            level.setSpawnY(data.spawnY);
                            for (EnemyData enemyData : data.enemies) {
                                Enemy enemy = parseEnemyData(enemyData);
                                level.addEnemy(enemy);
                            }
                            for (Wall wall : data.walls) {
                                level.addWall(wall);
                            }
                            for (Coin coin : data.coins) {
                                level.addCoin(coin);
                            }
                            levels.add(level);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return levels;
    }

    private static Enemy parseEnemyData(EnemyData enemyData) {
        MovementStrategy movementStrategy = switch (enemyData.movementStrategy) {
            case "HorizontalMovementStrategy" -> new HorizontalMovementStrategy();
            case "VerticalMovementStrategy" -> new VerticalMovementStrategy();
            case "DiagonalMovementStrategy" -> new DiagonalMovementStrategy();
            default -> throw new IllegalArgumentException("Invalid movement strategy: " + enemyData.movementStrategy);
        };
        return new Enemy(enemyData.x, enemyData.y, movementStrategy);
    }
}