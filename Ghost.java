public class Ghost {
    private boolean runAway;
    private int runAwayTimer;
    private int imageX;
    private int imageY;
    private int spawnX;
    private int spawnY;
    private Display display;
    private String direction;
    private int directionCounter;
    private int imageWidth;
    private int imageHeight;
    private int type;
    private int killTimer;
    private boolean isKilled;
    private boolean validToKilled;
    private int centerX;
    private int centerY;
    private boolean onlyGoingLeft;
    private int leftTimer;
    static boolean canRunAway;
    private String name;
    private boolean canPlayAudio;

    public Ghost(String name, int imageX, int imageY, Display disp, int type){
        this.name = name;
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
        direction = "left";
    }

    public void run() {
        if (runAway && !isKilled) {
            if (runAwayTimer <= 300) {
                if(runAwayTimer >= 200){
                    if(runAwayTimer < 220 || (runAwayTimer >= 240 && runAwayTimer < 260) || (runAwayTimer >= 280))
                    display.changeGhostImage(type, "changingghost.png");
                    else display.changeGhostImage(type, "vulnerableghost.png");
                }
                runAwayTimer++;
            }
            else{
                runAwayTimer = 0;
                runAway = false;
                display.changeGhostImage(type, getRegular(type));
            }
        }
        else if(isKilled && runAway){
            if(killTimer < 140) {
                killTimer++;
                if(canPlayAudio) {
                    display.playAudio("ghosttohome.wav");
                    canPlayAudio = false;
                }
            }
            else{
                killTimer = 0;
                isKilled = false;
                runAway = false;
                display.respawnGhost(type, this);
            }
        }
        else if(canRunAway && (int)(Math.random()*1250) == 0) {
            runAway = true;
            display.playAudio("ghosttoblue.wav");
            display.changeGhostImage(type, "vulnerableghost.png");
            canPlayAudio = true;
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
            if(!onlyGoingLeft) {
                if (directionCounter >= 5) {
                    int x = (int) (Math.random() * 4);
                    if(x == 0)
                        direction = "left";
                    else if(x == 1)
                        direction = "right";
                    else if(x == 2)
                        direction = "up";
                    else if(x == 3)
                        direction = "down";
                    /*Location pacCenter = new Location(display.getPacCenterX(), display.getPacCenterY());
                    Location closest = new Location(imageX - 1, imageY);
                    direction = "left";
                    double closestDist = Display.distance(new Location(closest.getX(), closest.getY()), pacCenter);

                    double rightDist = Display.distance(new Location(imageX + 1, imageY), pacCenter);
                    if (rightDist < closestDist) {
                        direction = "right";
                        closestDist = rightDist;
                    }
                    double downDist = Display.distance(new Location(imageX, imageY + 1), pacCenter);
                    if (downDist < closestDist) {
                        direction = "down";
                        closestDist = downDist;
                    }
                    double upDist = Display.distance(new Location(imageX, imageY - 1), pacCenter);
                    if (upDist < closestDist)
                        direction = "up";*/
                    directionCounter = 0;
                } else directionCounter++;

                if (direction.equals("left")) {
                    for (int i = 0; i < 14; i++) {
                        if (imageX > 0 && display.ghostNotOnWall("left", this))
                            imageX--;
                    }
                } else if (direction.equals("right")) {
                    for (int i = 0; i < 14; i++) {
                        if (imageX < display.getDisplayWidth() - 60 && display.ghostNotOnWall("right", this))
                            imageX++;
                    }
                } else if (direction.equals("up")) {
                    for (int i = 0; i < 14; i++) {
                        if (imageY > 0 && display.ghostNotOnWall("up", this))
                            imageY--;
                    }
                } else if (direction.equals("down")) {
                    for (int i = 0; i < 14; i++) {
                        if (imageY < display.getDisplayHeight() - 55 && display.ghostNotOnWall("down", this))
                            imageY++;
                    }
                }
            }
            else{
                if(leftTimer < 40) {
                    leftTimer ++;
                    for (int i = 0; i < 10; i++) {
                        if (imageX > 0 && display.ghostNotOnWall("left", this))
                            imageX--;
                    }
                }
                else{
                    onlyGoingLeft = false;
                    leftTimer = 0;
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
        int x = (int) (Math.random() * 4);
        if(x == 0)
            direction = "left";
        else if(x == 1)
            direction = "right";
        else if(x == 2)
            direction = "up";
        else if(x == 3)
            direction = "down";
        //onlyGoingLeft = true;
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

    public static void setCanRunAway(){
        canRunAway = true;
    }

    public String getName(){
        return name;
    }
}
