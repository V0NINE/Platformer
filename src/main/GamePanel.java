package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Dimension;

import static main.Game.GAME_WIDTH;
import static main.Game.GAME_HEIGHT;;
 
public class GamePanel extends JPanel{

    private Game game;
    private MouseInputs mouse;

    public GamePanel(Game game) {
        this.game = game;

        this.mouse = new MouseInputs(this);

        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouse);                        
        addMouseMotionListener(mouse);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);

        System.out.println("[+] SCREEN SIZE: " + GAME_WIDTH + "x" + GAME_HEIGHT + "\n");
    }

    public void updateGame() {}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
