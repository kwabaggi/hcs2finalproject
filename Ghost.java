public class Ghost {
    private boolean runAway;
    private int runAwayTimer;
    private int imageX;
    private int imageY;
    private Display display;
    private int direction;
    private int directionCounter;

    public Ghost(int imageX, int imageY, Display disp){
        this.imageX = imageX;
        this.imageY = imageY;
        display = disp;
    }

    public void run() {
        if (runAway) {
            if (runAwayTimer < 480)
            runAwayTimer ++;
            else{
                runAwayTimer = 0;
                runAway = false;
            }
        }
        else if((int)(Math.random()*100) == 0)
        runAway = true;

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
            for(int i = 0; i < 6; i ++){
                if(imageX > 0 && display.notOnWall("left"))
                    imageX --;
            }
        }
        else if(direction == 1){
            for(int i = 0; i < 6; i ++){
                if(imageX < display.getDisplayWidth()-60 && display.notOnWall("right"))
                    imageX ++;
            }
        }
        else if(direction == 2){
            for(int i = 0; i < 6; i ++){
                if(imageY > 0 && display.notOnWall("up"))
                    imageY --;
            }
        }
        else if(direction == 3){
            for(int i = 0; i < 6; i ++){
                if(imageY < display.getDisplayHeight()-55 && display.notOnWall("down"))
                    imageY ++;
            }
        }
    }
}
