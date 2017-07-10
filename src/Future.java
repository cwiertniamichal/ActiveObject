public class Future {
    int size;
    int[] tab;
    boolean done;

    public Future(){
        this.done = false;
    }

    public Future(int size){
        this.done = false;
        this.size = size;
        tab = new int[size];
    }
}
