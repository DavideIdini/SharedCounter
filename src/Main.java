public class Main {
    public static void main(String[] args) throws InterruptedException {
        SharedCounter sharedCounter = new SharedCounter();
        Thread t1 = new Thread() {
            int n = 10;

            public synchronized void run() {
                if (!sharedCounter.isLibero()) {
                    try {
                        System.out.println("sono in attesa per "+ n);
                        wait();
                        System.out.println("sono stato svegliato");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                    sharedCounter.setLibero(false);
                    if (n == sharedCounter.getCounter()) {
                        System.out.println("sono gia a " + n);
                    }
                    if (n > sharedCounter.getCounter())
                        n = sharedCounter.incr(n);
                    else if (n < sharedCounter.getCounter()) {
                        n = sharedCounter.decr(n);
                    }
                    sharedCounter.setLibero(true);
                    notifyAll();
                }


        };
        Thread t2 = new Thread() {
            int n = 50;

            public synchronized void run() {
                    if (!sharedCounter.isLibero()) {
                            try {
                                System.out.println("sono in attesa per "+ n);
                                wait();
                                System.out.println("sono stato svegliato");
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }



                    sharedCounter.setLibero(false);
                    if (n == sharedCounter.getCounter()) {
                        System.out.println("sono gia a " + n);
                    }
                    if (n > sharedCounter.getCounter())
                        n = sharedCounter.incr(n);
                    else if (n < sharedCounter.getCounter()) {
                        n = sharedCounter.decr(n);
                    }
                    sharedCounter.setLibero(true);
                    notifyAll();
                }


        };
        t1.start();t1.join();
        t2.start();


    }
}

class SharedCounter {
        private static int counter = 0;
        private boolean libero = true;

        public boolean isLibero() {
            return libero;
        }

        public void setLibero(boolean libero) {
            this.libero = libero;
        }

        public synchronized static int getCounter() {
            return counter;
        }

        public synchronized static void setCounter(int counter) {
            SharedCounter.counter = counter;
        }

        public synchronized int incr(int n) {
            System.out.println(" incr counter " + counter + " n " + n);
            while (counter < n)
                counter++;
            System.out.println("sono arrivato a " + counter);
            return n;
        }

        public synchronized int decr(int n) {
            System.out.println("decr counter " + counter + " n " + n);
            while (counter > n) {
                counter--;
            }
            System.out.println("sono arrivato a " + counter);
            return n;
        }


    }



