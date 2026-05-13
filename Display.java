import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.util.*;
import javax.swing.*;

//The Display is the region in the window where drawing occurs.
public class Display extends JComponent implements
  KeyListener,  //need for keyboard input
  MouseListener  //need for mouse input
{
  //main method for testing
  public static void main(String[] args)
  {
    Display display = new Display();
    display.run();
  }

  private Image pac;  //image to draw
  private Ghost ghost1;
  private Image ghost1Img;
  private Ghost ghost2;
  private Image ghost2Img;
  private Ghost ghost3;
  private Image ghost3Img;
  private Ghost ghost4;
  private Image ghost4Img;
  private ArrayList<Ghost> ghosts;
  private int imageX;  //position of left edge of image
  private int imageY;  //position of top edge of image
  private String direction;
  private boolean isOpen;
  private int timer;
  private String imageFile;
  private boolean justChangedDir;
  private int displayWidth;
  private int displayHeight;
  private int imageWidth;
  private int imageHeight;
  private BufferedImage maze;

  public Display()
  {
    imageX = 200;
    imageY = 200;

    isOpen = true;
    justChangedDir = false;

    //load image
    String fileName = "PacRight.png";
    imageFile = fileName;
    URL url = getClass().getResource(fileName);
    if (url == null)
      throw new RuntimeException("Unable to load:  " + fileName);
    pac = new ImageIcon(url).getImage();

    fileName = "Pac.png"; //replace with ghost image later
    imageFile = fileName;
    url = getClass().getResource(fileName);
    if (url == null)
      throw new RuntimeException("Unable to load:  " + fileName);
    ghost1Img = new ImageIcon(url).getImage();

    fileName = "Pac.png"; //replace with ghost image later
    imageFile = fileName;
    url = getClass().getResource(fileName);
    if (url == null)
      throw new RuntimeException("Unable to load:  " + fileName);
    ghost2Img = new ImageIcon(url).getImage();

    fileName = "Pac.png"; //replace with ghost image later
    imageFile = fileName;
    url = getClass().getResource(fileName);
    if (url == null)
      throw new RuntimeException("Unable to load:  " + fileName);
    ghost3Img = new ImageIcon(url).getImage();

    fileName = "Pac.png"; //replace with ghost image later
    imageFile = fileName;
    url = getClass().getResource(fileName);
    if (url == null)
      throw new RuntimeException("Unable to load:  " + fileName);
    ghost4Img = new ImageIcon(url).getImage();


    displayWidth = 1250;
    displayHeight = 800;
    maze = new BufferedImage(displayWidth, displayHeight, BufferedImage.TYPE_INT_RGB);
    Graphics g = maze.getGraphics();

    g.setColor(Color.BLACK);  //set pen color to black
    g.fillRect(0, 0, displayWidth, displayHeight);  //fill with black rectangle
    g.setColor(Color.BLUE);
    g.fillRect(500,0, 10,100);
    g.fillRect(500,100,300,10);
    g.fillRect(800,100, 10,75);
    g.fillRect(400,175,410,10);
    g.fillRect(400,175,10,210);
    g.fillRect(400,500,10,300);
    g.fillRect(400,700,575,10);
    g.fillRect(975,560,10,150);
    g.fillRect(975,560,50,10);
    g.fillRect(1085,635,50,50);
    g.fillRect(1100,175,10,375);
    g.fillRect(925,175,175,10);
    g.fillRect(925,175,10,50);
    g.fillRect(500,290,425,50);
    g.fillRect(850,410,10,175);
    g.fillRect(150,100,75,350);
    g.fillRect(150,550,75,100);
    g.fillRect(225,275,175,10);
    g.fillRect(1100,340,150,10);
    g.fillRect(displayWidth/2-(150/2),(displayHeight/2-(150/2))+80,150,10); //GHOST SQUARE
    g.fillRect(displayWidth/2-(150/2),(displayHeight/2-(150/2))+80,10,150);
    g.fillRect(displayWidth/2+(150/2),(displayHeight/2-(150/2))+80,10,150);
    g.fillRect(displayWidth/2-(150/2),(displayHeight/2+(150/2))+80,160,10);
    g.fillRect(0, 0, 1250, 20); //Top edge
    g.fillRect(0, 0, 20, 819); //left side edge
    g.fillRect(0, 780, 1250, 20); //bottom edge
    g.fillRect(1230, 0, 20, 800); //right edge

    JFrame frame = new JFrame();  //create window
    frame.setTitle("Title");  //set title of window
    imageWidth = pac.getWidth(frame);
    imageHeight = pac.getHeight(frame);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//closing window will exit program
    setPreferredSize(new Dimension(displayWidth, displayHeight));  //set size of drawing region
    
    //need for keyboard input
    setFocusable(true);  //indicates that Display can process key presses
    addKeyListener(this);  //will notify Display when a key is pressed
    
    //need for mouse input
    addMouseListener(this);  //will notify Display when the mouse is pressed
    
    frame.getContentPane().add(this);  //add drawing region to window
    frame.pack();  //adjust window size to fit drawing region
    frame.setVisible(true);  //show window

    ghosts = new ArrayList<Ghost>();
    //add ghost to list and initialize ghosts
    ghost1 = new Ghost(displayWidth/2-(150/2)+30, displayHeight/2-(150/2)+115);
    ghost2 = new Ghost(displayWidth/2-(150/2)+30, displayHeight/2-(150/2)+175);
    ghost3 = new Ghost(displayWidth/2-(150/2)+90, displayHeight/2-(150/2)+115);
    ghost4 = new Ghost(displayWidth/2-(150/2)+90, displayHeight/2-(150/2)+175);
    ghosts.add(ghost1);
    ghosts.add(ghost2);
    ghosts.add(ghost3);
    ghosts.add(ghost4);
  }

  //called automatically when Java needs to draw the Display
  public void paintComponent(Graphics g)
  {
    int width = getWidth();  //get width of drawing region
    int height = getHeight();  //get height of drawing region
    //g.fillRect(width/4, height/4, 2*width/4, 2*height/4);
    g.drawImage(maze, 0, 0, null); //DRAWS BUFFERED IMAGE BEFORE PACMAN
    g.drawImage(pac, imageX, imageY, null);  //draw image.gif at (imageX, imageY)
    g.drawImage(ghost1Img, ghost1.getX(), ghost1.getY(), null);
    g.drawImage(ghost2Img, ghost2.getX(), ghost2.getY(), null);
    g.drawImage(ghost3Img, ghost3.getX(), ghost3.getY(), null);
    g.drawImage(ghost4Img, ghost4.getX(), ghost4.getY(), null);
  }

  //need for keyboard input
  public void keyPressed(KeyEvent e)
  {
    int key = e.getKeyCode();  //indicates which key was pressed
    //System.out.println("key pressed:  " + key);  //shows you key code values for other keys
    if(key == 87) {  //tests if "up" arrow was pressed
      direction = "up";
      justChangedDir = true;
      repaint();  //indicates Display must be redrawn (Java will call paintComponent)
    }
    else if(key == 83) {  //tests if "up" arrow was pressed
      direction = "down";
      justChangedDir = true;
      repaint();  //indicates Display must be redrawn (Java will call paintComponent)
    }
    else if(key == 65) {  //tests if "up" arrow was pressed
      direction = "left";
      justChangedDir = true;
      repaint();  //indicates Display must be redrawn (Java will call paintComponent)
    }
    else if(key == 68) {  //tests if "up" arrow was pressed
      direction = "right";
      justChangedDir = true;
      repaint();  //indicates Display must be redrawn (Java will call paintComponent)
    }
  }
  public void keyReleased(KeyEvent e) { }
  public void keyTyped(KeyEvent e) {
  }

  //need for mouse input
  public void mousePressed(MouseEvent e)
  {
    imageX = e.getX();  //get x-coordinate of mouse (and move image to it)
    imageY = e.getY();  //get y-coordinate of mouse (and move image to it)
    //System.out.println("mouse clicked:  " + imageX + ", " + imageY);
    repaint();  //indicates Display must be redrawn (Java will call paintComponent)
  }
  public void mouseReleased(MouseEvent e) { }
  public void mouseClicked(MouseEvent e) { }
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }

 public boolean notOnWall(String direction) { //check a little outside pac and see if it overlaps with wall
   boolean bool = true;
   if (direction.equals("left")) {
     for(int i = imageY-5; i < imageY + imageHeight+5; i++){
       Color leftSide = new Color(maze.getRGB(imageX-6, i));
       if(leftSide.equals(Color.BLUE))
         bool = false;
     }
   }
   else if (direction.equals("right")) {
     for(int i = imageY-5; i < imageY + imageHeight+5; i++){
       Color rightSide = new Color(maze.getRGB(imageX + imageWidth + 6, i));
       if(rightSide.equals(Color.BLUE))
         bool = false;
     }
   }
   else if (direction.equals("up")) {
     for(int i = imageX-5; i < imageX + imageWidth+5; i++){
       Color topSide = new Color(maze.getRGB(i, imageY - 6));
       if(topSide.equals(Color.BLUE))
         bool = false;
     }
   }
   else if (direction.equals("down")) {
     for(int i = imageX-5; i < imageX + imageWidth+5; i++){
       Color topSide = new Color(maze.getRGB(i, imageY + imageHeight + 6));
       if(topSide.equals(Color.BLUE))
         bool = false;
     }
   }
   if (bool)
     return true;
   else return false;
 }

  public void moveImage(String direction){ //move the image
    if(direction.equals("left")){
      for(int i = 0; i < 4; i ++){
        if(imageX > 0 && notOnWall("left"))
          imageX --;
      }
    }
    else if(direction.equals("right")){
      for(int i = 0; i < 4; i ++){
        if(imageX < displayWidth-60 && notOnWall("right"))
          imageX ++;
      }
    }
    else if(direction.equals("up")){
      for(int i = 0; i < 4; i ++){
        if(imageY > 0 && notOnWall("up"))
          imageY --;
      }
    }
    else if(direction.equals("down")){
      for(int i = 0; i < 4; i ++){
        if(imageY < displayHeight-55 && notOnWall("down"))
          imageY ++;
      }
    }
  }
  //need for automation (graphical changes not prompted by the keyboard or mouse)
  public void run()
  {
    while (true)
    {
      if(direction != null){//move images and update them
        boolean readyToChange = false;
        if(timer >= 3) {
          readyToChange = true;
          timer = 0;
        }
        else timer ++;
        String fileName = imageFile;
      if(direction.equals("left")) {
        moveImage("left");
        if(!isOpen && (readyToChange || justChangedDir)) {
          fileName = "PacLeft.png";
          justChangedDir = false;
        }
      }
      else if(direction.equals("right")) {
        moveImage("right");
        if(!isOpen && (readyToChange || justChangedDir))
          fileName = "PacRight.png";
        justChangedDir = false;
      }
      else if(direction.equals("up")) {
        moveImage("up");
        if(!isOpen && (readyToChange || justChangedDir))
          fileName = "PacUp.png";
        justChangedDir = false;
      }
      else if(direction.equals("down")) {
        moveImage("down");
        if(!isOpen && (readyToChange || justChangedDir))
          fileName = "PacDown.png";
        justChangedDir = false;
      }
      if(isOpen && readyToChange){
        fileName = "Pac.png";
        isOpen = false;
      }
      else if(!isOpen && readyToChange)
        isOpen = true;

      imageFile = fileName;
        URL url = getClass().getResource(fileName);
        if (url == null)
          throw new RuntimeException("Unable to load:  " + fileName);
        pac = new ImageIcon(url).getImage();
      }

      //check if Ghost is close enough to pac
      for(Ghost ghost : ghosts){
        double dist = Math.sqrt(Math.pow((double)ghost.getX()-imageX, 2)+Math.pow((double)ghost.getY()-imageY, 2));
        System.out.println(dist);
        if(dist < 25){ //distance formula
          return;
        }
      }

      //change the ghosts' coords
      ghost1.changeLocation();
      ghost2.changeLocation();
      ghost3.changeLocation();
      ghost4.changeLocation();

      repaint();  //indicates Display must be redrawn (Java will call paintComponent)
      try{Thread.sleep(25);} catch(Exception e){}  //give Java 25ms to run paintComponent
    }
  }
}