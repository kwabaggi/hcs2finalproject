import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.net.*;
import java.sql.Array;
import java.util.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.util.List;

public class Display extends JComponent implements
        KeyListener,  //need for keyboard input
        MouseListener  //need for mouse input
{
    //main method for testing
    public static void main(String[] args)
    {
            Display display = new Display();
        //System.out.println(highscoreInts.size());
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
    static ArrayList<Integer> highscoreInts = new ArrayList<Integer>();
    static ArrayList<String> highscoreNames = new ArrayList<String>();
    static ImageIcon ghost1ImageIcon;
    static ImageIcon ghost2ImageIcon;
    static ImageIcon ghost3ImageIcon;
    static ImageIcon ghost4ImageIcon;

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
        ghost1ImageIcon = new ImageIcon(getClass().getResource("feinbergend.png"));
        ghost1Img = new ImageIcon(url).getImage();

        ghost2File = "olexioghost.png";
        url = getClass().getResource(ghost2File);
        if (url == null)
            throw new RuntimeException("Unable to load:  " + ghost2File);
        ghost2ImageIcon = new ImageIcon(getClass().getResource("olexioend.png"));
        ghost2Img = new ImageIcon(url).getImage();

        ghost3File = "dennettghost.png";
        url = getClass().getResource(ghost3File);
        if (url == null)
            throw new RuntimeException("Unable to load:  " + ghost3File);
        ghost3ImageIcon = new ImageIcon(getClass().getResource("dennettend.png"));
        ghost3Img = new ImageIcon(url).getImage();

        ghost4File = "bologneseghost.jpg";
        url = getClass().getResource(ghost4File);
        if (url == null)
            throw new RuntimeException("Unable to load:  " + ghost4File);
        ghost4ImageIcon = new ImageIcon(getClass().getResource("bologneseend.jpg"));
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
        g.fillRect(400,175,10,175);
        g.fillRect(400,500,10,300);
        g.fillRect(400,700,575,10);
        g.fillRect(975,560,10,150);

        g.fillRect(1085,635,50,50);
        g.fillRect(1100,175,10,375);
        g.fillRect(925,175,175,10);

        g.fillRect(500,290,425,50);
        g.fillRect(850,410,10,175);
        g.fillRect(150,100,75,350);
        g.fillRect(150,550,75,100);
        g.fillRect(225,275,175,10);
        g.fillRect(1100,340,150,10);

        orbs = new ArrayList<Orb>(); //update this arraylist with new orbs
        g.setColor(Color.WHITE);
        g.fillOval(60,50,25,25); //top left
        orbs.add(new Orb(60, 50, 25));
        g.fillOval(110,50,25,25); //directly right of top left
        orbs.add(new Orb(110,50,25));
        g.fillOval(160,50,25,25);
        orbs.add(new Orb(160,50,25));
        g.fillOval(210,50,25,25);
        orbs.add(new Orb(210,50,25));
        g.fillOval(260,50,25,25);
        orbs.add(new Orb(260,50,25));
        g.fillOval(310,50,25,25);
        orbs.add(new Orb(310,50,25));
        g.fillOval(360,50,25,25);
        orbs.add(new Orb(360,50,25));
        g.fillOval(410,50,25,25);
        orbs.add(new Orb(410,50,25));
        g.fillOval(460,50,25,25);
        orbs.add(new Orb(460,50,25));
        g.fillOval(460,100,25,25);
        orbs.add(new Orb(460,100,25));
        g.fillOval(410,100,25,25);
        orbs.add(new Orb(410,100,25));
        g.fillOval(360,100,25,25);
        orbs.add(new Orb(360,100,25));
        g.fillOval(310,100,25,25);
        orbs.add(new Orb(310,100,25));
        g.fillOval(260,100,25,25);
        orbs.add(new Orb(260,100,25));
        g.fillOval(260,150,25,25);
        orbs.add(new Orb(260,150,25));
        g.fillOval(260,200,25,25);
        orbs.add(new Orb(260,200,25));
        g.fillOval(300,300,25,25);
        orbs.add(new Orb(300,300,25));
        g.fillOval(240,300,25,25);
        orbs.add(new Orb(240,300,25));
        g.fillOval(360,300,25,25);
        orbs.add(new Orb(360,300,25));
        g.fillOval(310,200,25,25);
        orbs.add(new Orb(310,200,25));
        g.fillOval(360,200,25,25);
        orbs.add(new Orb(360,200,25));
        g.fillOval(360,150,25,25);
        orbs.add(new Orb(360,150,25));
        g.fillOval(310,150,25,25);
        orbs.add(new Orb(310,150,25));
        g.setColor(Color.YELLOW);
        g.fillOval(750,125,35,35);
        orbs.add(new Orb(750,125,35));
        g.setColor(Color.WHITE);
        g.fillOval(60,100,25,25); //directly down from top left
        orbs.add(new Orb(60, 100,25));
        g.fillOval(60,150,25,25);
        orbs.add(new Orb(60, 150,25));
        g.fillOval(60,200,25,25);
        orbs.add(new Orb(60, 200,25));
        g.fillOval(60,250,25,25);
        orbs.add(new Orb(60, 250,25));
        g.fillOval(60,300,25,25);
        orbs.add(new Orb(60, 300,25));
        g.fillOval(60,350,25,25);
        orbs.add(new Orb(60, 350,25));
        g.fillOval(60,400,25,25);
        orbs.add(new Orb(60, 400,25));
        g.fillOval(60,450,25,25);
        orbs.add(new Orb(60, 450,25));
        g.fillOval(110,475,25,25); //directly right of middle segment
        orbs.add(new Orb(110, 475,25));
        g.fillOval(160,475,25,25);
        orbs.add(new Orb(160, 475,25));
        g.fillOval(210,475,25,25);
        orbs.add(new Orb(210, 475,25));
        g.fillOval(260,475,25,25);
        orbs.add(new Orb(260, 475,25));
        g.fillOval(310,475,25,25);
        orbs.add(new Orb(310, 475,25));
        g.fillOval(310,510,25,25);
        orbs.add(new Orb(310, 510,25));
        g.fillOval(310,560,25,25);
        orbs.add(new Orb(310, 560,25));
        g.fillOval(310,610,25,25);
        orbs.add(new Orb(310, 610,25));
        g.fillOval(310,660,25,25);
        orbs.add(new Orb(310, 660,25));
        g.fillOval(310,710,25,25);
        orbs.add(new Orb(310, 710,25));
        g.fillOval(260,710,25,25);
        orbs.add(new Orb(260, 710,25));
        g.fillOval(210,710,25,25);
        orbs.add(new Orb(210, 710,25));
        g.fillOval(160,710,25,25);
        orbs.add(new Orb(160, 710,25));
        g.fillOval(110,710,25,25);
        orbs.add(new Orb(110, 710,25));
        g.fillOval(60,500,25,25);
        orbs.add(new Orb(60, 500,25));
        g.fillOval(60,550,25,25);
        orbs.add(new Orb(60, 550,25));
        g.fillOval(60,600,25,25);
        orbs.add(new Orb(60, 600,25));
        g.fillOval(60,650,25,25);
        orbs.add(new Orb(60, 650,25));
        g.fillOval(60,700,25,25);
        orbs.add(new Orb(60, 700,25)); //completes bottom left square
        g.setColor(Color.YELLOW);
        g.fillOval(420,725,15,15); //VC
        orbs.add(new Orb(420,725,15));
        g.fillOval(435,740,15,15);
        orbs.add(new Orb(435,740,15));
        g.fillOval(465,740,15,15);
        orbs.add(new Orb(465,740,15));
        g.fillOval(450,755,15,15);
        orbs.add(new Orb(450,755,15));
        g.fillOval(480,725,15,15);
        orbs.add(new Orb(480,725,15));
        g.fillOval(520,720,15,15);
        orbs.add(new Orb(520,720,15));
        g.fillOval(520,754,15,15);
        orbs.add(new Orb(520,754,15));
        g.fillOval(540,720,15,15);
        orbs.add(new Orb(540,720,15));
        g.fillOval(540,754,15,15);
        orbs.add(new Orb(540,754,15));
        g.fillOval(505,737,15,15);
        orbs.add(new Orb(505,737,15));
        g.setColor(Color.WHITE);
        g.fillOval(420,670,20,20); //E
        orbs.add(new Orb(420,670,20));
        g.fillOval(450,670,20,20);
        orbs.add(new Orb(450,670,20));
        g.fillOval(480,670,20,20);
        orbs.add(new Orb(480,670,20));
        g.fillOval(420,640,20,20);
        orbs.add(new Orb(420,640,20));
        g.fillOval(420,610,20,20);
        orbs.add(new Orb(420,610,20));
        g.fillOval(420,580,20,20);
        orbs.add(new Orb(420,580,20));
        g.fillOval(450,580,20,20);
        orbs.add(new Orb(450,580,20));
        g.fillOval(480,580,20,20);
        orbs.add(new Orb(480,580,20));
        g.fillOval(445,625,20,20);
        orbs.add(new Orb(445,625,20));
        g.fillOval(475,625,20,20);
        orbs.add(new Orb(475,625,20));
        g.fillOval(530,610,20,20); //T
        orbs.add(new Orb(530,610,20));
        g.fillOval(560,610,20,20);
        orbs.add(new Orb(560,610,20));
        g.fillOval(590,610,20,20);
        orbs.add(new Orb(590,610,20));
        g.fillOval(560,580,20,20);
        orbs.add(new Orb(560,580,20));
        g.fillOval(560,640,20,20);
        orbs.add(new Orb(560,640,20));
        g.fillOval(560,670,20,20);
        orbs.add(new Orb(560,670,20));
        g.fillOval(630,605,20,20); //H
        orbs.add(new Orb(630,605,20));
        g.fillOval(630,575,20,20);
        orbs.add(new Orb(630,575,20));
        g.fillOval(630,640,20,20);
        orbs.add(new Orb(630,640,20));
        g.fillOval(630,670,20,20);
        orbs.add(new Orb(630,670,20));
        g.fillOval(655,620,20,20);
        orbs.add(new Orb(655,620,20));
        g.fillOval(685,620,20,20);
        orbs.add(new Orb(685,620,20));
        g.fillOval(685,640,20,20);
        orbs.add(new Orb(685,640,20));
        g.fillOval(685,670,20,20);
        orbs.add(new Orb(685,670,20));
        g.fillOval(720,615,20,20); //A
        orbs.add(new Orb(720,615,20));
        g.fillOval(750,615,20,20);
        orbs.add(new Orb(750,615,20));
        g.fillOval(780,615,20,20);
        orbs.add(new Orb(780,615,20));
        g.fillOval(720,645,20,20);
        orbs.add(new Orb(720,645,20));
        g.fillOval(720,670,20,20);
        orbs.add(new Orb(720,670,20));
        g.fillOval(750,670,20,20);
        orbs.add(new Orb(750,670,20));
        g.fillOval(780,670,20,20);
        orbs.add(new Orb(780,670,20));
        g.fillOval(780,640,20,20);
        orbs.add(new Orb(780,640,20));
        g.fillOval(810,670,20,20);
        orbs.add(new Orb(810,670,20));
        g.fillOval(850,670,20,20); //N
        orbs.add(new Orb(850,670,20));
        g.fillOval(850,640,20,20);
        orbs.add(new Orb(850,640,20));
        g.fillOval(850,610,20,20);
        orbs.add(new Orb(850,610,20));
        g.fillOval(870,625,20,20);
        orbs.add(new Orb(870,625,20));
        g.fillOval(900,625,20,20);
        orbs.add(new Orb(900,625,20));
        g.fillOval(900,645,20,20);
        orbs.add(new Orb(900,645,20));
        g.fillOval(900,670,20,20);
        orbs.add(new Orb(900,670,20));
        g.fillOval(420,200,20,20); //J
        orbs.add(new Orb(420,200,20));
        g.fillOval(450,200,20,20);
        orbs.add(new Orb(450,200,20));
        g.fillOval(480,200,20,20);
        orbs.add(new Orb(480,200,20));
        g.fillOval(450,230,20,20);
        orbs.add(new Orb(450,230,20));
        g.fillOval(450,260,20,20);
        orbs.add(new Orb(450,260,20));
        g.fillOval(425,260,20,20);
        orbs.add(new Orb(425,260,20));
        g.fillOval(530,195,20,20); //U
        orbs.add(new Orb(530,195,20));
        g.fillOval(530,225,20,20);
        orbs.add(new Orb(530,225,20));
        g.fillOval(530,255,20,20);
        orbs.add(new Orb(530,255,20));
        g.fillOval(555,255,20,20);
        orbs.add(new Orb(555,255,20));
        g.fillOval(580,255,20,20);
        orbs.add(new Orb(580,255,20));
        g.fillOval(580,225,20,20);
        orbs.add(new Orb(580,225,20));
        g.fillOval(580,195,20,20);
        orbs.add(new Orb(580,195,20));
        g.fillOval(625,195,20,20); //D
        orbs.add(new Orb(625,195,20));
        g.fillOval(625,225,20,20);
        orbs.add(new Orb(625,225,20));
        g.fillOval(625,255,20,20);
        orbs.add(new Orb(625,255,20));
        g.fillOval(650,210,20,20);
        orbs.add(new Orb(650,210,20));
        g.fillOval(650,240,20,20);
        orbs.add(new Orb(650,240,20));
        g.fillOval(665,225,20,20);
        orbs.add(new Orb(665,225,20));
        g.fillOval(710,195,20,20); //Jude's E
        orbs.add(new Orb(710,195,20));
        g.fillOval(710,225,20,20);
        orbs.add(new Orb(710,225,20));
        g.fillOval(710,255,20,20);
        orbs.add(new Orb(710,255,20));
        g.fillOval(735,255,20,20);
        orbs.add(new Orb(735,255,20));
        g.fillOval(760,255,20,20);
        orbs.add(new Orb(760,255,20));
        g.fillOval(735,225,20,20);
        orbs.add(new Orb(735,225,20));
        g.fillOval(760,195,20,20);
        orbs.add(new Orb(760,195,20));
        g.fillOval(735,195,20,20);
        orbs.add(new Orb(735,195,20));
        g.fillOval(420,350,25,25); // line
        orbs.add(new Orb(420,350,25));
        g.fillOval(470,350,25,25);
        orbs.add(new Orb(470,350,25));
        g.fillOval(520,350,25,25);
        orbs.add(new Orb(520,350,25));
        g.fillOval(570,350,25,25);
        orbs.add(new Orb(570,350,25));
        g.fillOval(620,350,25,25);
        orbs.add(new Orb(620,350,25));
        g.fillOval(670,350,25,25);
        orbs.add(new Orb(670,350,25));
        g.fillOval(720,350,25,25);
        orbs.add(new Orb(720,350,25));
        g.fillOval(770,350,25,25);
        orbs.add(new Orb(770,350,25));
        g.fillOval(820,350,25,25);
        orbs.add(new Orb(820,350,25));
        g.fillOval(870,350,25,25);
        orbs.add(new Orb(870,350,25));
        g.fillOval(920,350,25,25);
        orbs.add(new Orb(920,350,25));
        g.fillOval(970,350,25,25);
        orbs.add(new Orb(970,350,25));
        g.fillOval(1020,350,25,25);
        orbs.add(new Orb(1020,350,25));
        g.fillOval(1070,350,25,25);
        orbs.add(new Orb(1070,350,25));
        g.setColor(Color.YELLOW);
        g.fillOval(1050,200,25,25);
        orbs.add(new Orb(1050,200,25));
        g.fillOval(1020,200,25,25);
        orbs.add(new Orb(1020,200,25));
        g.fillOval(1020,230,25,25);
        orbs.add(new Orb(1020,230,25));
        g.fillOval(1050,230,25,25);
        orbs.add(new Orb(1050,230,25));
        g.setColor(Color.WHITE);
        g.fillOval(1020,600,25,25);// bottom right square
        orbs.add(new Orb(1020,600,25));
        g.fillOval(1070,600,25,25);
        orbs.add(new Orb(1070,600,25));
        g.fillOval(1120,600,25,25);
        orbs.add(new Orb(1120,600,25));
        g.fillOval(1170,550,25,25);
        orbs.add(new Orb(1170,550,25));
        g.fillOval(1170,500,25,25);
        orbs.add(new Orb(1170,500,25));
        g.fillOval(1170,450,25,25);
        orbs.add(new Orb(1170,450,25));
        g.fillOval(1170,400,25,25);
        orbs.add(new Orb(1170,400,25));
        g.fillOval(1170,360,25,25);
        orbs.add(new Orb(1170,360,25));
        g.fillOval(1170,600,25,25);
        orbs.add(new Orb(1170,600,25));
        g.fillOval(1170,650,25,25);
        orbs.add(new Orb(1170,650,25));
        g.fillOval(1170,700,25,25);
        orbs.add(new Orb(1170,700,25));
        g.fillOval(1170,750,25,25);
        orbs.add(new Orb(1170,750,25));
        g.fillOval(1120,750,25,25);
        orbs.add(new Orb(1120,750,25));
        g.fillOval(1070,750,25,25);
        orbs.add(new Orb(1070,750,25));
        g.fillOval(1020,750,25,25);
        orbs.add(new Orb(1020,750,25));
        g.fillOval(1020,700,25,25);
        orbs.add(new Orb(1020,700,25));
        g.fillOval(1020,650,25,25);
        orbs.add(new Orb(1020,650,25));
        g.fillOval(1070,700,25,25);
        orbs.add(new Orb(1070,700,25));
        g.fillOval(1120,700,25,25);
        orbs.add(new Orb(1120,700,25));
        g.fillOval(520,50,25,25); //top line
        orbs.add(new Orb(520,50,25));
        g.fillOval(570,50,25,25);
        orbs.add(new Orb(570,50,25));
        g.fillOval(620,50,25,25);
        orbs.add(new Orb(620,50,25));
        g.fillOval(670,50,25,25);
        orbs.add(new Orb(670,50,25));
        g.fillOval(720,50,25,25);
        orbs.add(new Orb(720,50,25));
        g.fillOval(770,50,25,25);
        orbs.add(new Orb(770,50,25));
        g.fillOval(820,50,25,25);
        orbs.add(new Orb(820,50,25));
        g.fillOval(870,50,25,25);
        orbs.add(new Orb(870,50,25));
        g.fillOval(920,50,25,25);
        orbs.add(new Orb(920,50,25));
        g.fillOval(970,50,25,25);
        orbs.add(new Orb(970,50,25));
        g.fillOval(1020,50,25,25);
        orbs.add(new Orb(1020,50,25));
        g.fillOval(1070,50,25,25);
        orbs.add(new Orb(1070,50,25));
        g.fillOval(1120,50,25,25);
        orbs.add(new Orb(1120,50,25));
        g.fillOval(1170,50,25,25);
        orbs.add(new Orb(1170,50,25));
        g.fillOval(1170,100,25,25);
        orbs.add(new Orb(1170,100,25));
        g.fillOval(1170,150,25,25);
        orbs.add(new Orb(1170,150,25));
        g.fillOval(1170,200,25,25);
        orbs.add(new Orb(1170,200,25));
        g.fillOval(1170,250,25,25);
        orbs.add(new Orb(1170,250,25));
        g.fillOval(1170,300,25,25);
        orbs.add(new Orb(1170,300,25));
        g.fillOval(1120,100,25,25);
        orbs.add(new Orb(1120,100,25));
        g.fillOval(1070,100,25,25);
        orbs.add(new Orb(1070,100,25));
        g.fillOval(1020,100,25,25);
        orbs.add(new Orb(1020,100,25));
        g.fillOval(970,100,25,25);
        orbs.add(new Orb(970,100,25));
        g.fillOval(920,100,25,25);
        orbs.add(new Orb(920,100,25));
        g.fillOval(870,100,25,25);
        orbs.add(new Orb(870,100,25));
        g.fillOval(820,100,25,25);
        orbs.add(new Orb(820,100,25));




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


        //change logo of JPanels
        String pacLogo = "pacmenlogo.png";
        url = getClass().getResource(pacLogo);
        if (url == null)
            throw new RuntimeException("Unable to load:  " + ghost4File);
        ImageIcon img = new ImageIcon(url);
        UIManager.put("OptionPane.informationIcon", img);

        Taskbar taskbar = Taskbar.getTaskbar();
        taskbar.setIconImage(pac);



        frame.getContentPane().add(this);  //add drawing region to window
        frame.pack();  //adjust window size to fit drawing region
        frame.setVisible(true);  //show window

        ghosts = new ArrayList<Ghost>();
        //add ghost to list and initialize ghosts
        ghost1 = new Ghost("Mr. Feinberg", displayWidth/2-(150/2)+30, displayHeight/2-(150/2)+115, this, 1);
        ghost2 = new Ghost("Mr. Olexio", displayWidth/2-(150/2)+25, displayHeight/2-(150/2)+170, this, 2);
        ghost3 = new Ghost("Dr. Dennett", displayWidth/2-(150/2)+90, displayHeight/2-(150/2)+115, this, 3);
        ghost4 = new Ghost("Mr. B", displayWidth/2-(150/2)+90, displayHeight/2-(150/2)+175, this, 4);
        ghosts.add(ghost1);
        ghosts.add(ghost2);
        ghosts.add(ghost3);
        ghosts.add(ghost4);

        //modify highscores (hard-coded) so don't use
        //highscoreNames.clear();
        //highscoreInts.clear();
       // highscoreNames.add("Jude");
        //highscoreInts.add(100);

        File scoreFile = new File("highscores.txt");
        ArrayList<String> currentHighscoreNames = new ArrayList<>();
        ArrayList<Integer> currentHighscoreInts = new ArrayList<>();
        try{
            Scanner scan = new Scanner(scoreFile);
            highscoreInts.clear();
            highscoreNames.clear();
            while(scan.hasNextLine()){ //only read the first ten lines and dispose the other ones
                currentHighscoreNames.add(scan.next());
                int a = Integer.parseInt(scan.next());
                highscoreInts.add(a);
                currentHighscoreInts.add(a);
            }
            scan.close();
        }
        catch(Exception e) {

        }

        Collections.sort(currentHighscoreInts, Comparator.reverseOrder());
        for(int num : currentHighscoreInts){
            String str = currentHighscoreNames.get(findIndexOf(highscoreInts, num));
            if(!highscoreNames.contains(str))
                highscoreNames.add(findIndexOf(currentHighscoreInts, num), str);
            else{
                ArrayList<Integer> duplicates = new ArrayList<>();
                for(int i = 0; i < highscoreNames.size(); i ++){
                   // if(highscoreNames.get(i).equals(str))
                      //  duplicates.add(
                }
            }
        }
        highscoreInts = currentHighscoreInts;

        //highscore JFrame
        JFrame highscoreFrame = new JFrame();  //create window
        JPanel scorePanel = new JPanel();
        highscoreFrame.setTitle("Highscores");  //set title of window
        highscoreFrame.setLocation(frame.getX()+frame.getWidth(), frame.getY());
        highscoreFrame.setPreferredSize(new Dimension(1470-highscoreFrame.getX(), 500));
        DefaultListModel listModel = new DefaultListModel();

        ArrayList<String> names = new ArrayList<String>();
        for(String name : highscoreNames)
            names.add(name);

        ArrayList<Integer> scores = new ArrayList<Integer>();
        for(int score : highscoreInts)
            scores.add(score);
        for (int i = 0; i < names.size(); i++) {
            String row = "<html><table><tr><td width='150'><span style='font-size:15px;'>" + names.get(i) +
                    "</td><td width='120'><span style='font-size:15px;'>" + scores.get(i) + "</td></tr></table></html>";
            listModel.addElement(row);
        }
        JList list = new JList(listModel);
        JScrollPane listPane = new JScrollPane(list);
        listPane.setPreferredSize(new Dimension(1470-highscoreFrame.getX(), 450));
        scorePanel.add(listPane);
        scorePanel.setPreferredSize(new Dimension(1470-highscoreFrame.getX(), 450));
        highscoreFrame.add(scorePanel);
        highscoreFrame.pack();
        highscoreFrame.setVisible(true);  //show window


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
            for(int i = ghost.getY()-4; i < ghost.getY() + ghost.getImageHeight()+4; i++){
                Color leftSide = new Color(maze.getRGB(ghost.getX()-6, i));
                if(leftSide.equals(Color.BLUE))
                    bool = false;
            }
        }
        else if (direction.equals("right")) {
            for(int i = ghost.getY()-4; i < ghost.getY() + ghost.getImageHeight()+4; i++){
                Color rightSide = new Color(maze.getRGB(ghost.getX() + ghost.getImageWidth() + 6, i));
                if(rightSide.equals(Color.BLUE))
                    bool = false;
            }
        }
        else if (direction.equals("up")) {
            for(int i = ghost.getX()-4; i < ghost.getX() + ghost.getImageWidth()+4; i++){
                Color topSide = new Color(maze.getRGB(i, ghost.getY() - 6));
                if(topSide.equals(Color.BLUE))
                    bool = false;
            }
        }
        else if (direction.equals("down")) {
            for(int i = ghost.getX()-4; i < ghost.getX() + ghost.getImageWidth()+4; i++){
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
                else if(!notOnWall("left")) {
                    getNewDirection();
                    return;
                }
            }
        }
        else if(direction.equals("right")){
            for(int i = 0; i < 4; i ++){
                if(imageX < displayWidth-60 && notOnWall("right"))
                    imageX ++;
                else if(!notOnWall("right")) {
                    getNewDirection();
                    return;
                }
            }
        }
        else if(direction.equals("up")){
            for(int i = 0; i < 4; i ++){
                if(imageY > 0 && notOnWall("up"))
                    imageY --;
                else if(!notOnWall("up")) {
                    getNewDirection();
                    return;
                }
            }
        }
        else if(direction.equals("down")){
            for(int i = 0; i < 4; i ++){
                if(imageY < displayHeight-55 && notOnWall("down"))
                    imageY ++;
                else if(!notOnWall("down")) {
                    getNewDirection();
                    return;
                }
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
                    if (!ghost.isRunAway() && dist < 45) {
                        JPanel finalPanel = new JPanel();
                        playAudio("pacman_death.wav");
                        JLabel label = new JLabel("<html>You died to <font color = 'red'>" + ghost.getName() + "<font color = 'black'><br>Your score is: <font color = '#A11FC2'><b>" + score + "</b></html>");
                        JLabel second = new JLabel("<html><font color = '#2BAD7D'><br>Thanks for playing Pac-Men! \uD83D\uDE01<br><font color = 'black'>Close to see if you got a highscore </html>");;
                        label.setFont(new Font("Arial", Font.BOLD, 25));
                        second.setFont(new Font("Arial", Font.BOLD, 25));
                        finalPanel.add(label);
                        finalPanel.add(second);
                        second.setPreferredSize(new Dimension(350, 225));
                        finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));
                        finalPanel.setPreferredSize(new Dimension(350, 225));
                        JOptionPane optionPane = new JOptionPane();
                        optionPane.setPreferredSize(new Dimension(350, 0));
                        UIManager.put("OptionPane.okButtonText", "Close");
                        JOptionPane.showMessageDialog(frame, finalPanel, "Final Score", JOptionPane.INFORMATION_MESSAGE, getTeacherIcon(ghost.getName()));
                        checkForHighscore();
                        String[] args = new String[0];
                        main(args);
                        return;
                    }
                    else if(ghost.isRunAway() && dist < 40 && ghost.getValidToKilled()){
                        //g.clearRect(ghost.getX(), ghost.getY(), ghost.getImageWidth(), ghost.getImageHeight());
                        ghost.setKill();
                        score += pointFunction("ghost", new Orb(0, 0, 0));
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
                    Ghost.setCanRunAway();
                } else openerTimer++;

                for (int x = pacCenterX - 7; x < pacCenterX + 7; x++) { //check for orbs
                    for(int y = pacCenterY - 7; y < pacCenterY + 7; y++) {
                        Color col = new Color(maze.getRGB(x, y));
                        if (col.equals(Color.WHITE) || col.equals(Color.YELLOW)) {
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
                            g.clearRect(closest.getX(), closest.getY(), closest.getSize(), closest.getSize());
                            score += pointFunction("orb", closest);
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

    public int pointFunction(String a, Orb orb){
        if(a.equals("orb"))
            return (int)Math.pow(orb.getValue(), -1*((gameTime-200)/200));
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
        if (soundTimer > 12 || file.equals("pacman_death.wav")) {
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

    public void getNewDirection(){
        if(direction.equals("left"))
            direction = "right";
        else if(direction.equals("right"))
            direction = "left";
        else if(direction.equals("up"))
            direction = "down";
        else if(direction.equals("down"))
            direction = "up";
    }

    public void checkForHighscore(){
        JPanel finalPanel = new JPanel();
        JLabel label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 25));
        finalPanel.add(label);
        JOptionPane optionPane = new JOptionPane();
        if(10 > highscoreInts.size() || score > highscoreInts.get(9) && score > 0){
            finalPanel.setPreferredSize(new Dimension(350, 150));
            optionPane.setPreferredSize(new Dimension(350, 0));
            UIManager.put("OptionPane.okButtonText", "Save and close");
            label.setText("<html>You're on the leaderboard!<br>Put your name here <br>(without spaces):</html>");
            JTextField textField = new JTextField(10);
            finalPanel.add(textField);
            JOptionPane.showMessageDialog(frame, finalPanel, "Highscore Results", JOptionPane.INFORMATION_MESSAGE);
            String name = textField.getText();
            while(name == null || name.length() < 1 || name.indexOf(" ") != -1){
                finalPanel.setPreferredSize(new Dimension(500, 50));
                label.setText("<html>Please provide a<font color='red'> name without spaces</html>");
                JOptionPane.showMessageDialog(frame, finalPanel, "Highscore Results", JOptionPane.INFORMATION_MESSAGE);
                name = textField.getText();
            }
            File scoreFile = new File("highscores.txt");
            ArrayList<String> currentHighscoreNames = new ArrayList<>();
            ArrayList<Integer> currentHighscoreInts = new ArrayList<>();
            try{
                Scanner scan = new Scanner(scoreFile);
                int i = 0;
                while(scan.hasNextLine() && i < 9){ //only read the first ten lines and dispose the other ones
                    currentHighscoreNames.add(scan.next());
                    int a = Integer.parseInt(scan.next());
                    currentHighscoreInts.add(a);
                    i ++;
                }
                scan.close();
                new PrintWriter("highscores.txt").close();
                PrintWriter writer = new PrintWriter("highscores.txt");
                for(i = 0; i < currentHighscoreNames.size(); i++){
                    writer.print(currentHighscoreNames.get(i) + " ");
                    writer.println(currentHighscoreInts.get(i));
                }
                writer.print(name + " ");
                writer.print(score);
                writer.close();
            }
            catch(Exception e) {
                System.err.println(e);
            }
            //printAll(highscoreInts);
            //printAll(highscoreNames);
        }
        else{
            finalPanel.setPreferredSize(new Dimension(350, 100));
            optionPane.setPreferredSize(new Dimension(350, 0));
            label.setPreferredSize(new Dimension(350, 100));
            UIManager.put("OptionPane.okButtonText", "Exit game");
            if(score > 0)
            label.setText("<html>The score was lower than<br>the 10th highest score,<br> but thanks for playing! \uD83D\uDE01</html>");
            else label.setText("<html>You got 0 points...<br> but thanks for playing! \uD83D\uDE01</html>");
            JOptionPane.showMessageDialog(frame, finalPanel, "Highscore Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public <E> int findIndexOf(List<E> list, int obj){
        for(int i = 0; i < list.size(); i ++){
            if(list.get(i).equals(obj))
                return i;
        }
        return -1;
    }

    public <E> void printAll(List<E> list){
        for(E obj : list)
            System.out.println(obj);
    }

    public ImageIcon getTeacherIcon(String name){
        if(name.equals("Mr. Feinberg"))
            return ghost1ImageIcon;
        else if(name.equals("Mr. Olexio"))
            return ghost2ImageIcon;
        else if(name.equals("Dr. Dennett"))
            return ghost3ImageIcon;
        else if(name.equals("Mr. B"))
            return ghost4ImageIcon;
        else return null;
    }
}