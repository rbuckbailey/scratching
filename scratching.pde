Sprite cat;
Sprite alsoCat;
int numberOfSprites=0;

void setup() {
  size(600, 600);
  cat = new Sprite(this);
  numberOfSprites++;
  cat.size = 50;
  cat.pos.set(0, 0);
  alsoCat = new Sprite(this);
  numberOfSprites++;
   cat.size=50;
   cat.goToXY(0,0);
   alsoCat.size=25;
   alsoCat.pos.set(250,250);
}
  
void wrapAtEdges() {
  if (cat.pos.x>330) cat.pos.x=-330;
  if (cat.pos.y>330) cat.pos.y=-330;
  if (cat.pos.x<-330) cat.pos.x=330;
  if (cat.pos.y<-330) cat.pos.y=330;
}  

void draw() {
  background(0);

  //cat.pointTowardsMouse();
  cat.pointTowards(alsoCat);
  cat.update(true);
  cat.nextCostume();
  cat.move(5);
  wrapAtEdges();
   
  alsoCat.nextCostume();
  if (alsoCat.touchingSprite(cat)) { alsoCat.hide(); cat.goToXY(0,0); } 
  else alsoCat.show();
  alsoCat.update(false);
   
  delay(100);
}

void mouseClicked() {    
  cat.pos.set(0, 0);
  alsoCat.pos.set(250,250);
}

