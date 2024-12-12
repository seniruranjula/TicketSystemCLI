public class Vendor implements Runnable {
    private final TicketPool ticketPool;

    public Vendor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        String methodDetails = "[Vendor] - run";
        while (ticketPool.checkTicketAvailability()) {
            try {
                ticketPool.addTicket();
                Thread.sleep(2000); // Simulates delay in ticket release
            } catch (InterruptedException e) {
                Logger.error(methodDetails + ": Vendor thread interrupted.");
                break;
            }
        }
        Logger.info(methodDetails + ": No more tickets to add. Vendor stopping.");
    }
}
