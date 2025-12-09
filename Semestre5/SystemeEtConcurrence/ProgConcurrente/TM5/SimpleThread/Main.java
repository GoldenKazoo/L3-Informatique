package SimpleThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

import Blocking_queu_Home_made.Blocking_queu;

public class Main {
    public static void main(String[] args) {
        SimpleThread st = new SimpleThread();

        for (int i = 0 ; i < 100 ; i++) {
            int id = i;
            st.execute(() -> {
                for (int k = 0 ; k < 5 ; k++) {
                    System.out.println("Tâche " + id + " → " + k);
                    try { Thread.sleep(300); } catch (InterruptedException e) {}
                }
            });
        }
    }
}

