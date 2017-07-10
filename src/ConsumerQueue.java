import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumerQueue {
    int take = 0;
    int in = 0;
    int size;
    Get[] consumerQueue;
    Lock l1 = new ReentrantLock();
    Condition full = l1.newCondition();
    Scheduler scheduler;

    public ConsumerQueue(int size){
        this.size = size;
        this.consumerQueue = new Get[size];
    }
/*
    public boolean isEmpty(){
        if(consumerQueue[take] == null)
            return true;
        return false;
    }

    public boolean fullConsumerQueue(){
        if(consumerQueue[in] != null)
            return true;
        else
            return false;
    }

    public void enqueue(Get get) {
        l1.lock();
        try {
            while(fullConsumerQueue()) {
                try {
                    full.await();
                } catch (InterruptedException e) {

                }
            }
            consumerQueue[in] = get;
            in = (in + 1) % size;
            scheduler.ntd.signal();
        }finally{
            l1.unlock();
        }
    }

    public Get dequeue(){
        Get result;
        l1.lock();
        try{
            if(consumerQueue[take] == null)
                result = null;
            else {
                result = consumerQueue[take];
                take = (take + 1) % size;
            }
            /*try {
                while (consumerQueue[take] == null) {
                    scheduler.consumer.await();
                }
            }catch (InterruptedException e){

            }

        }finally {
            full.signal();
            l1.unlock();
        }
        //return result;
    }

    public Get check(){
        Get result;
        l1.lock();
        try{
            if(consumerQueue[take] == null)
                result = null;
            else {
                result = consumerQueue[take];
            }
        }finally {
            full.signal();
            l1.unlock();
        }
        return result;
    }
    */
}
