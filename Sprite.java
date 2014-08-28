/* Sprite.java
 * Scratching  -- Scratch for Processing
 *
 * This file seeks to implement Scratch blocks and sprites in
 * Processing, in order to facilitate a transition from Scratch
 * into p.
 * See: http://wiki.scratch.mit.edu/wiki/Blocks
 *
 * Sound blocks are NOT included (for sanity's sake). 
 * Data & list blocks are eradicated - use variables instead!
 *
 * Points are stored in the 'PVector' type because Processing
 * contains built-in functions for accessing and manipulating such
 * objects.
 *
 * Avoid changing this file in any way! Do not use the Sprite class!
 * Instead, make a new tab and make a new .java file with a new name.
 * Copy the contents of this to your new tab and extend the code there.
 * This way you will always have a fresh template to start new objects.
 *
 */

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.core.PGraphics;

import java.util.ArrayList;


public class Sprite {

  // without this, built-in functions are broken. use p.whatever to access functionality
  PApplet p;
  static int rotationStyle_allAround=0;
  static int rotationStyle_leftRight=1;
  static int rotationStyle_dontRotate=2;
  public int rotationStyle;
  public int costumeNumber, numberOfCostumes;
  public int ghostEffect;
  public float size; 
  public boolean visible;
  public ArrayList<PImage> costumes = new ArrayList<PImage>();
  public PVector pos = new PVector(0, 0);
  public boolean penDown;
  public float lineOfSight = 180;
  public PGraphics penLayer;
  boolean localPenLayer = false;

  /* DIRECTION IS IN DEGREES! any math will require conversion.
   * This for end-user simplicity.
   * Use degrees() to convert to degrees; radians() to convert to
   * radians.
   */
  public float direction = 0;
  Sprite (PApplet parent) {
    p = parent;
    costumeNumber=0;
    visible = true;
    numberOfCostumes=0;
    size=100;
    rotationStyle=rotationStyle_leftRight;
    ghostEffect=0;
    penLayer = p.createGraphics(p.width,p.height);
    p.imageMode(p.CENTER);
  }

  /* ==== Drawing ====
   * 
   * In order to draw sprites to the screen, each one will have
   * an Image object. This image object can be individually
   * manipulated during the program.
   *
   * The .update() function must be called for all sprites.
   * It may be easiest to store sprites in an array of Sprites,
   * and looping through the array to redraw all sprites.
   */
  public void draw() {
    p.translate(pos.x, pos.y);    
    if (localPenLayer) p.image(penLayer.get(0,0,p.width,p.height),p.width/2-pos.x, p.height/2-pos.y);
    if (visible) {
      p.pushMatrix(); // save old visual style for other sprites
      // locked left-right rotation
      if (((direction%360<=270) & (direction%360>=90)) & rotationStyle==rotationStyle_leftRight) p.scale(-1.0f,1.0f);
      if (rotationStyle==rotationStyle_allAround) p.rotate(p.radians(-direction));
      if (ghostEffect > 0) {
        int calculatedAlpha = (int)p.map(ghostEffect,100,0,0,255);
        
        int[] alpha = new int[costumes.get(costumeNumber).width*costumes.get(costumeNumber).height];
        for (int i=0; i<alpha.length; i++) {
          // only fade non-zero pixels; 0 is full-transparency
          if (costumes.get(costumeNumber).pixels[i]!=0) alpha[i]=calculatedAlpha;
        }
        costumes.get(costumeNumber).mask(alpha);
      }
      p.image(costumes.get(costumeNumber), 0, 0, costumes.get(costumeNumber).width*(size/100),
        costumes.get(costumeNumber).height*(size/100));
        
      p.popMatrix(); // restore default visual style
    }
  }

  // set transparency effect
  public void setGhostEffect(int newAlpha) {
    ghostEffect = newAlpha;
  }

  public void move(int distance) {
    /* Create a new vector, representing the desired motion (angle + distance) 
     * fromAngle() makes a unit vector (length 1)
     * negative on direction is b/c processing flips the cartesian y axis
     */
     float oldX=0, oldY=0;
     if (penDown) {
       oldX = pos.x; oldY = pos.y;
     }
     PVector temp = PVector.fromAngle(p.radians(-direction));
     temp.mult(distance);
     pos.add(temp);
     if (penDown) {
       penLayer.beginDraw();
       penLayer.line(oldX,oldY,pos.x,pos.y);
       penLayer.endDraw();
     }
  }

  // load "Scratch" cat costumes
  public void addDefaultCostumes() {
    addCostume("images/cat.costume1.png");
    addCostume("images/cat.costume2.png");
  }

  // add costume from bitmap image file
  public void addCostume(String filePath) {
    numberOfCostumes++;
    costumes.add(p.loadImage(filePath));
  }

  // change to next costume
  public void nextCostume() { 
    costumeNumber++;
    if (costumeNumber > numberOfCostumes-1) costumeNumber=0;
  }

  // change to previous costume
  public void previousCostume() {
    costumeNumber--;
    if (costumeNumber < 0) costumeNumber=numberOfCostumes-1;
  }

  // switch to specific costume
  public void setCostume(int newCostumeNumber) {
    costumeNumber=newCostumeNumber;
  }

  // set "visible" variable to make sprite appear
  public void show() {
    visible=true;
  }

  // set not visible to hide
  public void hide() {
    visible=false;
  }

  public void say(String what) { 
    p.print("\""); 
    p.print(what); 
    p.println("\"");
  }

  public void think(String what) { 
    p.print(". o O ("); 
    p.print(what); 
    p.println(")");
  }

  // turn any angle
  public void turn(float angle) {
    direction += angle;
    if (direction>360) direction=direction-360;
    else if (direction<0) direction=direction+360;
  }

  // turn right
  public void turnLeft(float angle) {
    direction += angle;
    if (direction>360) direction=direction-360;
  }

  // turn left
  public void turnRight(float angle) { 
    direction -= angle;
    if (direction<0) direction=direction+360;
  }

  // point towards arbitrary grid position
  public void pointTowardsXY(int x, int y) {
    PVector targetVector;
    targetVector = new PVector(x, y);
    direction = (p.degrees(p.atan2(pos.x - (targetVector.x), pos.y - (targetVector.y))))+90;
  }

  // absolute heading
  public void pointInDirection(float angle) {
    direction = angle;
  }

  /* Sets the direction to point towards another Sprite. */
  public void pointTowards(Sprite target) {
    pointTowardsXY((int)target.pos.x,(int)target.pos.y);
  }

  /* Same as above, but for mouse. */
  public void pointTowardsMouse() {
    pointTowardsXY(p.mouseX,p.mouseY);
  }

  /* move to specific location on grid */
  public void goToXY(float x, float y) { 
    pos.x = x; pos.y = y;
  }

  // move to position of Sprite object
  public void goToSprite(Sprite target) { 
    pos.x=target.pos.x; 
    pos.y=target.pos.y;
  }

  // check if a Sprite is touching another Sprite using simple rectangular hit box
  public boolean touchingSprite(Sprite target) {
    if (!visible || !target.visible) return false;
    boolean touchingX, touchingY;
    PVector testVector;
    touchingX=false; 
    touchingY=false;
    testVector=new PVector(target.pos.x, pos.y);
    if (pos.dist(testVector) < ((target.costumes.get(target.costumeNumber).width*(target.size/100))/2)+(costumes.get(costumeNumber).width*(size/100))/2) {
      touchingX = true;
    }
    testVector=new PVector(pos.x, target.pos.y);
    if (pos.dist(testVector) < ((target.costumes.get(target.costumeNumber).height*(target.size/100))/2)+(costumes.get(costumeNumber).height*(size/100))/2) {
      touchingY = true;
    }
    if (touchingX & touchingY) return true;
    else return false;
  }

  // return distance to arbitrary grid position  
  public float distanceToXY(int x, int y) { 
    PVector temp = new PVector(x, y);
    return pos.dist(temp); 
  }

  // return distance to arbitrary grid position  
  public float distanceToMouse() { 
    PVector temp = new PVector(p.mouseX, p.mouseY);
    return pos.dist(temp);
  }

  // return distance to Sprite object
  public float distanceToSprite(Sprite target) { 
    return distanceToXY((int)target.pos.x, (int)target.pos.y);
  }
  
   
  void wrapAtEdges() {
    if (pos.x>p.width) pos.x -= p.width;
    if (pos.x<0) pos.x += p.width;
    if (pos.y>p.height) pos.y -= p.height;
    if (pos.y<0) pos.y += p.height;
  }  
  
  PVector vectorForSpeed(float distance) {
    PVector i = PVector.fromAngle(p.radians(-direction));
    PVector j = new PVector(pos.x,pos.y);
    i.mult(distance);
    j.add(i);
    j.x = j.x - pos.x;
    j.y = j.y - pos.y;
    return j;
  }
  
  float directionTowards(Sprite target) {
    PVector temp = new PVector(target.pos.x,target.pos.y);
    float a = (p.degrees(p.atan2(pos.x - (target.pos.x), pos.y - (target.pos.y))))+90;
    if (a < 0) a += 360;
    return a;
    //return p.atan2(pos.y - target.pos.y,pos.x - target.pos.x);
  }

  boolean facingSprite(Sprite target) {
    float directionTo = directionTowards(target); //direction to other sprite
    //p.println("1: "+direction+" 2: "+directionTo);
    if (direction+(lineOfSight/2) > directionTo && direction-(lineOfSight/2) < directionTo) return true;
    else return false;
  }
  
  public void drawOnStage(Stage stage) {
    penLayer = stage.penLayer;
    localPenLayer = false;
  }


  public void penColor(int r, int g, int b) {
    penLayer.beginDraw();
    penLayer.stroke(p.color(r,g,b));
    penLayer.endDraw();
  }
  
    
  public void penWidth(int penWidth) {
    penLayer.beginDraw();
    penLayer.strokeWeight(penWidth);
    penLayer.endDraw();
  }
  
  public void penClear() {
    penLayer.clear();
  }
  
  public void drawOwnPen() {
    penLayer = p.createGraphics(p.width,p.height);
    localPenLayer = true;
  }
}
