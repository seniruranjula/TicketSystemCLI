import java.util.Scanner;

public class ControlPanel {
    private Configuration configuration;
    private TicketPool ticketPool;

    // Constructor initializes configuration with default values
    public ControlPanel(Configuration configuration, TicketPool ticketPool) {
        this.configuration = configuration;  // Ensure a valid Configuration object
        this.ticketPool = ticketPool;
    }

    public void start() {
        setConfiguration();

        System.out.println("********** Real-Time Event Ticketing System **********");
        System.out.println("1 - Start the System");
        System.out.println("2 - Stop the System");
        System.out.print("Enter option: ");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        if (option == 1) {
            initializeThreads();
        } else if (option == 2) {
            System.out.println("Stopping the system...");
            System.exit(0);
        } else {
            System.out.println("Invalid option.");
            start(); // Restart if the user enters an invalid option
        }
    }
    public void setConfiguration() {
        Scanner scanner = new Scanner(System.in);

        System.out.println();

        System.out.println("********** Configuration Setup **********");


            System.out.println("Enter Total Number of Tickets: ");
            configuration.setTotalTickets(scanner.nextInt());


            System.out.println("Enter Maximum Ticket Capacity: ");
            configuration.setMaxTicketCapacity(scanner.nextInt());



            System.out.println("Enter Ticket Release Rate: ");
            configuration.setTicketReleaseRate(scanner.nextInt());



            System.out.println("Enter Customer Retrieval Rate: ");
            configuration.setCustomerRetrievalRate(scanner.nextInt());


        System.out.println("Configuration set successfully!");
    }

    // Initialize vendor and customer threads
    private void initializeThreads() {
        Thread vendorThread = new Thread(new Vendor(ticketPool));
        vendorThread.start();

        // Start customer threads based on the retrieval rate
        for (int i = 0; i < configuration.getCustomerRetrievalRate(); i++) {
            Thread customerThread = new Thread(new Customer(ticketPool));
            customerThread.start();
        }

        try {
            vendorThread.join(); // Wait for the Vendor thread to complete
            System.out.println("[ControlPanel] Vendor has finished processing tickets.");
            promptForReconfiguration(); // Prompt user for reconfiguration after Vendor is done
        } catch (InterruptedException e) {
            System.out.println("[ControlPanel] System interrupted.");
        }
    }

    public void promptForReconfiguration() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to reconfigure the system? (yes/no)");

        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            configuration.resetConfiguration();
            start();
        } else if (response.equalsIgnoreCase("no")) {
            System.out.println("System shutting down.");
            System.exit(0);
        } else {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            promptForReconfiguration();  // Retry if input is invalid
        }
    }
}
