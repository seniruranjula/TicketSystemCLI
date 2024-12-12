import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AdminLogin adminLogin = new AdminLogin();

        System.out.println("********** Admin Login **********");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (adminLogin.authenticate(username, password)) {
            System.out.println("Login successful! Welcome to the Real-Time Event Ticketing System.");

            Configuration configuration = new Configuration();
            TicketPool ticketPool = new TicketPool(configuration);


            ControlPanel controlPanel = new ControlPanel(configuration, ticketPool);
            
            controlPanel.start();
        } else {
            System.out.println("Invalid credentials. Access denied.");
        }

        scanner.close();
    }
}
