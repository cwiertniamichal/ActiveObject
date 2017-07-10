import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerQueue {
    int in = 0;
    int take = 0;
    int size;
    Insert[] producerQueue;
    Lock l1 = new ReentrantLock();
    Condition full = l1.newCondition();
    Scheduler scheduler;

    public ProducerQueue(int size){
        this.size = size;
        this.producerQueue = new Insert[size];
    }
/*
    public boolean isEmpty(){
        if(producerQueue[take] == null)
            return true;
        return false;
    }

    public boolean fullProducerQueue(){
        if(producerQueue[in] != null)
            return true;
        else
            return false;
    }

    public void enqueue(Insert ins) {
        l1.lock();
        try {
            while (fullProducerQueue()) {
                try {
                    full.await();
                } catch (InterruptedException e) {

                }
            }
            producerQueue[in] = ins;
            in = (in + 1) % size;
            scheduler.ntd.signal();
        }finally{
            l1.unlock();
        }
    }

    public Insert dequeue(){
        Insert res;
        l1.lock();
        try{
            if(producerQueue[take] == null)
                res = null;
            else{
                res = producerQueue[take];
                take = (take + 1)%size;
            }
        }finally {
            full.signal();
            l1.unlock();
        }
        return res;
    }

    public Insert check(){
        Insert res;
        l1.lock();
        try{
            if(producerQueue[take] == null)
                res = null;
            else{
                res = producerQueue[take];
            }
        }finally {
            full.signal();
            l1.unlock();
        }
        return res;
    }*/
}
