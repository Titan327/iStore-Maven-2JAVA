package iStore_ltd;

import java.util.ArrayList;
import java. util.List;
public class Main {
    public static void main(String [] args){
        List<String>name = new ArrayList<String>();
        name.add("daniel");
        name.add("bob");
        name.add("Mia");
        name.forEach(n->System.out.println(n));

    }

}