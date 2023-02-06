package Exercise1;

import java.util.Scanner;

public class MainThread {

    public static void main(String[] args) {
        double result;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter x: ");
        int x = sc.nextInt();
        System.out.println("Enter n: ");
        int n = sc.nextInt();
        //Thread
        Thread1 f1 = new Thread1(n);
        Thread2 f2 = new Thread2(n);
        Thread3 f3 = new Thread3(x,n);
        //run thread
        try {
            f1.start();
            f2.start();
            f3.start();
            f1.join();
            f2.join();
            f3.join();
            result = f1.getResult() + f2.getResult() + f3.getResult();
            System.out.println("S = F1(n) + F2(n) + F3(x,n) = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
