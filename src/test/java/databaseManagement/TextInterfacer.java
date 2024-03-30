package databaseManagement;

import CRUD.*;
import java.util.Scanner;


public class TextInterfacer {
	
    private Scanner scanner;
    private NavigationStack navStack;
    
    DatabaseManager connection;
	InputDataValidation validate;
	RecordValidation valid;

    public TextInterfacer() {
    	
    	// Initialize interface
         scanner = new Scanner(System.in);
         navStack = new NavigationStack();
         
         // Setup connection to database
         connection = new DatabaseManager();
 		 validate = new InputDataValidation();
 		 valid = new RecordValidation(connection);
 		
 		connection.connectToTestDatabase();
    }

    public void showHomePage() {
    	navStack.navigateTo("Homepage");
        System.out.println("Welcome to the Home Page");
        System.out.println("-------------------------");
        System.out.println("Please Login or l to logout");
        
        System.out.print("Enter agency: ");
        String agency = scanner.next();
        
        // check if need to logout system
        if (agency.equals("l")) {
        	System.out.println("Exiting...");
        	connection.disconnectFromDatabase();
            System.exit(0);
        }
        
        
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        
        while(!valid.checkLoginInfo(username, password, agency)) {
        	System.out.println("Login information incorrect please try again!");
        	
        	System.out.println("Please Login");
            System.out.print("Enter agency: ");
        	agency = scanner.nextLine();
        	
        	 if (agency.equals("l")) {
             	System.out.println("Exiting...");
             	connection.disconnectFromDatabase();
                 System.exit(0);
             }
        	 
            System.out.print("Enter username: ");
            username = scanner.nextLine();
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            
            
        }
        

        switch (agency) {
            case "Admin":
            	navStack.navigateTo(agency);
                showProfilePage();
                break;
            case "Provincial":
            	navStack.navigateTo(agency);
                showSettingsPage();
                break;
            case "Municiple":
            	navStack.navigateTo(agency);
                System.out.println("Exiting...");
                System.exit(0);
                break;
            case "l":
            	navStack.navigateTo(agency);
                System.out.println("Exiting...");
                connection.disconnectFromDatabase();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                showHomePage();
                break;
        }
    }

    public void showProfilePage() {
        System.out.println("Viewing Profile");
        System.out.println("----------------");
        System.out.println("Name: John Doe");
        System.out.println("Age: 30");
        System.out.println("Email: johndoe@example.com");
        System.out.println();
        System.out.println("Press enter to go back to Home Page...");
        scanner.nextLine(); // Wait for user to press enter
        showHomePage();
    }

    public void showSettingsPage() {
        System.out.println("Settings");
        System.out.println("--------");
        System.out.println("1. Change Password");
        System.out.println("2. Change Email");
        System.out.println("3. Go Back");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                changePassword();
                break;
            case 2:
                changeEmail();
                break;
            case 3:
                showHomePage();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                showSettingsPage();
                break;
        }
    }

    public void changePassword() {
        System.out.println("Change Password");
        // Logic to change password goes here
        showSettingsPage();
    }

    public void changeEmail() {
        System.out.println("Change Email");
        // Logic to change email goes here
        showSettingsPage();
    }

    public static void main(String[] args) {
    	TextInterfacer textInterface = new TextInterfacer();
        textInterface.showHomePage();
        
    }
}
