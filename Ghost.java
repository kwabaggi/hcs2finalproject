public class Ghost {
    private boolean runAway;
    private int runAwayTimer;
    private int imageX;
    private int imageY;

    public Ghost(int imageX, int imageY){
        this.imageX = imageX;
        this.imageY = imageY;
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

    }
}
