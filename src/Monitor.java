import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {

    int timestamp;
    int size = 10;
    int consumerIn = 0;
    int consumerTake = 0;
    int producerIn = 0;
    int producerTake = 0;
    ProducerQueue producerQueue;
    ConsumerQueue consumerQueue;
    Lock l1 = new ReentrantLock();
    Condition fullProducer = l1.newCondition();
    Condition fullConsumer = l1.newCondition();
    Condition producerEmpty = l1.newCondition();
    Condition consumerEmpty = l1.newCondition();
    Condition empty = l1.newCondition();

    public Monitor(ProducerQueue producerQueue, ConsumerQueue consumerQueue){
        this.producerQueue = producerQueue;
        this.consumerQueue = consumerQueue;
        this.timestamp = 0;
    }

    public boolean fullProducerQueue(){
        if(producerQueue.producerQueue[producerIn] != null)
            return true;
        else
            return false;
    }

    public void enqueueProducer(Insert ins) {
        l1.lock();
        try {
            while (fullProducerQueue()) {
                try {
                    fullProducer.await();
                } catch (InterruptedException e) {

                }
            }
            ins.timestamp = timestamp;
            timestamp += 1;
            producerQueue.producerQueue[producerIn] = ins;
            producerIn = (producerIn + 1) % size;
           // System.out.println("Inserted to producer queue");
        }finally{
            empty.signal();
            producerEmpty.signal();
            l1.unlock();
        }
    }

    public Insert dequeueProducer(){
        Insert res;

            if(producerQueue.producerQueue[producerTake] == null)
                res = null;
            else{
                res = producerQueue.producerQueue[producerTake];
                producerQueue.producerQueue[producerTake] = null;
                producerTake = (producerTake + 1)%size;
            }

        return res;
    }


    public boolean fullConsumerQueue(){
        if(consumerQueue.consumerQueue[consumerIn] != null)
            return true;
        else
            return false;
    }

    public void enqueueConsumer(Get get) {
        l1.lock();
        try {
            while(fullConsumerQueue()) {
                try {
                    fullConsumer.await();
                } catch (InterruptedException e) {

                }
            }
            get.timestamp = timestamp;
            timestamp += 1;
            consumerQueue.consumerQueue[consumerIn] = get;
            consumerIn = (consumerIn + 1) % size;
           // System.out.println("Inserted to consumer queue");
        }finally{
            empty.signal();
            consumerEmpty.signal();
            l1.unlock();
        }
    }

    public Get dequeueConsumer(){
        Get result;


            if(consumerQueue.consumerQueue[consumerTake] == null)
                result = null;
            else {
                result = consumerQueue.consumerQueue[consumerTake];
                consumerQueue.consumerQueue[consumerTake] = null;
                consumerTake = (consumerTake + 1) % size;
            }

        return result;
    }

    public Get checkConsumer(){
        Get result;


            if(consumerQueue.consumerQueue[consumerTake] == null)
                result = null;
            else {
                result = consumerQueue.consumerQueue[consumerTake];
            }

        return result;
    }

    public Insert checkProducer(){
        Insert res;

            if(producerQueue.producerQueue[producerTake] == null)
                res = null;
            else{
                res = producerQueue.producerQueue[producerTake];
            }

        return res;
    }

    public boolean isEmptyConsumer(){
        if(consumerQueue.consumerQueue[consumerTake] == null)
            return true;
        return false;
    }

    public boolean isEmptyProducer(){
        if(producerQueue.producerQueue[producerTake] == null)
            return true;
        return false;
    }

    public MethodRequest getNextElement(int elements){
        Get consumer;
        Insert producer;
        MethodRequest result;
        l1.lock();
        try {
            while (isEmptyConsumer() == true && isEmptyProducer() == true) {
                try {
                        empty.await();
                } catch (InterruptedException e) {

                }
            }
            consumer = checkConsumer();
            producer = checkProducer();
            if (consumer != null && producer != null) {
                if (consumer.timestamp < producer.timestamp) {
                    if (consumer.number <= elements) {
                        result = dequeueConsumer();
                    } else {
                        result = dequeueProducer();
                    }
                } else {
                    if (producer.number <= size - elements) {
                        result = dequeueProducer();
                    } else {
                        result = dequeueConsumer();
                    }
                }
            } else if (consumer != null) {
                if (consumer.number > elements) {
                    while (isEmptyProducer() == true) {
                        try {
                            producerEmpty.await();
                        } catch (InterruptedException e) {

                        }
                    }
                    result = dequeueProducer();
                } else
                    result = dequeueConsumer();
            } else {
                if (producer.number > size - elements) {
                    while (isEmptyConsumer() == true) {
                        try {
                            consumerEmpty.await();
                        } catch (InterruptedException e) {

                        }
                    }
                    result = dequeueConsumer();
                } else
                    result = dequeueProducer();
            }
        }
        finally{
            l1.unlock();
        }
        return result;

    }

}
