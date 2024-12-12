import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private Configuration configuration;
    private final Queue<Integer> ticketList = new LinkedList<>();
    private int releasedTicketCount = 0;
    private boolean isEmptyWarningShown = false;

    public TicketPool(Configuration configuration) {
        this.configuration = configuration;
    }

    // Method to add tickets to the pool
    public synchronized void addTicket() {
        String methodDetails = "[TicketPool] - addTicket";

        while (ticketList.size() >= configuration.getMaxTicketCapacity()) {
            try {
                Logger.warn(methodDetails + ": Ticket pool is full. Waiting for customers to purchase tickets.");
                wait();
            } catch (InterruptedException e) {
                Logger.error(methodDetails + ": Thread interrupted while waiting.");
            }
        }

        int ticketReleaseRate = configuration.getTicketReleaseRate();
        for (int i = 0; i < ticketReleaseRate; i++) {
            if (releasedTicketCount >= configuration.getTotalTickets()) {
                notifyAll();
                return;
            }
            ticketList.add(++releasedTicketCount);
        }

        Logger.info(methodDetails + ": " + ticketReleaseRate + " tickets added. Total in pool: " + ticketList.size());
        isEmptyWarningShown = false; // Reset empty warning flag
        notifyAll(); // Notify consumers
    }

    // Method to remove tickets from the pool
    public synchronized void removeTicket() {
        String methodDetails = "[TicketPool] - removeTicket";
        StringBuilder removalLog = new StringBuilder(); // To accumulate removal messages
        int ticketsToRemove = configuration.getCustomerRetrievalRate();
        int ticketsRemoved = 0;

        for (int i = 0; i < ticketsToRemove; i++) {
            while (ticketList.isEmpty()) {
                if (!isEmptyWarningShown) {
                    Logger.warn(methodDetails + ": Ticket pool is empty. Waiting for tickets to be added.");
                    isEmptyWarningShown = true;
                }
                try {
                    wait();
                } catch (InterruptedException e) {
                    Logger.error(methodDetails + ": Thread interrupted while waiting.");
                }
            }

            int ticket = ticketList.poll(); // Remove a ticket
            ticketsRemoved++;
            removalLog.append("Ticket ").append(ticket).append(" purchased. ");
        }

        if (removalLog.length() > 0) {
            Logger.info(methodDetails + ": " + ticketsRemoved + " tickets removed. ");
        }

        // Log if no tickets are left in the pool
        if (ticketList.isEmpty()) {
            Logger.info(methodDetails + ": No more tickets available in the pool.");
        }

        notifyAll(); // Notify producers
    }

    // Method to check ticket availability
    public synchronized boolean checkTicketAvailability() {
        return releasedTicketCount < configuration.getTotalTickets();
    }

}
