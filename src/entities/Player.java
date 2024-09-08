package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.CanMoveHere;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int animationTick = 0, animationIndex = 0, animationSpeed = 20;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, right, up, down;
    private float playerSpeed = 1.0f;
    private int[][] lvlData;
    private float xDrawOffset = 21*Game.SCALE, yDrawOffset = 4*Game.SCALE;

    private float airSpeed = -1.3f;
    private float gravity = 0.04f;
    private boolean jump;
    private boolean onAir = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 20*Game.SCALE, 28*Game.SCALE);
    }
    
    public void update() {
        updatePosition();
        updateAnimationTick();
        setAction();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int)(hitbox.x-xDrawOffset), (int)(hitbox.y-yDrawOffset), width, height, null);
        drawHitbox(g);
    }

    public void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[9][6];

        for(int i = 0; i < animations.length; i++)
            for(int j = 0; j < animations[i].length; j++)
                animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData; 
    }

    public void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            
            if(animationIndex >= GetSpriteAmount(playerAction))
            {
                animationIndex = 0;
                attacking = false;
                jump = false;
            }
        }
    }

    public void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    public void updatePosition() {
        moving = false;
                
        if(onAir == false)
            airSpeed = -1.4f; 

        if(!left && !right && !jump)
            return;

        float xSpeed = 0;

        if(right && !left)
            xSpeed += playerSpeed;
        else if(left && !right)
            xSpeed -= playerSpeed;

        if(jump) 
            onAir = true;

        if(onAir && CanMoveHere(hitbox.x+xSpeed, hitbox.y+airSpeed-gravity, hitbox.width, hitbox.height, lvlData))
        {
            hitbox.y += airSpeed;
            airSpeed += gravity;
        } else 
        {
            onAir = false;
        }

        // if(CanMoveHere(hitbox.x+xSpeed, hitbox.y+ySpeed, hitbox.width, hitbox.height, lvlData))
        // {
        //     hitbox.x += xSpeed;
        //     hitbox.y += ySpeed;
        //     moving = true;
        // }

    }

    public void setAction() {
        int startAnimation = playerAction;

        if(moving)
            this.playerAction = RUNNING;
        else
            this.playerAction = IDLE;

        if(attacking)
            this.playerAction = ATTACK_1;

        if(jump)
            this.playerAction = JUMP;

        if(startAnimation != playerAction)
            resetAnimationTick();
    }

    public void resetBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
        jump = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setJumping(boolean jump) {
        this.jump = jump;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
