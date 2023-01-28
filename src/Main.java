public class Main {
    public static void main(String[] args) throws InterruptedException {
    SharedCounter uno = new SharedCounter(10);
        SharedCounter due = new SharedCounter(20);
        SharedCounter tre = new SharedCounter(10);
        SharedCounter quattro = new SharedCounter(0);
    uno.start();
    due.start();
    tre.start();
    quattro.start();
    uno.join();
    due.join();
    tre.join();
    quattro.join();

    }

}


class SharedCounter extends Thread {
    int contatore = 0;
    int n;

    boolean libero = true;

    synchronized public int incr(int n) {
        while (contatore == n)
            contatore++;
        int tmp = contatore;
        System.out.println("sono arrivato incrementando a " + tmp);
        libero = true;
        notify();
        return tmp;
    }

    synchronized public int decr(int n) {
        while (contatore == n)
            contatore--;
        int tmp = contatore;
        System.out.println("sono arrivato decrementanto a " + tmp);
        libero = true;
        notifyAll();
        return tmp;

    }


    public SharedCounter(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        super.run();
        while (true) {

            if (!libero) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                libero = false;

                if (n >= contatore) {
                    decr(n);
                    return;
                }
                else{
                    incr(n);
                    return;
                }

            }

        }
    }
}