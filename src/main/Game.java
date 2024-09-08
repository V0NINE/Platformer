package main;

import entities.Player;
import levels.LevelManager;

import java.awt.Graphics;

public class Game implements Runnable{

    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 1.5f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_IN_WIDTH * TILES_SIZE;
    public static final int GAME_HEIGHT = TILES_IN_HEIGHT * TILES_SIZE;

    @SuppressWarnings("unused")
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Player player;
    private LevelManager levelManager;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    public Game(){
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        levelManager = new LevelManager(this);
        player = new Player(250, 200, (int)(64*SCALE), (int)(40*SCALE));
            player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
    }

    private void startGameLoop() 
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
        levelManager.update();
    }

    public void render(Graphics g) {
        levelManager.draw(g);
        player.render(g);
    }

    public void windowFocusLost() {
        player.resetBooleans();
    }

    public void run() 
    {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timPerUpdate = 1000000000.0 / UPS_SET;
        long currentTime, previousTime = System.nanoTime();

        int frames = 0, updates = 0;
        double deltaU = 0, deltaF = 0;

        long lastChecked = System.currentTimeMillis();

        while (true) 
        {
            currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timPerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaU >= 1)
            {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) 
            {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if(System.currentTimeMillis() - lastChecked >= 1000){
                lastChecked = System.currentTimeMillis();
                System.out.println("FPS " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }
}
