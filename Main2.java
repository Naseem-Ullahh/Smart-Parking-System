package SmartParkingSystem;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main2 
{
    static final PrintStream out = System.out;
    static final Scanner input = new Scanner(System.in);

    public static void clearScreen()
    {
        try 
        {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } 
        catch (Exception e) 
        {
            // Ignore
        }
    }

    public static void promptEnterKey()
    {
        System.out.println("\nPress Enter to continue...");
        input.nextLine();
    }

    private static String getNonEmptyString(String prompt)
    {
        String inputStr;
        while (true)
        {
            out.print(prompt);
            inputStr = input.nextLine().trim();
            if (inputStr.equalsIgnoreCase("back"))
            {
                return "back";
            }
            if (!inputStr.isEmpty())
            {
                return inputStr;
            }
            else
            {
                out.println("Input cannot be empty. Please enter a valid value or type 'back' to return.");
            }
        }
    }

    public static String getValidatedName(String prompt)
    {
        String name;
        while (true)
        {
            name = getNonEmptyString(prompt);
            if (name.equalsIgnoreCase("back"))
            {
                return "back";
            }
            if (name.matches("[a-zA-Z\\s]+"))
            {
                return name;
            }
            else
            {
                out.println("Invalid name. Only alphabets and spaces are allowed.");
            }
        }
    }

    public static void adminPortal()
    {
        Admin admin = Admin.getInstance(); 
        while (true)
        {
            clearScreen();
            System.out.println("=============================ADMIN PORTAL=============================");
            System.out.println("1. Sign In\n2. Back to Main Menu");

            int choice2 = 0;

            while (true)
            {
                System.out.print("Enter your choice : ");
                if (input.hasNextInt())
                {
                    choice2 = input.nextInt();
                    input.nextLine();
                    if (choice2 == 1 || choice2 == 2)
                    {
                        break;
                    }
                }
                else
                {
                    input.next(); 
                }
                System.out.println("Invalid input. Please enter 1 or 2.");
            }

            switch(choice2)
            {
                case 1:
                {
                    System.out.println("=============================SIGN IN PORTAL=============================");
                    while (true)
                    {
                        System.out.println("Type 'back' to return to the previous menu.");
                        String loginUsername = getNonEmptyString("Enter username : ");
                        if (loginUsername.equalsIgnoreCase("back")) break;

                        String loginPassword = getNonEmptyString("Enter password : ");
                        if (loginPassword.equalsIgnoreCase("back")) break;
                        if (admin.getUsername().equals(loginUsername) && admin.getPassword().equals(loginPassword))
                        {
                            clearScreen();
                            System.out.println("Logged in successfully!");
                            System.out.println("Welcome " + admin.getName() + " !");
                            adminDashboard(admin);
                            break;
                        }
                        else 
                        {
                            System.out.println("Incorrect username or password. Try again.");
                        }
                    }
                    break;
                }
                case 2:
                    return;
            }
        }
    }

    public static void adminDashboard(Admin admin)
    {
        while (true)
        {
            clearScreen();
            System.out.println("======================================ADMIN PORTAL DASHBOARD======================================");
            System.out.println("1. Add Parking Site\n2. Remove Parking Site\n3. View Parking Site Status\n4. Search Vehicle by License Plate\n5. View Regular Users\n6. View Sales Reports\n7. Change username password\n8. Logout");

            int choice3 = 0;

            while (true)
            {
                System.out.print("Enter your choice : ");
                if (input.hasNextInt())
                {
                    choice3 = input.nextInt();
                    input.nextLine();
                    if (choice3 >= 1 && choice3 <= 8)
                    {
                        break;
                    }
                }
                else
                {
                    input.next();
                }
                System.out.println("Invalid input. Please enter a number between 1 and 8.");
            }

            String siteId, location;
            int maxSiteCapacity;
            double hourlyRate;
            boolean isOperational;

            switch(choice3)
            {
                case 1:
                {
                    clearScreen();

                    siteId = getNonEmptyString("Enter site ID : ");
                    location = getNonEmptyString("Enter Location of the site : ");

                    System.out.print("Enter maximum capacity of vehicles : ");
                    while (true)
                    {
                        if (input.hasNextInt())
                        {
                            maxSiteCapacity = input.nextInt();
                            input.nextLine();
                            if (maxSiteCapacity >= 0)
                            {
                                break;
                            }
                        }
                        else
                        {
                            input.next();
                        }
                        System.out.print("Invalid. Capacity cannot be negative. Please enter again : ");
                    }

                    System.out.print("Enter Hourly Parking Rate : ");
                    while (true)
                    {
                        if (input.hasNextDouble())
                        {
                            hourlyRate = input.nextDouble();
                            input.nextLine();
                            if (hourlyRate >= 0)
                            {
                                break;
                            }
                        }
                        else
                        {
                            input.next();
                        }
                        System.out.print("Invalid. Hourly rate cannot be negative. Please enter again : ");
                    }

                    System.out.print("Set site operational? (true/false) : ");
                    while (!input.hasNextBoolean())
                    {
                        input.next();
                        System.out.print("Invalid input. Enter true or false : ");
                    }
                    isOperational = input.nextBoolean();
                    input.nextLine();

                    admin.addSite(siteId, maxSiteCapacity, location, hourlyRate, isOperational);
                    System.out.println("Site added successfully!");
                    promptEnterKey();
                    break;
                }
                case 2:
                {
                    clearScreen();
                    if(admin.hasNoSites())
                    {
                        System.out.println("No sites added! Please add a site first!");
                        promptEnterKey();
                        break;
                    }
                    System.out.println("Existing sites are : ");
                    admin.displayAllSites();

                    boolean removed = false;

                    while (!removed)
                    {
                        siteId = getNonEmptyString("Enter site ID to remove it : ");
                        removed = admin.removeParkingSite(siteId);
                        if (!removed)
                        {
                            System.out.println("No site found with this ID. Try again.");
                        }
                        else
                        {
                            System.out.println("Site removed successfully.");
                        }
                    }
                    promptEnterKey();
                    break;
                }
                case 3:
                {
                    clearScreen();
                    if(admin.hasNoSites()) 
                    {
                        System.out.println("No sites entered! Please create a site first!");
                        promptEnterKey();
                        break;
                    }
                    System.out.println("All managed sites are : ");
                    admin.displayAllSites();

                    siteId = getNonEmptyString("Enter the site ID for which you want to check status : ");
                    while (admin.findSiteIndex(siteId) == -1) 
                    {
                        siteId = getNonEmptyString("No site found with this ID. Enter a valid site ID : ");
                    }

                    admin.returnSiteById(siteId).showSiteStatus();
                    promptEnterKey();
                    break;
                }
                case 4:
                {
                    clearScreen();
                    String licenseInput = getNonEmptyString("Enter license plate to check for vehicle record : ");
                    admin.searchVehiclebyLicensePlate(licenseInput);
                    promptEnterKey();
                    break;
                }
                case 5:
                {
                    clearScreen();
                    if(!(RegularUser.cnicUserMap == null) && !(RegularUser.cnicUserMap.isEmpty()))
                    {
                        for (RegularUser user : RegularUser.cnicUserMap.values()) 
                        {
                            user.showDetails();
                        }
                    } 
                    else
                    {
                        System.out.println("No users registered till now!");
                    }
                    promptEnterKey();
                    break;
                }
                case 6:
                {
                    clearScreen();
                    siteId = getNonEmptyString("Enter the site for which you want to have sales report : ");
                    if(admin.findSiteIndex(siteId) != -1)
                    {
                        SalesReport.getRegularUsersSalesReport(siteId);
                        SalesReport.getWalkinUserSalesReport(siteId);
                        SalesReport.getTotalSales(siteId); 
                    }
                    else
                    {
                        System.out.println("Invalid site ID.");
                    }
                    promptEnterKey();
                    break;
                }
                case 7:
               {
                   String userName, password;
                   System.out.print("Enter old username : ");
                   userName = input.nextLine();
                   System.out.print("Enter old password : ");
                   password = input.nextLine();

                   if (admin.getUsername().equals(userName) && admin.getPassword().equals(password))
                   {
                       clearScreen();
                       System.out.print("Enter new username : ");
                       String newUsername = input.nextLine();
                       System.out.print("Enter new password : ");
                       String newPassword = input.nextLine();

                       admin.setUsername(newUsername);
                       admin.setPassword(newPassword);

                       System.out.println("Username & Password updated successfully!");
                   }
                   else
                   {
                       System.out.println("Incorrect username or password. Try again!");
                   }

                   promptEnterKey();
                   break;
               }

                case 8:
                {
                    System.out.println("Logging out...");
                    promptEnterKey();
                    return;
                }
            }
        }
    }

    public static void userPortal()
    {
        while (true)
        {
            clearScreen();
            System.out.println("\n=============================USER PORTAL=============================");
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Back to Main Menu");
            System.out.println("===================================================================");

            int choice2 = 0;
            while (true)
            {
                System.out.print("\nEnter your choice : ");
                if (input.hasNextInt())
                {
                    choice2 = input.nextInt();
                    input.nextLine();
                    if (choice2 >= 1 && choice2 <= 3)
                    {
                        break;
                    }
                }
                else
                {
                    input.next();
                }
                System.out.println("Invalid input. Please enter 1, 2, or 3.");
            }

            switch (choice2)
            {
                case 1:
                {
                    clearScreen();
                    System.out.println("\n=============================SIGN UP PORTAL=============================");
                    System.out.println("Type 'back' at any time to return to the previous menu.");
                    System.out.println("===================================================================");

                    String name = getValidatedName("\nEnter Name : ");
                    if (name.equalsIgnoreCase("back")) continue;

                    String CNIC;
                    while (true)
                    {
                        System.out.print("\nEnter CNIC (13 digits): ");
                        CNIC = input.nextLine().trim();
                        
                        if (CNIC.equalsIgnoreCase("back")) 
                        {
                            CNIC = "back";
                            break;
                        }

                        if (RegularUser.isValidCNIC(CNIC))
                        {
                            if (RegularUser.cnicUserMap != null && RegularUser.cnicUserMap.containsKey(CNIC))
                            {
                                System.out.println("A user with this CNIC is already registered. Please use a different CNIC or sign in.");
                                continue;
                            }
                            break;
                        }
                        else
                        {
                            System.out.println("Invalid CNIC format! Please try again.");
                        }
                    }
                    if (CNIC.equalsIgnoreCase("back")) continue;

                    String contactNo;
                    while (true)
                    {
                        System.out.print("\nEnter contact no (e.g., 03XXXXXXXXX): ");
                        contactNo = input.nextLine().trim();
                        
                        if (contactNo.equalsIgnoreCase("back"))
                        {
                            contactNo = "back";
                            break;
                        }

                        if (RegularUser.isValidPakistaniNumber(contactNo))
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Invalid contact number format! Please try again.");
                        }
                    }
                    if (contactNo.equalsIgnoreCase("back")) continue;

                    String vehicleType = getNonEmptyString("\nEnter vehicle type : ");
                    if (vehicleType.equalsIgnoreCase("back")) continue;

                    String vehicleNo = getNonEmptyString("\nEnter license plate number : ");
                    if (vehicleNo.equalsIgnoreCase("back")) continue;

                    String password = getNonEmptyString("\nEnter password : ");
                    if (password.equalsIgnoreCase("back")) continue;

                    RegularUser newUser = new RegularUser(name, contactNo, vehicleType, vehicleNo, CNIC, password);

                    clearScreen();
                    System.out.println("\nUser registered successfully! Please log in to continue.");
                    System.out.println("===================================================================");

                    // Auto-login
                    while (true)
                    {
                        String loginCNIC = getNonEmptyString("\nEnter CNIC : ");
                        if (loginCNIC.equalsIgnoreCase("back")) break;

                        String loginPassword = getNonEmptyString("Enter password : ");
                        if (loginPassword.equalsIgnoreCase("back")) break;

                        if (newUser.validateLogin(loginCNIC, loginPassword))
                        {
                            clearScreen();
                            System.out.println("\nLogged in successfully!");
                            System.out.println("Welcome " + newUser.getName() + " !");
                            promptEnterKey();
                            userDashbord(newUser);
                            break;
                        }
                        else
                        {
                            System.out.println("Incorrect CNIC or password. Please try again.");
                        }
                    }
                    break;
                }

                case 2:
                {
                    clearScreen();
                    if (RegularUser.cnicUserMap == null || RegularUser.cnicUserMap.isEmpty())
                    {
                        System.out.println("\nNo user registered yet. Please sign up first.");
                        promptEnterKey();
                        continue;
                    }

                    System.out.println("\n=============================SIGN IN PORTAL=============================");
                    System.out.println("Type 'back' at any time to return to the previous menu.");
                    System.out.println("===================================================================");

                    while (true)
                    {
                        String CNIC = getNonEmptyString("\nEnter CNIC : ");
                        if (CNIC.equalsIgnoreCase("back")) break;

                        String password = getNonEmptyString("Enter password : ");
                        if (password.equalsIgnoreCase("back")) break;

                        RegularUser user = RegularUser.cnicUserMap.get(CNIC);

                        if (user != null && user.validateLogin(CNIC, password))
                        {
                            clearScreen();
                            System.out.println("\nLogged in successfully!");
                            System.out.println("Welcome " + user.getName() + " !");
                            promptEnterKey();
                            userDashbord(user);
                            break;
                        }
                        else
                        {
                            System.out.println("Incorrect CNIC or password. Try again.");
                        }
                    }
                    break;
                }

                case 3:
                    return;
            }
        }
    }

    public static void userDashbord(RegularUser regularUser)
    {
        while (true)
        {
            clearScreen();
            System.out.println("============================ REGULAR USER PORTAL DASHBOARD ============================");
            System.out.println("1. Book Slot");
            System.out.println("2. Checkout from Parking");
            System.out.println("3. Display Parking Records");
            System.out.println("4. View Due Bill");
            System.out.println("5. Pay Bill");
            System.out.println("6. View Wallet");
            System.out.println("7. Wallet Top Up");
            System.out.println("8. Change Password");
            System.out.println("9. Logout");

            int choice = 0;

            while (true)
            {
                System.out.print("Enter your choice: ");
                if (input.hasNextInt())
                {
                    choice = input.nextInt();
                    input.nextLine();
                    if (choice >= 1 && choice <= 9)
                    {
                        break;
                    }
                }
                else
                {
                    input.next(); // clear invalid input
                }
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
            }

            switch (choice)
            {
                case 1: // Book Slot
                {
                    clearScreen();

                    if (regularUser.bookedSlot != -1)
                    {
                        System.out.println("You have already booked a slot (Slot #" + regularUser.bookedSlot + ").");
                        System.out.println("You must free it before booking a new one.");
                        promptEnterKey();
                        break;
                    }

                    if (Admin.getInstance() == null || Admin.getInstance().hasNoSites())
                    {
                        System.out.println("\nNo parking sites have been added by admin yet.");
                        System.out.println("Please try again later or contact the admin.");
                        promptEnterKey();
                        break;
                    }

                    String siteId = getNonEmptyString("Enter the site ID where you want to park the vehicle: ");
                    String vehicleNo = regularUser.getVehiclNo();
                    ParkingSite site = Admin.getInstance().returnSiteById(siteId);

                    if (site != null)
                    {
                        System.out.println("Available Slots at this site:");
                        site.getAvailableSlots();
                        int slotChoice = -1;
                        while (true) {
                            System.out.print("Enter the slot number you want to book: ");
                            if (input.hasNextInt()) {
                                slotChoice = input.nextInt();
                                input.nextLine();
                                if (slotChoice >= 1 && slotChoice <= site.getMaxSiteCapacity() && site.isSlotAvailable(slotChoice - 1)) {
                                    break;
                                }
                            } else {
                                input.next();
                            }
                            System.out.println("Invalid or unavailable slot. Please choose an available slot.");
                        }
                        int slotBooked = site.bookSlotAt(vehicleNo, siteId, slotChoice - 1);
                        if (slotBooked > 0) {
                            regularUser.bookedSlot = slotBooked;
                            System.out.println("Slot booked successfully! Your slot number is: " + slotBooked);
                        } else if (slotBooked == -1) {
                            System.out.println("Site is not currently operational.");
                        } else {
                            System.out.println("No slots available at the moment. Please try another site.");
                        }
                    }
                    else
                    {
                        System.out.println("Invalid site ID.");
                    }

                    promptEnterKey();
                    break;
                }

                case 2: // Checkout from Parking
                {
                    clearScreen();
                    if (regularUser.getCurrentParking() == null)
                    {
                        System.out.println("You do not have any active parking session.");
                    }
                    else
                    {
                        String licensePlate = regularUser.getVehiclNo();
                        ParkingSite site = Admin.getInstance().returnSiteById(regularUser.getCurrentParking().getsiteId());
                        if (site != null)
                        {
                            boolean released = site.releaseSlot(licensePlate);
                            if(released)
                            {
                                regularUser.bookedSlot = -1;
                                System.out.println("You have successfully checked out and your slot has been released.");
                            }
                            else
                            {
                                System.out.println("Error releasing the slot!"); 
                            }
                        }
                        else
                        {
                            System.out.println("Error: Site not found. Could not release slot.");
                        }
                    }
                    promptEnterKey();
                    break;
                }

                case 3: // Display Parking Records
                {
                    clearScreen();
                    regularUser.showParkingHistory();
                    promptEnterKey();
                    break;
                }

                case 4: // View Due Bill
                {
                    clearScreen();
                    double due = 0;
                    for (ParkingRecord record : regularUser.getParkingHistory())
                    {
                        if (!record.isPaid())
                        {
                            due += record.getSessionBill();
                        }
                    }
                    System.out.println("Total unpaid bill: Rs " + due);
                    promptEnterKey();
                    break;
                }

                case 5: // Pay Bill
                {
                    clearScreen();
                    regularUser.payMonthlyBill();
                    promptEnterKey();
                    break;
                }

                case 6: // View Wallet
                {
                    clearScreen();
                    System.out.println("Your current wallet balance is: Rs " + regularUser.getWalletBalance());
                    promptEnterKey();
                    break;
                }

                case 7: // Wallet Top Up
                {
                    clearScreen();
                    System.out.print("Enter amount to deposit: ");
                    if (input.hasNextDouble())
                    {
                        double amount = input.nextDouble();
                        input.nextLine();
                        regularUser.deposit(amount);
                    }
                    else
                    {
                        input.nextLine(); // consume invalid
                        System.out.println("Invalid amount entered.");
                    }
                    promptEnterKey();
                    break;
                }

                case 8: // Change Password
                {
                    clearScreen();
                    String oldPassword = getNonEmptyString("Enter current password: ");
                    if (!oldPassword.equals(regularUser.getPassword()))
                    {
                        System.out.println("Incorrect current password.");
                    }
                    else
                    {
                        String newPassword = getNonEmptyString("Enter new password: ");
                        regularUser.setPassword(newPassword); 
                        System.out.println("Password updated successfully.");
                    }
                    promptEnterKey();
                    break;
                }

                case 9: // Logout
                {
                    System.out.println("Logging out...");
                    promptEnterKey();
                    return; // exit dashboard and return to user portal menu
                }
            }
        }
    }

    public static void walkInPortal()
    {
        while (true)
        {
            clearScreen();
            System.out.println("\n=============================WALK-IN CUSTOMER PORTAL=============================");
            System.out.println("1. Check-In");
            System.out.println("2. Check-Out");
            System.out.println("3. Back to Main Menu");
            System.out.println("===================================================================");

            int choice = 0;
            while (true)
            {
                System.out.print("\nEnter your choice : ");
                if (input.hasNextInt())
                {
                    choice = input.nextInt();
                    input.nextLine();
                    if (choice >= 1 && choice <= 3)
                    {
                        break;
                    }
                }
                else
                {
                    input.next();
                }
                System.out.println("Invalid input. Please enter 1, 2, or 3.");
            }

            switch (choice)
            {
                case 1: // Check-In
                {
                    clearScreen();
                    System.out.println("\n=============================CHECK-IN PORTAL=============================");
                    System.out.println("Type 'back' at any time to return to the previous menu.");
                    System.out.println("===================================================================");

                    // Check if admin has added any sites
                    if (Admin.getInstance() == null || Admin.getInstance().hasNoSites())
                    {
                        System.out.println("\nNo parking sites have been added by admin yet.");
                        System.out.println("Please try again later or contact the admin.");
                        promptEnterKey();
                        break;
                    }

                    // Get user details
                    String name = getValidatedName("\nEnter Name : ");
                    if (name.equalsIgnoreCase("back")) continue;

                    String contactNo;
                    while (true)
                    {
                        System.out.print("\nEnter contact no (e.g., 03XXXXXXXXX): ");
                        contactNo = input.nextLine().trim();
                        
                        if (contactNo.equalsIgnoreCase("back"))
                        {
                            contactNo = "back";
                            break;
                        }

                        if (RegularUser.isValidPakistaniNumber(contactNo))
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Invalid contact number format! Please try again.");
                        }
                    }
                    if (contactNo.equalsIgnoreCase("back")) continue;

                    String vehicleType = getNonEmptyString("\nEnter vehicle type : ");
                    if (vehicleType.equalsIgnoreCase("back")) continue;

                    String vehicleNo = getNonEmptyString("\nEnter license plate number : ");
                    if (vehicleNo.equalsIgnoreCase("back")) continue;

                    // Check if vehicle is already parked
                    if (User.getUserByVehicleNo(vehicleNo) != null)
                    {
                        System.out.println("\nThis vehicle is already parked in the system!");
                        promptEnterKey();
                        continue;
                    }
                    
                    // Show available sites
                    System.out.println("\nAvailable Parking Sites:");
                    System.out.println("===================================================================");
                    Admin.getInstance().displayAllSites();
                    
                    String siteId = getNonEmptyString("\nEnter site ID where you want to park : ");
                    ParkingSite site = Admin.getInstance().returnSiteById(siteId);
                    if (siteId.equalsIgnoreCase("back")) continue;

                    if (site == null)
                    {
                        System.out.println("Invalid site ID!");
                        promptEnterKey();
                        continue;
                    }

                    // Show site details and ask for confirmation
                    System.out.println("\nParking Site Details:");
                    System.out.println("===================================================================");
                    System.out.println("Site ID: " + site.getSiteID());
                    System.out.println("Location: " + site.getSiteLocation());
                    System.out.println("Hourly Rate: Rs " + site.getHourlyRate());
                    System.out.println("===================================================================");

                    
                    // Show available slots
                    System.out.println("Available Slots at this site:");
                    site.getAvailableSlots();
                    int slotChoice = -1;
                    while (true) {
                        System.out.print("Enter the slot number you want to book: ");
                        if (input.hasNextInt()) {
                            slotChoice = input.nextInt();
                            input.nextLine();
                            if (slotChoice >= 1 && slotChoice <= site.getMaxSiteCapacity() && site.isSlotAvailable(slotChoice - 1)) {
                                break;
                            }
                        } else {
                            input.next();
                            String confirm = getNonEmptyString("\nDo you want to proceed with check-in? (yes/no): ");
                            if (!confirm.equalsIgnoreCase("yes"))
                            {
                                System.out.println("Check-in cancelled.");
                                promptEnterKey();
                                continue;
                            }
                        }
                        System.out.println("Invalid or unavailable slot. Please choose an available slot.");
                    }
                    WalkInUser walkInUser = new WalkInUser(name, contactNo, vehicleNo, vehicleType, siteId);
                    int slotBooked = site.bookSlotAt(vehicleNo, siteId, slotChoice - 1);

                    if (slotBooked > 0) {
                        walkInUser.bookedSlot = slotBooked;
                        System.out.println("Slot booked successfully! Your slot number is: " + slotBooked);
                    } else if (slotBooked == -1) {
                        System.out.println("Site is not currently operational.");
                    } else {
                        System.out.println("No slots available at the moment. Please try another site.");
                    }
                    promptEnterKey();
                    break;
                }

                case 2: // Check-Out
                {
                    clearScreen();
                    System.out.println("\n=============================CHECK-OUT PORTAL=============================");
                    System.out.println("Type 'back' at any time to return to the previous menu.");
                    System.out.println("===================================================================");

                    String vehicleNo = getNonEmptyString("\nEnter license plate number : ");
                    if (vehicleNo.equalsIgnoreCase("back")) continue;

                    User user = User.getUserByVehicleNo(vehicleNo);
                    if (user == null || !(user instanceof WalkInUser))
                    {
                        System.out.println("No active parking session found for this vehicle!");
                        promptEnterKey();
                        continue;
                    }

                    WalkInUser walkInUser = (WalkInUser) user;
                    if (!walkInUser.isParked)
                    {
                        System.out.println("This vehicle is not currently parked!");
                        promptEnterKey();
                        continue;
                    }

                    // Set check-out time without ending parking session
                    walkInUser.currentParking.setParkOut(LocalDateTime.now());
                    walkInUser.billingCost();

                    // Show bill and payment options
                    clearScreen();
                    System.out.println("\n=============================CHECK-OUT INVOICE=============================");
                    System.out.println("Customer Details:");
                    System.out.println("Name: " + walkInUser.getName());
                    System.out.println("Contact: " + walkInUser.getContactNo());
                    System.out.println("Vehicle Type: " + walkInUser.getVehicleType());
                    System.out.println("License Plate: " + walkInUser.getVehiclNo());
                    System.out.println("\nParking Details:");
                    System.out.println("Site ID: " + walkInUser.siteId);
                    System.out.println("Slot Number: " + walkInUser.bookedSlot);
                    System.out.println("Check-in Time: " + walkInUser.currentParking.getParkIn());
                    System.out.println("Check-out Time: " + walkInUser.currentParking.getParkOut());
                    System.out.println("Duration: " + walkInUser.currentParking.getDurationInMinutes() + " minutes");
                    System.out.println("Total Bill: Rs " + walkInUser.bill);
                    System.out.println("===================================================================");

                    System.out.println("\nSelect Payment Method:");
                    System.out.println("1. Cash");
                    System.out.println("2. Card");

                    int paymentChoice = 0;
                    while (true)
                    {
                        System.out.print("\nEnter your choice : ");
                        if (input.hasNextInt())
                        {
                            paymentChoice = input.nextInt();
                            input.nextLine();
                            if (paymentChoice == 1 || paymentChoice == 2)
                            {
                                break;
                            }
                        }
                        else
                        {
                            input.next();
                        }
                        System.out.println("Invalid input. Please enter 1 or 2.");
                    }

                    // Process payment
                    String paymentMethod = paymentChoice == 1 ? "Cash" : "Card";
                    walkInUser.currentParking.markAsPaid(paymentMethod);
                    System.out.println("\nPayment successful! Thank you for using our service.");

                    // Now end the parking session and release the slot
                    walkInUser.parkOut();
                    ParkingSite site = Admin.getInstance().returnSiteById(walkInUser.siteId);
                    if (site != null) {
                        site.releaseSlot(vehicleNo); 
                    }

                    promptEnterKey(); 
                    break;
                }

                case 3:
                    return;
            }
        }
    }

    public static void main(String[] args) 
    {
        clearScreen();
 
        while (true)
        {
            System.out.println("=============================SMART PARKING MANAGEMENT SYSTEM=============================");
            System.out.println("1. Enter Admin Portal\n2. Enter User Portal\n3. Walk-In Customer\n4. Exit");
 
            int choice1 = 0;

            while (true)
            {
                System.out.print("Enter your choice : ");
                if (input.hasNextInt())
                {
                    choice1 = input.nextInt();
                    input.nextLine(); // consume newline
                    if (choice1 >= 1 && choice1 <= 4)
                    {
                        break;
                    }
                }
                else
                {
                    input.next(); 
                }
                System.out.println("Invalid input. Please enter 1, 2, 3 or 4.");
            }

            switch(choice1)
            {
                case 1:
                    adminPortal();
                    break;
                case 2:
                    userPortal(); 
                    promptEnterKey();
                    break;
                case 3:
                    walkInPortal();
                    break;
                case 4:
                    System.out.println("Exiting system. Goodbye!");
                    return;
            }
            clearScreen();
        }
    }
}