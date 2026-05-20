public class Orb{
    private int imageX;
    private int imageY;
    private int size;

    public Orb(int x, int y){ //x, y = top left corner
        imageX = x;
        imageY = y;
        this.size = size;
    }

    public int getX(){
        return imageX;
    }

    public int getY(){
        return imageY;
    }

    public int getSize(){
        return 25; //FIX
    }
}
