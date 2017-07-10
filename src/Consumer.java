import java.util.ArrayList;
import java.util.Random;

public class Consumer implements Runnable {
    Random generator = new Random();
    Proxy proxy;
    Future res;
    long start_time;
    ArrayList<Point> points;

    public Consumer(Proxy proxy, long start_time, ArrayList<Point> points){
        this.start_time = start_time;
        this.proxy = proxy;
        this.points = points;
    }

    public void run(){
        double tmp;
        long stop = System.currentTimeMillis();
        int step = 1;
        int number = 0;
        int services = 0;
        int benchmarks = 20;

        while(true){
            res = proxy.get(generator.nextInt(4) + 1);
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
            System.out.println("Consumer get done ");
            services++;
            System.out.println(stop - start_time);
            stop = System.currentTimeMillis();

        }

    }

}

