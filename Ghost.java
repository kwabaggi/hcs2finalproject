public class Ghost {
    private boolean runAway;
    private int runAwayTimer;
    private int imageX;
    private int imageY;
    private int spawnX;
    private int spawnY;
    private Display display;
    private int direction;
    private int directionCounter;
    private int imageWidth;
    private int imageHeight;
    private int type;
    private int killTimer;
    private boolean isKilled;
    private boolean validToKilled;
    private int centerX;
    private int centerY;

    public Ghost(int imageX, int imageY, Display disp, int type){
        this.imageX = imageX;
        this.imageY = imageY;
        spawnX = imageX;
        spawnY = imageY;
        display = disp;
        imageWidth = disp.getGhostImg(type).getWidth(disp.getFrame());
        imageHeight = disp.getGhostImg(type).getHeight(disp.getFrame());
        this.type = type;
        centerX = imageX + imageWidth/2;
        centerY = imageY + imageHeight/2;
        validToKilled = true;
    }

    public void run() {
        if (runAway && !isKilled) {
            if (runAwayTimer < 240)
            runAwayTimer ++;
            else{
                runAwayTimer = 0;
                runAway = false;
                display.changeGhostImage(type, getRegular(type));
            }
        }
        else if(isKilled && runAway){
            if(killTimer < 140)
                killTimer ++;
            else{
                killTimer = 0;
                isKilled = false;
                runAway = false;
                display.respawnGhost(type, this);
            }
        }
        else if((int)(Math.random()*1000) == 0) {
            runAway = true;
            display.changeGhostImage(type, "vulnerableghost.png");
        }

    }

    public String getRegular(int type){
        if(type == 1)
            return "feinbergghost.png";
        else if(type == 2)
            return "olexioghost.png";
        else if(type == 3)
            return "dennettghost.png";
        else return "bologneseghost.jpg";
    }

    public int getX(){
        return imageX;
    }

    public int getY(){
        return imageY;
    }

    public void changeLocation() {
        if (!isKilled) {
            if (directionCounter >= 10) {
                direction = (int) (Math.random() * 4);
                directionCounter = 0;
            } else directionCounter++;
            if (direction == 0) {
                for (int i = 0; i < 11; i++) {
                    if (imageX > 0 && display.ghostNotOnWall("left", this))
                        imageX--;
                }
            } else if (direction == 1) {
                for (int i = 0; i < 11; i++) {
                    if (imageX < display.getDisplayWidth() - 60 && display.ghostNotOnWall("right", this))
                        imageX++;
                }
            } else if (direction == 2) {
                for (int i = 0; i < 11; i++) {
                    if (imageY > 0 && display.ghostNotOnWall("up", this))
                        imageY--;
                }
            } else if (direction == 3) {
                for (int i = 0; i < 11; i++) {
                    if (imageY < display.getDisplayHeight() - 55 && display.ghostNotOnWall("down", this))
                        imageY++;
                }
            }
            centerX = imageX + imageWidth/2;
            centerY = imageY + imageHeight/2;
        }
    }

    public int getImageWidth(){
        return imageWidth;
    }

    public int getImageHeight(){
        return imageHeight;
    }

    public void changeDirection(){
        direction = (int) (Math.random() * 4);
        directionCounter = 0;
    }

    public boolean isRunAway(){
        return runAway;
    }

    public int getSpawnX(){
        return spawnX;
    }

    public int getSpawnY(){
        return spawnY;
    }

    public void setKill(){
        isKilled = true;
        runAway = true;
        imageX = spawnX;
        imageY = spawnY;
        display.changeGhostImage(type, "redx.png");
        display.playAudio("pacman_eatghost.wav");
        validToKilled = false;
    }

    public boolean getValidToKilled(){
        return validToKilled;
    }

    public int getCenterX(){
        return centerX;
    }

    public int getCenterY(){
        return centerY;
    }
}
