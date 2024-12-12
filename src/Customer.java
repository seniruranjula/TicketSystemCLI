public class Customer implements Runnable {
    private final TicketPool ticketPool;

    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        String methodDetails = "[Customer] - run";
        while (true) {
            try {
                if (!ticketPool.checkTicketAvailability()) {
                    Logger.info(methodDetails + ": No more tickets available to purchase.");
                    break;
                }
                ticketPool.removeTicket();
                Thread.sleep(100); // Simulates customer purchasing time
            } catch (InterruptedException e) {
                Logger.error(methodDetails + ": Thread interrupted.");
                break;
            }
        }
    }
}
