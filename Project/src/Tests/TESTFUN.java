package Tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by boyinzhang on 4/22/17.
 */
public class TESTFUN {
    public static void main(String[] args){
        String abc = new String("COLLECT 3 1 5 4 0");
        System.out.println();

        Scanner scanner = new Scanner(abc.substring(8));
        List<Integer> list = new ArrayList<Integer>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }

        for(Integer i : list){
            System.out.println(i+"\n");
        }

    }
}
