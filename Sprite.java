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
 */

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import java.util.ArrayList;

public class Sprite {

  static final int rotationStyle_Free=0;
  static final int rotationStyle_LeftRight=1;
  static final int rotationStyle_noRotation=2;
  //public PVector globOrigin = new PVector();

  PApplet p;

  public int rotationStyle;
  public int costumeNumber, numberOfCostumes;
  public float size; 
  public boolean visible;
  public int penColor, penSize; 
  public boolean penUp;
  public ArrayList<PImage> costumes = new ArrayList<PImage>();
  public PVector pos = new PVector(0, 0);
  public float spin = 0;
  
  /* DIRECTION IS IN DEGREES! any math will require conversion.
   * This for end-user simplicity.
   * Use degrees() to convert to degrees; radians() to convert to
   * radians.
   */
  public float direction = 0;
  // add images as costumes;
  //costumes = ;

  // without this, built-in functions are broken. use p.whatever to access functionality
  Sprite (PApplet parent) {
    p = parent;
    loadDefaultCostumes();
    costumeNumber=0;
    visible = true;
    numberOfCostumes=0;
    size=100;
    //rotationStyle=rotationStyle_LeftRight;
    rotationStyle=rotationStyle_Free;
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
  public void update() {    
    if (visible) {
      p.pushMatrix(); // save old visual style for other sprites
      // set the center of the screen to (0, 0)
      p.translate((p.width/2)+pos.x, (p.height/2)+pos.y);    
    
      p.print("x "); p.print(pos.x); p.print(" y "); p.println(pos.y);
      
      p.imageMode(p.CENTER);
      // locked left-right rotation
      if (((direction>90) & (direction<270)) & rotationStyle==rotationStyle_LeftRight) p.image(getReversePImage(costumes.get(costumeNumber)), 0,0 ,costumes.get(costumeNumber).width*(size/100), 
      costumes.get(costumeNumber).height*(size/100));
      else 
      {
        // draw sprite. rotate if free; or not.
        if (rotationStyle==rotationStyle_Free) p.rotate(p.radians(-direction));
        p.image(costumes.get(costumeNumber), 0, 0, costumes.get(costumeNumber).width*(size/100),
        costumes.get(costumeNumber).height*(size/100));
      }
 
      p.popMatrix(); // restore default visual style

    }
  }

  // load "Scratch" cat costumes
  public void loadDefaultCostumes() {
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
    if (costumeNumber > numberOfCostumes + 1) costumeNumber=0;
  }

  // change to previous costume
  public void previousCostume() {
    costumeNumber--;
    if (costumeNumber < 0) costumeNumber=numberOfCostumes;
  }

  // switch to specific costume
  public void switchToCostume(int newCostumeNumber) {
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
  //public void changeSize, setSize, say/think(ForSeconds)


  // TODO this can be replaced by a matrix operation
  public PImage getReversePImage( PImage image ) {
    PImage reverse = new PImage( image.width, image.height );
    for ( int i=0; i < image.width; i++ ) {
      for (int j=0; j < image.height; j++) {
        reverse.set( image.width - 1 - i, j, image.get(i, j) );
      }
    }
    return reverse;
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
    /*
    p.print("mouseX "); p.print((p.mouseX-300)); p.print(" mouseX "); p.print((p.mouseY-300));
    p.print(" pos.x "); p.print(pos.x); p.print(" pos.y "); p.println(pos.y);
    p.print("direction: "); p.println(-direction);
    p.println("*************"); 
    */
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
    pointTowardsXY(p.mouseX-300,p.mouseY-300);
  }

  public void move(int distance) {
    /* Create a new vector, representing the desired motion (angle + distance) 
     * fromAngle() makes a unit vector (length 1)
     * negative on direction is b/c processing flips the cartesian y axis
     */
    PVector temp = PVector.fromAngle(p.radians(-direction));
    temp.mult(distance);
    pos.add(temp);
  }

  /* move to specific location on grid */
  public void goToXY(int x, int y) { 
    pos.x = x; pos.y = y;
  }

  // move to position of Sprite object
  public void goToSprite(Sprite target) { 
    pos.x=target.pos.x; 
    pos.y=target.pos.y;
  }

  // check if a Sprite is touching another Sprite using simple rectangular hit box
  public boolean touchingSprite(Sprite target) {
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

  // return distance to Sprite object
  public float distanceToSprite(Sprite target) { 
    return distanceToXY((int)target.pos.x, (int)target.pos.y);
  }
}

