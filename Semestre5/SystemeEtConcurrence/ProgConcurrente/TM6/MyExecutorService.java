import java.util.concurrent.Executor;

public interface MyExecutorService extends Executor {
    <T> MyFuture<T> submit(Callable<T> task);
}
