import java.util.ArrayList;
import java.util.Random;

public class Producer implements Runnable {
    Random generator = new Random();
    Proxy proxy;
    Future res;
    int id;
    long start_time;
    ArrayList<Point> points;

    public Producer(Proxy proxy, long start_time, int id, ArrayList<Point> points){
        this.start_time = start_time;
        this.proxy = proxy;
        this.id = id;
        this.points = points;
    }


    public void run() {
        double tmp;
        long stop = System.currentTimeMillis();
        int step = 1;
        int number = 0;
        int services = 0;
        int benchmarks = 20;

        while (true){
            res = proxy.insert(generator.nextInt(4) + 1);
            while(res.done != true){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {

                }
                for(int i = 0; i < 1000; i++){
                    tmp = Math.pow(Math.cos(i) * Math.sin(i), 5);
                }
                number++;
                if(stop - start_time > step * 1000 && step < benchmarks) {
                    Point p =  new Point(step, number, services);
                    points.add(p);
                    step++;
                }
                stop = System.currentTimeMillis();


            }
            services++;
            System.out.println("Producer got done");
            stop = System.currentTimeMillis();

        }

    }
}
