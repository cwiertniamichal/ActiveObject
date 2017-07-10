import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler implements Runnable {
    Servant servant;
    ConsumerQueue consumerQueue;
    ProducerQueue producerQueue;
    Monitor m;


    public Scheduler(Servant s, ConsumerQueue c, ProducerQueue p){
        this.servant = s;
        this.consumerQueue = c;
        this.producerQueue = p;
        m = new Monitor(producerQueue, consumerQueue);
    }

    public void run(){
        MethodRequest task;
        int bufEl;
        Get get;
        Insert ins;
        while(true){
            bufEl = servant.elementsIn();
            System.out.println(bufEl);
            task = m.getNextElement(bufEl);
            if( task instanceof Insert) {
                ins = (Insert) task;
                System.out.println("TIMESTAMP: " + ins.timestamp);
                ins.call();
                System.out.println("In scheduler" + ins.res.done);
            }
            else {
                get = (Get) task;
                System.out.println("TIMESTAMP: " + get.timestamp);
                get.call();
                System.out.println("In scheduler" + get.res.done);
            }





        }
    }

}
