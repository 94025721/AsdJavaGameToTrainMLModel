package settings;

import lombok.Getter;

@Getter
public class Settings {

    private static Settings instance = null;
    private final int GAME_WIDTH = 1000, GAME_HEIGHT = 500;

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }
}