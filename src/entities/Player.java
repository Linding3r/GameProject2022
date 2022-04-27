package entities;

import main.Game;
import utilz.LoadSave;
import static utilz.Constants.PlayerConstant.*;
import static utilz.HelpMethods.*;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity{

  private BufferedImage[][] animations;
  private int aniTick, aniIndex, aniSpeed = 25;
  private int playerAction = IDLE;
  private boolean moving = false;
  private boolean attacking = false;
  private boolean left, up, right, down, jump;
  private float playerSpeed = 1.0f * Game.SCALE;
  private int[][] lvlData;
  private float xDrawOffset = 21 * Game.SCALE;
  private float yDrawOffset = 4 * Game.SCALE;

  //Jumping / Gravity
  private float airSpeed = 0f;
  private float gravity = 0.04f * Game.SCALE;
  private float jumpSpeed = -2.25f * Game.SCALE;
  private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
  private boolean inAir = false;


  public Player(float x, float y, int width, int height) {
    super(x, y, width, height);
    loadAnimations();
    initHitBox(x, y, (int)(20*Game.SCALE), (int)(27*Game.SCALE));

  }

  public void update(){
    updatePos();
    updateAnimationTick();
    setAnimation();
  }

  public void render(Graphics g){
    g.drawImage(animations[playerAction][aniIndex],(int)(hitBox.x - xDrawOffset),(int)(hitBox.y - yDrawOffset),width,height, null);
    //drawHitBox(g);

  }


  private void updateAnimationTick() {
    aniTick++;
    if(aniTick >= aniSpeed){
      aniTick = 0;
      aniIndex++;
      if(aniIndex >= GetSpriteAmount(playerAction)){
        aniIndex = 0;
        attacking = false;
      }
    }
  }

  private void updatePos() {

    moving = false;
    if(jump)
      jump();

    if(!left && !right && !inAir)
      return;

    float xSpeed = 0;

    if(left)
      xSpeed -= playerSpeed;
    if(right)
      xSpeed += playerSpeed;
    if(!inAir)
      if(!IsEntityOnFloor(hitBox, lvlData))
        inAir = true;


    if(inAir){
      if(CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)){
        hitBox.y += airSpeed;
        airSpeed += gravity;
        updateXPos(xSpeed);
      } else {
          hitBox.y = GetEntityYPosUnderRoodOrAboveFloor(hitBox, airSpeed);
          if(airSpeed > 0)
            resetInAir();
          else
            airSpeed = fallSpeedAfterCollision;
          updateXPos(xSpeed);
      }
    }else
      updateXPos(xSpeed);
    moving = true;
  }

  private void jump() {
    if(inAir)
      return;
    inAir = true;
    airSpeed = jumpSpeed;
  }

  private void resetInAir() {
    inAir = false;
    airSpeed = 0;
  }

  private void updateXPos(float xSpeed) {
    if(CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
      hitBox.x += xSpeed;
    }else {
      hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);
    }

  }

  private void setAnimation() {
    int startAni = playerAction;
    if(moving)
      playerAction = RUNNING;
    else
      playerAction = IDLE;
    if(inAir){
      if(airSpeed < 0)
        playerAction = JUMPING;
      else
        playerAction = FALLING;
    }
    if(attacking)
      playerAction = ATTACK_1;
    if(startAni != playerAction)
      resetAniTick();
  }

  public void resetAniTick(){
    aniTick = 0;
    aniIndex = 0;
  }

  private void loadAnimations() {
      BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
      animations = new BufferedImage[9][6];
      for (int j = 0; j < animations.length; j++)
        for (int i = 0; i < animations[j].length; i++)
          animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
  }

  public void loadLvlData(int[][] lvlData){
    this.lvlData = lvlData;
    if(!IsEntityOnFloor(hitBox, lvlData))
      inAir = true;
  }

  public void resetDirBooleans(){
    left = false;
    right = false;
    up = false;
    down = false;
  }

  public void setAttacking(boolean attacking){
    this.attacking = attacking;
  }

  public boolean isLeft() {
    return left;
  }

  public void setLeft(boolean left) {
    this.left = left;
  }

  public boolean isUp() {
    return up;
  }

  public void setUp(boolean up) {
    this.up = up;
  }

  public boolean isRight() {
    return right;
  }

  public void setRight(boolean right) {
    this.right = right;
  }

  public boolean isDown() {
    return down;
  }

  public void setDown(boolean down) {
    this.down = down;
  }
  public void setJump(Boolean jump){
    this.jump = jump;
  }
}
