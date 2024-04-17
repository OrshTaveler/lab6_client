package initials;

public class Coordinates {
    private double x;
    private long y; //Значение поля должно быть больше -660, Поле не может быть null

    public Coordinates(double x,long y){
        this.x = x;
        this.y = y;

    }

    public double getX() {
        return x;
    }

    public long getY() {
        return y;
    }
}
