class Main{
    public static void main(String[]args){
        SharedCounter counter = new SharedCounter();
        Mythreads t1 = new Mythreads(10, counter);
        Mythreads t2 = new Mythreads(20, counter);
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        t2.start();

    }
}

 class SharedCounter {
    private int counter =0;
    boolean libero = true;

     public int getCounter() {
         return counter;
     }
     public boolean isLibero() {
         return libero;
     }

     public void setLibero(boolean libero) {
         this.libero = libero;
     }

     public synchronized void incrementa()
    { counter ++; }
    public synchronized void decrementa()
    { counter--; }
}
class Mythreads extends Thread{
    int n;
    SharedCounter sc;

    public Mythreads(int n, SharedCounter counter) {
        this.n = n;
        this.sc = counter;
    }

    @Override
    public void run() {
        super.run();
        if(!sc.isLibero()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        sc.setLibero(false);
            if(n< sc.getCounter()){
                while(n<sc.getCounter())
                    sc.decrementa();
                System.out.println("sono arrivato a "+sc.getCounter());
            } else if (n> sc.getCounter()) {
                while(n>sc.getCounter())
                    sc.incrementa();
                System.out.println("sono arrivato a "+sc.getCounter());
            } else if (n== sc.getCounter()) {
                System.out.println("sono già a "+sc.getCounter());
            }
            sc.setLibero(true);
            notify();


            }

        }



