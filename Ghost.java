public class Ghost {
    private boolean runAway;
    private int runAwayTimer;
    private int imageX;
    private int imageY;
    private Display display;
    private int direction;
    private int directionCounter;
    private int imageWidth;
    private int imageHeight;
    private int type;

    public Ghost(int imageX, int imageY, Display disp, int type){
        this.imageX = imageX;
        this.imageY = imageY;
        display = disp;
        imageWidth = disp.getGhostImg(type).getWidth(disp.getFrame());
        imageHeight = disp.getGhostImg(type).getHeight(disp.getFrame());
        this.type = type;
    }

    public void run() {
        if (runAway) {
            if (runAwayTimer < 240)
            runAwayTimer ++;
            else{
                runAwayTimer = 0;
                runAway = false;
                display.changeGhostImage(type, getRegular(type));
            }
        }
        else if((int)(Math.random()*750) == 0) {
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

    public void changeLocation(){
        if(directionCounter >= 10) {
            direction = (int) (Math.random() * 4);
            directionCounter = 0;
        }
        else directionCounter ++;
        if(direction == 0){
            for(int i = 0; i < 11; i ++){
                if(imageX > 0 && display.ghostNotOnWall("left", this))
                    imageX --;
            }
        }
        else if(direction == 1){
            for(int i = 0; i < 11; i ++){
                if(imageX < display.getDisplayWidth()-60 && display.ghostNotOnWall("right", this))
                    imageX ++;
            }
        }
        else if(direction == 2){
            for(int i = 0; i < 11; i ++){
                if(imageY > 0 && display.ghostNotOnWall("up", this))
                    imageY --;
            }
        }
        else if(direction == 3){
            for(int i = 0; i < 11; i ++){
                if(imageY < display.getDisplayHeight()-55 && display.ghostNotOnWall("down", this))
                    imageY ++;
            }
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
}
