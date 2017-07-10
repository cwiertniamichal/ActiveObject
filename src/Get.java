
public class Get extends MethodRequest {
    int number;
    Servant servant;
    Future res;
    int timestamp;

    public Get(int number, Servant servant, Future res){
        this.number = number;
        this.servant = servant;
        this.res = res;
    }

    public void call(){
        res.tab = servant.get(number);
        res.done = true;
    }
}
