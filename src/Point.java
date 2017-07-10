import java.io.Serializable;


public class Point implements Serializable {
    int x;
    int number;
    int services;

    public Point(int x, int number, int services){
        this.x = x;
        this.number = number;
        this.services = services;
    }

    public String toString(){
        return x + " " + number + " " + services;
    }
}
