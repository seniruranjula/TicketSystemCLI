public class Configuration {
    private int totalTickets;
    private int maxTicketCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    // Constructor
    public Configuration() {
        this.totalTickets = 0;
        this.maxTicketCapacity = 0;
        this.ticketReleaseRate = 0;
        this.customerRetrievalRate = 0;
    }

    // Getter and Setter methods
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    // Method to reset configuration values
    public void resetConfiguration() {
        this.totalTickets = 0;
        this.maxTicketCapacity = 0;
        this.ticketReleaseRate = 0;
        this.customerRetrievalRate = 0;
    }
}
