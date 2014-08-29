# scratching

### Scratching allows you to write Scratch programs in Processing.

written by Buck Bailey and Eli Baum, 2014


By now, you are probably an expert in Scratch. But most programming languages are not like Scratch. Instead of dragging in blocks, you type commands.

So instead of

![Scratch example](readme-img/scratch-example.png)

you would write something like

```
function whenGreenFlagClicked() {
    if (mouseDown == true) {
        move(10)
    }
}
```

Just like there are many spoken languages (English, Spanish, Chinese) there are also many programming languages. You have been working in **Scratch**. You may have heard of others. Now, you will learn **Processing**, which is based on **Java**.

We have written some code (called "**scratching**") which will allow to you write Processing programs from your old Scratch programs. All you need to do is copy scratching into a new tab in Processing.

Below are some instructions on how to use Processing, as well as a full reference for all of the scratching commands.

---

##### Let's take a look at a basic sketch:

Explanation of all code is below.

```
Sprite cat;

void setup() {
	size(600, 600);
	cat = new Sprite(this);
}

void draw() {
	background(0);
	
	cat.move(2);
	cat.update();
}
```

If you run it, you should see the cat moving to the right across the screen.

So how does it work?

`Sprite cat;` This makes a sprite called "cat". **Notice the semicolon ; at the end of the line. You need one after every command!**

`void setup() {` The setup function runs once, at the beginning of your program. This is its *function definition*. A function is like a script in Scratch. It must have a name ("setup"), followed by parantheses. The curly bracket starts the function. Inside of functions, we usually *indent* code (use the tab key).

*What does "void" mean?* I'll tell you later.

`size(600,600);` The size *function* tells the computer how big you want your window to be. Here, it is 600 pixels by 600 pixels. Notice the semicolon at the end of the line!

`cat = new Sprite(this);` This line actually *creates* the *new* sprite. Don't worry to much about it for now. Just don't forget it.

You know what else you shouldn't forget? ***Semicolons!***

*What does "this" mean?* Stop asking so many questions.

`}` This curly bracket closes the setup function.

Next, we have the `draw` function. The draw functions runs over and over again. So you can use to repeatedly draw to or update the window. Because of this, if you only want to draw something once (something that never needs to be updated), you can just put it in `setup`.

`background(0);` You'll probably always want this line at the beginning of your draw function. It clears the screen: the argument is a color. 0 is black, 255 is white, everything in between is gray.

If you didn't clear the background, you would just keep seeing new images of the cat sprite on top of each other.

`cat.move(2);` As you can probably guess, this command **moves** the **cat** sprite **2** pixels. Note that because the draw functions loops over and over again, the cat repeatedly moves 2 pixels — the result is that you see smooth motion on your screen.

`cat.draw();` This actually draws the cat to the screen.

---
## Full Command Reference
In no particular order.

#### Sprite Commands
“cat.” indicates an example Sprite name.

`Sprite(PApplet parent)` Creates a new sprite in the specified `PApplet`. Will usually be `this`.

`void cat.draw()` Draw the sprite.

`void cat.setGhostEffect(int newAlpha)` Sets the ghost effect (transparency). 0 is fully transparent; 255 is fully opaque.
(You can also use `cat.ghostEffect = newValue`)

`void cat.move(int distance)` Move the sprite some distance (in pixels). Note that this function accounts for the sprite's current angle.

`void cat.addDefaultCostumes()` Loads the default cat costumes.

`void cat.addCostume(String filePath)` Add the costume image at the specified path. Image types `.gif`, `.jpg`, `.tga`, `.png`.

`void cat.nextCostume()` Switch to the next costume. Will wrap around to the first costume.

`void cat.previousCostume()` Switch to the previous costume. Will wrap around to the last costume.

`void cat.setCostume(int newCostumeNumber)` Switch to the specified costume. Be aware that the costume list is zero-index, so `setCostume(0)` will set the sprite to the first costume.
You can also set the value directly: `cat.costumeNumber = int`

`void cat.show()` Show the sprite.

`void cat.hide()` Hide the sprite.

You can also set `cat.visible = true/false`

`void cat.say(String what)` Say something (in the console).

`void cat.think(String what)` Think something (in the console).

`void cat.turn(float angle)` Turn by specified angle, **degrees**.

`void cat.turnLeft(float angle)	` Turn left.

`void cat.turnRight(float angle)` Turn right.

`void cat.pointTowardsXY(int x, int y)` Point towards the coordinates (x, y).

`void cat.pointInDirection(float angle)` Point in the specified direction, **degrees**.
Same as `cat.direction = angle`

`void cat.pointTowardsSprite(Sprite target)` Point towards another sprite.

`void cat.pointTowardsMouse()` Points towards the mouse pointer. This will only work if the mouse pointer is contained within the applet window.

`void cat.goToXY(int x, int y)` Sets the sprite's position to (x, y).

`void cat.goToSprite(Sprite target)` Set the sprite's position to that of another sprite.

`boolean cat.touchingSprite(Sprite target)` Returns true if the sprite is within the rectangular bounding box of another sprite.

`float cat.distanceToXY(int x, int y)` Returns the distance to another point.

`float cat.distanceToMouse()` Returns the distance to the mouse pointer.

`float cat.distanceToSprite(Sprite target)` Returns the distance to another sprite.

#### Stage Commands
You can use a stage if you don't want just a boring solid-color static background.

`Stage(PApplet parent)` Creates a stage in the specified applet. Will probably be `this`.

`void stage.draw()` Draw the stage.

`int stage.timer()` Returns the number of seconds since the program started.

`void stage.resetTimer()` Set the timer to 0.

`void stage.questionKeycheck()` Run this inside of a keypress event if you are expecting responses.

`String stage.answer()` Returns the answer to the asked question.

`void stage.ask(String newQuestion)` Ask a question.

`void stage.addDefaultBackdrop()` Load the default backdrop(s) into the backdrop list.

`void stage.addBackdrop(String filePath)` Add the backdrop image specified by the path.

`void stage.nextBackdrop()` Switch to the next backdrop.

`void stage.previousBackdrop()` Switch to the previous backdrop.

`void stage.setBackdrop(int newBackdropNumber)` Switch to the specified (zero-indexed) backdrop.