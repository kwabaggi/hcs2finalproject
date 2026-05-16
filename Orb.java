public class Orb{
    private int imageX;
    private int imageY;

    public Orb(int x, int y){ //x, y =  top left corner
        imageX = x;
        imageY = y;
    }

    public int getX(){
        return imageX;
    }

    public int getY(){
        return imageY;
    }
}
