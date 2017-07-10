
public class Servant {
    int[] bufor;
    int insert_id = 0;
    int size;
    int get_id = 0;

    public Servant(int size){

        this.size = size;
        this.bufor = new int[size];

    }

    public boolean insert(int number){
        System.out.println("Inserting " + number + " elements.");
        for(int i = number; i > 0; i--){
            bufor[insert_id] = 1;
            insert_id = (insert_id + 1)%size;
        }
        System.out.println("Inserting done");
        return true;
    }

    public int [] get(int number){
        System.out.println("Getting " + number + " elements");
        int[] res = new int[number];
        for(int i = number, j = 0; i > 0 && j < number; i--, j++){
            res[j] = bufor[get_id];
            bufor[get_id] = 0;
            get_id = (get_id + 1)%size;
        }
        System.out.println("Getting done");
        return res;
    }

    public boolean empty(){
        if(bufor[get_id] == 0)
            return true;
        return false;
    }

    public boolean full(){
        if(bufor[insert_id] != 0)
            return true;
        return false;
    }

    public int elementsIn(){
        int res = insert_id - get_id;
        if(res < 0){
            res = 10 + res;
            return res;
        }
        else{
            return res;
        }

    }
}
