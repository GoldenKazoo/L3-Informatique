public class Main {
    public static void main(String[] args) {
        Thread f1 = new Thread() {
            public void run() {
                print(Thread.currentThread().ThreadId());
            }
        }
        f1.start();
    }
}