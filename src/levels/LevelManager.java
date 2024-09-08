package levels;

import static main.Game.TILES_SIZE;
import static utils.LoadSave.LEVEL_ATLAS;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class LevelManager {
    
    private Game game;
    private Level levelOne;
    private BufferedImage[] levelSprite;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.GetLevelData());
    }

    public void importOutsideSprites() {
        int index;

        BufferedImage img = LoadSave.GetSpriteAtlas(LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];

        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 12; j++)
            {
                index = i*12 + j;
                levelSprite[index] = img.getSubimage(j*32, i*32, 32, 32);
            }
    }

    public void draw(Graphics g) {
        int index;

        for(int i = 0; i < Game.TILES_IN_HEIGHT; i++)
            for(int j = 0; j < Game.TILES_IN_WIDTH; j++)
            {
                index = levelOne.getSpriteIndex(j, i);
                g.drawImage(levelSprite[index], TILES_SIZE*j, TILES_SIZE*i, TILES_SIZE, TILES_SIZE, null);
            }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levelOne;
    }
}
