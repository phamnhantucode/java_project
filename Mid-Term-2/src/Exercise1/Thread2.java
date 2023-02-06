package Exercise1;

public class Thread2 extends Thread{
    private int n;
    private double sum = 0;
    public Thread2(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 1; i <= n; i++) {
            sum += i / (i+1) * 1.0;
        }
    }

    public double getResult() {
        return sum;
    }
}
