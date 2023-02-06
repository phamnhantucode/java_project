package Exercise1;

public class Thread1 extends Thread{
    private int n;
    private long sum = 1;
    public Thread1(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 1; i <= n; i++) {
            sum *= 2*i;
        }
    }

    public long getResult() {
        return sum;
    }
}
