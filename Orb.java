public class Orb{
    private int imageX;
    private int imageY;
    private int size;
    private int value;

    public Orb(int x, int y, int size){ //x, y = top left corner
        imageX = x;
        imageY = y;
        this.size = size;
        value = 100;
    }

    public Orb(int x, int y, int size, int v){
        imageX = x;
        imageY = y;
        this.size = size;
        value = v;
    }

    public int getX(){
        return imageX;
    }

    public int getY(){
        return imageY;
    }

    public int getSize(){
        return size; //FIX
    }

    public int getValue(){
        return value;
    }
}
