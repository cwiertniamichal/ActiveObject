
public class Proxy {
    ConsumerQueue consumerQueue;
    ProducerQueue producerQueue;
    Servant servant;
    Scheduler scheduler;

    public Proxy(ConsumerQueue c, ProducerQueue p, Servant servant, Scheduler scheduler){
        this.consumerQueue = c;
        this.producerQueue = p;
        this.servant = servant;
        this.scheduler = scheduler;
    }

    public Future get(int number){
        Future res = new Future(5);
        Get get = new Get(number, servant, res);
        scheduler.m.enqueueConsumer(get);
        return get.res;
    }

    public Future insert(int number){
        Future res = new Future();
        Insert ins = new Insert(number, servant, res);
        scheduler.m.enqueueProducer(ins);
        return ins.res;
    }
}
