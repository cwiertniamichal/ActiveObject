import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerActiveObject {
    static ArrayList<ArrayList<Point>> points = new ArrayList<ArrayList<Point>>(2000);
    public static void main(String[] args){
        long stop;
        int n = 2; // liczba watkow
        int benchmarks = 20;

        Servant ser = new Servant(10);
        ConsumerQueue consumerQueue = new ConsumerQueue(10);
        ProducerQueue producerQueue = new ProducerQueue(10);
        Scheduler s = new Scheduler(ser, consumerQueue, producerQueue);
        Proxy proxy = new Proxy(consumerQueue, producerQueue, ser, s);

        Consumer[] k = new Consumer[n];
        Producer[] p = new Producer[n];
        Thread[] kt = new Thread[n];
        Thread[] pt = new Thread[n];
        Thread st;

        long start = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            ArrayList<Point> p1 = new ArrayList<Point>();
            ArrayList<Point> p2 = new ArrayList<Point>();
            points.add(p1);
            points.add(p2);
            k[i] = new Consumer(proxy, start, p1);
            p[i] = new Producer(proxy, start, n + i, p2);
        }

        for (int i = 0; i < n; i++) {
            kt[i] = new Thread(k[i]);
            pt[i] = new Thread(p[i]);
        }

        st = new Thread(s);

        for (int i = 0; i < n; i++) {
            kt[i].start();
            pt[i].start();
        }

        st.start();

        stop = System.currentTimeMillis();
        while(stop - start < benchmarks * 1000){
            stop = System.currentTimeMillis();
        }

        List<Point> points2 = new ArrayList<Point>();
        int[] tab = new int[benchmarks];
        int[] serv = new int[benchmarks];

        for (ArrayList<Point> list : points) {
            for (Point point : list) {
                tab[point.x] += point.number;
                serv[point.x] += point.services;
            }
        }
        for(int i = 1; i < benchmarks; i++){
            Point p2 = new Point(i, tab[i], serv[i]);
            points2.add(p2);
        }

        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream("AO.txt"));
            for (Point point : points2)
                pw.println(point.toString()); // call toString() on club, like club.toString()
            pw.flush();
            pw.close();
        }catch (FileNotFoundException e){}

    }

}
