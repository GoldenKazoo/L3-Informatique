public class App
{
    public static void main(String args[])
    {
        MyThread t1 = new MyThread('a');
        MyThread t2 = new MyThread('b');
        MyThread t3 = new MyThread('c');
        MyThread t4 = new MyThread('d');
        MyThread t5 = new MyThread('e');
        MyThread t6 = new MyThread('f');
        MyThread t7 = new MyThread('g');
        MyThread t8 = new MyThread('h');
        MyThread t9 = new MyThread('i');
        MyThread t10 = new MyThread('j');

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
    }
}