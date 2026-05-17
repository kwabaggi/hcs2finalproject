import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;

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
    private final String ghost1File;
    private final String ghost2File;
    private final String ghost3File;
    private final String ghost4File;
    private boolean justChangedDir;
    private final int displayWidth;
    private final int displayHeight;
    private final int imageWidth;
    private final int imageHeight;
    private BufferedImage maze;
    private JFrame frame;
    private Graphics g;
    private int openerTimer;
    private boolean isDone;
    private int pacCenterX;
    private int pacCenterY;
    private ArrayList<Orb> orbs;
    private int score;
    private final long startTime;
    private double gameTime;
    private Clip c;
    private int soundTimer;

    public Display()
    {

        imageX = 400;
        imageY = 420;

        isOpen = true;
        justChangedDir = false;

        //load image
        String fileName = "PacRight.png";
        imageFile = fileName;
        URL url = getClass().getResource(fileName);
        if (url == null)
            throw new RuntimeException("Unable to load:  " + fileName);
        pac = new ImageIcon(url).getImage();

        ghost1File = "feinbergghost.png";
        url = getClass().getResource(ghost1File);
        if (url == null)
            throw new RuntimeException("Unable to load:  " + ghost1File);
        ghost1Img = new ImageIcon(url).getImage();

        ghost2File = "olexioghost.png";
        url = getClass().getResource(ghost2File);
        if (url == null)
            throw new RuntimeException("Unable to load:  " + ghost2File);
        ghost2Img = new ImageIcon(url).getImage();

        ghost3File = "dennettghost.png";
        url = getClass().getResource(ghost3File);
        if (url == null)
            throw new RuntimeException("Unable to load:  " + ghost3File);
        ghost3Img = new ImageIcon(url).getImage();

        ghost4File = "bologneseghost.jpg";
        url = getClass().getResource(ghost4File);
        if (url == null)
            throw new RuntimeException("Unable to load:  " + ghost4File);
        ghost4Img = new ImageIcon(url).getImage();

        displayWidth = 1250;
        displayHeight = 800;
        maze = new BufferedImage(displayWidth, displayHeight, BufferedImage.TYPE_INT_RGB);
        g = maze.getGraphics();

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

        orbs = new ArrayList<Orb>(); //update this arraylist with new orbs
        g.setColor(Color.WHITE);
                g.fillOval(60,50,25,25);
        orbs.add(new Orb(60, 50));
                g.fillOval(60,100,25,25);
        orbs.add(new Orb(60, 100));
                g.fillOval(60,150,25,25);
        orbs.add(new Orb(60, 150));
                g.fillOval(60,200,25,25);
        orbs.add(new Orb(60, 200));
                g.fillOval(60,250,25,25);
        orbs.add(new Orb(60, 250));
                g.fillOval(60,300,25,25);
        orbs.add(new Orb(60, 300));
                g.fillOval(60,350,25,25);
        orbs.add(new Orb(60, 350));
                g.fillOval(60,400,25,25);
        orbs.add(new Orb(60, 400));
                g.fillOval(60,450,25,25);
        orbs.add(new Orb(60, 450));
                g.fillOval(110,475,25,25);
        orbs.add(new Orb(110, 475));
                g.fillOval(160,475,25,25);
        orbs.add(new Orb(160, 475));
                g.fillOval(210,475,25,25);
        orbs.add(new Orb(210, 475));
                g.fillOval(260,475,25,25);
        orbs.add(new Orb(260, 475));
                g.fillOval(310,475,25,25);
        orbs.add(new Orb(310, 475));
                g.fillOval(310,510,25,25);
        orbs.add(new Orb(310, 510));
                g.fillOval(310,560,25,25);
        orbs.add(new Orb(310, 560));
                g.fillOval(310,610,25,25);
        orbs.add(new Orb(310, 610));
                g.fillOval(310,660,25,25);
        orbs.add(new Orb(310, 660));
                g.fillOval(310,710,25,25);
        orbs.add(new Orb(310, 710));
                g.fillOval(260,710,25,25);
        orbs.add(new Orb(260, 710));
                g.fillOval(210,710,25,25);
        orbs.add(new Orb(210, 710));
                g.fillOval(160,710,25,25);
        orbs.add(new Orb(160, 710));
                g.fillOval(110,710,25,25);
        orbs.add(new Orb(110, 710));
                g.fillOval(60,500,25,25);
        orbs.add(new Orb(60, 500));
                g.fillOval(60,550,25,25);
        orbs.add(new Orb(60, 550));
                g.fillOval(60,600,25,25);
        orbs.add(new Orb(60, 600));
                g.fillOval(60,650,25,25);
        orbs.add(new Orb(60, 650));
                g.fillOval(60,700,25,25);
        orbs.add(new Orb(60, 700));




        g.setColor(Color.BLUE);
        g.fillRect(displayWidth/2-(150/2),(displayHeight/2-(150/2))+80,150,10); //GHOST SQUARE
        g.fillRect(displayWidth/2-(150/2),(displayHeight/2-(150/2))+80,10,150);
        g.fillRect(displayWidth/2+(150/2),(displayHeight/2-(150/2))+80,10,150);
        g.fillRect(displayWidth/2-(150/2),(displayHeight/2+(150/2))+80,160,10);
        g.fillRect(0, 0, 1250, 20); //Top edge
        g.fillRect(0, 0, 20, 819); //left side edge
        g.fillRect(0, 780, 1250, 20); //bottom edge
        g.fillRect(1230, 0, 20, 800); //right edge

        frame = new JFrame();  //create window
        frame.setTitle("YOUR SCORE WILL SHOW HERE");  //set title of window
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
        ghost1 = new Ghost(displayWidth/2-(150/2)+30, displayHeight/2-(150/2)+115, this, 1);
        ghost2 = new Ghost(displayWidth/2-(150/2)+25, displayHeight/2-(150/2)+170, this, 2);
        ghost3 = new Ghost(displayWidth/2-(150/2)+90, displayHeight/2-(150/2)+115, this, 3);
        ghost4 = new Ghost(displayWidth/2-(150/2)+90, displayHeight/2-(150/2)+175, this, 4);
        ghosts.add(ghost1);
        ghosts.add(ghost2);
        ghosts.add(ghost3);
        ghosts.add(ghost4);

//        JFrame msg = new JFrame("Introduction");
//        JLabel text = new JLabel("Welcome to Pac-Men\nWASD to move");
//        JButton button = new JButton("Begin Game");
//        JPanel panel = new JPanel();
//        msg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        panel.add(text);
//        panel.add(button);
//        msg.add(panel);
//        msg.setPreferredSize(new Dimension(1000, 1000));
//        msg.pack();
//        msg.setVisible(true);

        //play intro audio
        soundTimer = 21;
        playAudio("pacman_beginning.wav");

        JPanel pane = new JPanel();
        JLabel label = new JLabel("<html><font color = '#EBAE15'>Pac-Men<font color = 'black'> by Jude S. and Ethan L.<br><br>WASD to move<br>Collect coins for high score<br><font color='#15A7EB'>Score is at the top<br><font color = '#D915EB'>You get more points the faster you collect the orbs" +
                "<br><font color = 'black'>Press ESC to restart");
        label.setFont(new Font("Arial", Font.BOLD, 25));
        pane.add(label);
        pane.setPreferredSize(new Dimension(600, 250));
        JOptionPane optionPane = new JOptionPane();
        optionPane.setPreferredSize(new Dimension(200, 200));
        UIManager.put("OptionPane.okButtonText", "Begin Game");
        JOptionPane.showMessageDialog(frame, pane, "Introduction", JOptionPane.INFORMATION_MESSAGE);

        startTime = System.currentTimeMillis();
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
        else if(key == 27) {
            isDone = true;
           //String[] args = new String[0];
           //main(args);
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

    public boolean ghostNotOnWall(String direction, Ghost ghost) { //check a little outside pac and see if it overlaps with wall
        boolean bool = true;
        if (direction.equals("left")) {
            for(int i = ghost.getY()-5; i < ghost.getY() + ghost.getImageHeight()+5; i++){
                Color leftSide = new Color(maze.getRGB(ghost.getX()-6, i));
                if(leftSide.equals(Color.BLUE))
                    bool = false;
            }
        }
        else if (direction.equals("right")) {
            for(int i = ghost.getY()-5; i < ghost.getY() + ghost.getImageHeight()+5; i++){
                Color rightSide = new Color(maze.getRGB(ghost.getX() + ghost.getImageWidth() + 6, i));
                if(rightSide.equals(Color.BLUE))
                    bool = false;
            }
        }
        else if (direction.equals("up")) {
            for(int i = ghost.getX()-5; i < ghost.getX() + ghost.getImageWidth()+5; i++){
                Color topSide = new Color(maze.getRGB(i, ghost.getY() - 6));
                if(topSide.equals(Color.BLUE))
                    bool = false;
            }
        }
        else if (direction.equals("down")) {
            for(int i = ghost.getX()-5; i < ghost.getX() + ghost.getImageWidth()+5; i++){
                Color topSide = new Color(maze.getRGB(i, ghost.getY() + ghost.getImageHeight() + 6));
                if(topSide.equals(Color.BLUE))
                    bool = false;
            }
        }
        if (bool)
            return true;
        else{
            ghost.changeDirection();
            return false;
        }
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
        while (true) {
            if (isDone) {
                String[] args = new String[0];
                main(args);
                break;
            } else {
                gameTime = (double) (System.currentTimeMillis() - startTime) / 1000;

                //check if Ghost is close enough to pac
                for (Ghost ghost : ghosts) {
                    double dist = distance(ghost.getCenterX(), ghost.getCenterY(), pacCenterX, pacCenterY);
                    //System.out.println(dist);
                    if (!ghost.isRunAway() && dist < 35) {
                        JPanel finalPanel = new JPanel();
                        playAudio("pacman_death.wav");
                        JLabel label = new JLabel("<html>You died. Your score is: <font color = '#A11FC2'><b>" + score + "</b></html>");
                        JLabel second = new JLabel("<html><font color = '#2BAD7D'><br>Thanks for playing Pac-Men! \uD83D\uDE01</html>");;
                        label.setFont(new Font("Arial", Font.BOLD, 25));
                        second.setFont(new Font("Arial", Font.BOLD, 25));
                        finalPanel.add(label);
                        finalPanel.add(second);
                        finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));
                        finalPanel.setPreferredSize(new Dimension(350, 175));
                        JOptionPane optionPane = new JOptionPane();
                        optionPane.setPreferredSize(new Dimension(350, 0));
                        UIManager.put("OptionPane.okButtonText", "Close");
                        JOptionPane.showMessageDialog(frame, finalPanel, "Final Score", JOptionPane.INFORMATION_MESSAGE);
                        String[] args = new String[0];
                        main(args);
                        return;
                    }
                    else if(ghost.isRunAway() && dist < 35 && ghost.getValidToKilled()){
                        //g.clearRect(ghost.getX(), ghost.getY(), ghost.getImageWidth(), ghost.getImageHeight());
                        ghost.setKill();
                        score += pointFunction("ghost");
                        frame.setTitle("Score: " + Integer.toString(score));
                    }
                }

                if (direction != null) {//move images and update them
                    boolean readyToChange = false;
                    if (timer >= 3) {
                        readyToChange = true;
                        timer = 0;
                    } else timer++;
                    String fileName = imageFile;
                    if (direction.equals("left")) {
                        moveImage("left");
                        if (!isOpen && (readyToChange || justChangedDir)) {
                            fileName = "PacLeft.png";
                            justChangedDir = false;
                        }
                    } else if (direction.equals("right")) {
                        moveImage("right");
                        if (!isOpen && (readyToChange || justChangedDir))
                            fileName = "PacRight.png";
                        justChangedDir = false;
                    } else if (direction.equals("up")) {
                        moveImage("up");
                        if (!isOpen && (readyToChange || justChangedDir))
                            fileName = "PacUp.png";
                        justChangedDir = false;
                    } else if (direction.equals("down")) {
                        moveImage("down");
                        if (!isOpen && (readyToChange || justChangedDir))
                            fileName = "PacDown.png";
                        justChangedDir = false;
                    }
                    if (isOpen && readyToChange) {
                        fileName = "Pac.png";
                        isOpen = false;
                    } else if (!isOpen && readyToChange)
                        isOpen = true;

                    imageFile = fileName;
                    URL url = getClass().getResource(fileName);
                    if (url == null)
                        throw new RuntimeException("Unable to load:  " + fileName);
                    pac = new ImageIcon(url).getImage();
                }

                pacCenterX = imageX + imageWidth / 2;
                pacCenterY = imageY + imageHeight / 2;

                //change the ghosts' coords
                ghost1.changeLocation();
                ghost2.changeLocation();
                ghost3.changeLocation();
                ghost4.changeLocation();

                //run the ghost for vulnerability
                ghost1.run();
                ghost2.run();
                ghost3.run();
                ghost4.run();

                if (openerTimer > 150) {
                    g.clearRect(displayWidth / 2 - (150 / 2), (displayHeight / 2 - (150 / 2)) + 80, 150, 10); //GHOST SQUARE
                    g.clearRect(displayWidth / 2 - (150 / 2), (displayHeight / 2 - (150 / 2)) + 80, 10, 150);
                    g.clearRect(displayWidth / 2 + (150 / 2), (displayHeight / 2 - (150 / 2)) + 80, 10, 150);
                    g.clearRect(displayWidth / 2 - (150 / 2), (displayHeight / 2 + (150 / 2)) + 80, 160, 10);
                } else openerTimer++;

                for (int x = pacCenterX - 5; x < pacCenterX + 5; x++) { //check for orbs
                    for(int y = pacCenterY - 5; y < pacCenterY + 5; y++) {
                        Color col = new Color(maze.getRGB(x, y));
                        if (col.equals(Color.WHITE)) {
                                playAudio("pacman_chomp.wav");
                            Orb closest = orbs.get(0);
                            double minDist = distance(closest.getX(), closest.getY(), pacCenterX, pacCenterY);
                            for (Orb dot : orbs) {
                                double candidateDist = distance(dot.getX(), dot.getY(), pacCenterX, pacCenterY);
                                if (candidateDist < minDist) {
                                    closest = dot;
                                    minDist = candidateDist;
                                }
                            }
                            orbs.remove(closest);
                            g.clearRect(closest.getX(), closest.getY(), 25, 25);
                            score += pointFunction("orb");
                            frame.setTitle("Score: " + Integer.toString(score));
                        }
                    }
            }
                if(soundTimer <= 20)
                    soundTimer ++;
                repaint();  //indicates Display must be redrawn (Java will call paintComponent)
                try {
                    Thread.sleep(25);
                } catch (Exception e) {
                }  //give Java 25ms to run paintComponent
            }
        }
    }

    public int getDisplayWidth(){
        return displayWidth;
    }

    public int getDisplayHeight(){
        return displayHeight;
    }

    public JFrame getFrame(){
        return frame;
    }

    public Image getGhostImg(int type){
        if(type == 1)
            return ghost1Img;
        else if(type == 2)
            return ghost2Img;
        else if(type == 3)
            return ghost3Img;
        else if(type == 4)
            return ghost4Img;
        else return null;
    }

    public void changeGhostImage(int type, String fileName){
        URL url = getClass().getResource(fileName);
        if(url == null)
            throw new RuntimeException("Unable to load:  " + fileName);
        Image img = new ImageIcon(url).getImage();
        if(type == 1)
            ghost1Img = img;
        else if(type == 2)
            ghost2Img = img;
        else if(type == 3)
            ghost3Img = img;
        else if(type == 4)
            ghost4Img = img;
    }

    public static double distance(int x1, int y1, int x2, int y2){
        return Math.sqrt(Math.pow((double)x2 - x1, 2) + Math.pow((double)y2 - y1, 2));
    }

    public static double distance(Location loc1, Location loc2){
        return Math.sqrt(Math.pow((double)loc2.getX() - loc1.getX(), 2) + Math.pow((double)loc2.getY() - loc1.getY(), 2));
    }

    public int pointFunction(String a){
        if(a.equals("orb"))
            return (int)Math.pow(100, -1*((gameTime-200)/200));
        else if(a.equals("ghost"))
            return (int)Math.pow(1000, -1*((gameTime-200)/200));
        else return -1;
    }

    public void respawnGhost(int type, Ghost ghost){
        URL url;
        Image img = null;
        if(type == 1) {
            url = getClass().getResource(ghost1File);
            img = new ImageIcon(url).getImage();
            ghost1Img = img;
        }
        else if(type == 2){
            url = getClass().getResource(ghost2File);
            img = new ImageIcon(url).getImage();
            ghost2Img = img;
        }
        else if(type == 3){
            url = getClass().getResource(ghost3File);
            img = new ImageIcon(url).getImage();
            ghost3Img = img;
        }
        else if(type == 4){
            url = getClass().getResource(ghost4File);
            img = new ImageIcon(url).getImage();
            ghost4Img = img;
        }
    }

    public void playAudio(String file) {
        if (soundTimer > 12) {
            soundTimer = 0;
            File sound = new File(file);
            if (sound.exists()) {
                try {
                    AudioInputStream input = AudioSystem.getAudioInputStream(sound);
                    c = AudioSystem.getClip();
                    c.open(input);
                    c.start();
                } catch (Exception a) {
                    System.out.println(a);
                }
            } else throw new RuntimeException("Unable to load " + file);
        }
    }

    public int getPacCenterX(){
        return pacCenterX;
    }

    public int getPacCenterY(){
        return pacCenterY;
    }
}