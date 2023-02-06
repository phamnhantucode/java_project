package Exercise1;

public class Thread3 extends Thread{
    private int n;
    private int x;
    private long s = 0;
    public Thread3(int x, int n) {
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 1; i <= n; i++) {
            s += Math.pow(x, i);
        }
    }

    public double getResult() {
        return s;
    }
}
