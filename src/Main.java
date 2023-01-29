public class Main {
    public static void main(String[] args) throws InterruptedException {
        SharedCounter sharedCounter = new SharedCounter();
        Thread t1 = new Thread(){
            int n = 10;
            public void run(){
                n = sharedCounter.waitForValue(n);
            }
        };
        Thread t2 = new Thread(){
            int n = 5;
            public void run(){
                n = sharedCounter.waitForValue(n);
            }
        };
        t1.start();
        t2.start();

    }

}
class SharedCounter{
    private static int counter = 0;
    private boolean libero = true;

    public synchronized static int getCounter() {
        return counter;
    }

    public synchronized static void setCounter(int counter) {
        SharedCounter.counter = counter;
    }
    public synchronized int incr(int n){
        System.out.println(" incr counter "+ counter+" n "+ n);
        while(counter<n)
            counter++;
        System.out.println("sono arrivato a "+ counter);
        return n;
    }
    public synchronized int decr(int n){
        System.out.println("decr counter "+ counter+" n "+ n);
        while(counter>n){
            counter--;
        }
        System.out.println("sono arrivato a "+ counter);
        return n;
    }
    public synchronized int  waitForValue(int n){
        if(libero==false) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        libero = false;
        if(n==counter)
            return n;
        if(n>counter)
            n=incr(n);
        else if (n<counter) {
            n=decr(n);
        }
        libero = true;
        notify();
        return n;
    }
}


