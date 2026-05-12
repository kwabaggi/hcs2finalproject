public class Ghost {
    private boolean runAway;
    private int runAwayTimer;


    public Ghost(){

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
}
