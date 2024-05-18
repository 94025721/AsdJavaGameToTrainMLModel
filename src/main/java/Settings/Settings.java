package Settings;

import lombok.Getter;

@Getter
public class Settings {

    private static Settings instance = null;
    private int gameWidth;
    private int gameHeight;

    public Settings(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }

    private Settings() {
        this(800, 600);
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

}