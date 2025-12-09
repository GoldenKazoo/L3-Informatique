import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TwoThreadsPool implements MyExecutorService {

    private final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
    private final Thread fil1;
    private final Thread fil2;

    public TwoThreadsPool() {
        fil1 = startWorker();
        fil2 = startWorker();
    }

    private Thread startWorker() {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Runnable task = queue.take(); // blocage si queue vide
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        t.start();
        return t;
    }

    @Override
    public void execute(Runnable task) {
        try {
            queue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public <T> MyFuture<T> submit(Callable<T> c) {
        FutureImpl<T> future = new FutureImpl<>();
        Task<T> task = new Task<>(c, future);
        execute(task);
        return future;
    }
}
