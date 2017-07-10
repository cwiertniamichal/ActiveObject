
public class Insert extends MethodRequest {
    int number;
    Servant servant;
    Future res;
    int timestamp;

    public Insert(int number, Servant servant, Future res){
        this.number = number;
        this.servant = servant;
        this.res = res;
    }

    public void call(){

        res.done = servant.insert(number);
    }
}
