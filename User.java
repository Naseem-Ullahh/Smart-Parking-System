package SmartParkingSystem; 

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class User
{ 
    protected String name;
    protected String contactNo;
    protected String vehicleType;
    protected String vehicleNo;
    protected ArrayList<ParkingRecord> parkingHistory = new ArrayList<>();
    protected ParkingRecord currentParking;
    protected boolean isParked = false;
    protected int bookedSlot ;
    protected String userType ;

    protected static HashMap<String,User> userMap = new HashMap<>();

    User(String name, String contactNo, String vehicleType, String vehicleNo){
       this.contactNo = contactNo;
        this.name = name;
        this.contactNo = contactNo;
        this.vehicleType = vehicleType;
        this.vehicleNo = vehicleNo;
        userMap.put(vehicleNo,this);
        this.bookedSlot = -1;
    }

    protected static boolean isValidPakistaniNumber(String number){
        return number != null && number.matches("^03\\d{9}$");
    }
    void updateName(String name){
        this.name = name;
    }
    void updateContactNo(String contactNo){
        if(!isValidPakistaniNumber(contactNo)){
            throw new IllegalArgumentException("Invalid Pakistani Contact Number: " + contactNo);
        }
        this.contactNo = contactNo;
    }
    public String getName(){
        return name;
    }
    public String getContactNo(){
        return contactNo;
    }
    public String getVehicleType(){
        return vehicleType;
    }

    public static User getUserByVehicleNo(String vehicleNo) {
        return userMap.get(vehicleNo); 
    }

    public String getVehiclNo()
    {
        return vehicleNo;
    }

    void startParking(String siteId) 
    
    {
        if (currentParking == null) 
        {
            isParked = true;
            currentParking = new ParkingRecord(LocalDateTime.now() , siteId);
        } 
        else 
        {
            System.out.println("You are already parked in.");
        }
    }

    void parkOut() {
        if (currentParking != null) {
            isParked = false;
            currentParking.setParkOut(LocalDateTime.now());
            parkingHistory.add(currentParking);
            currentParking = null;
            System.out.println("Park out recorded. Session added to monthly bill.");
        } else {
            System.out.println("No active parking session to end.");
        }
    }

    public ParkingRecord getCurrentParking() {
        return currentParking;
    }

    public ArrayList<ParkingRecord> getParkingHistory() {
        return parkingHistory;
    }

    public void showParkingHistory() {
        if (parkingHistory.isEmpty()) {
            System.out.println("No parking history available.");
            return;
        }
        System.out.println("\nParking History:");
        System.out.println("===================================================================");
        for (ParkingRecord record : parkingHistory) {
            System.out.println(record.toString());
        }
        System.out.println("===================================================================");
    }

    abstract void showDetails();

    
}  

class RegularUser extends User{
    private int userID;
    private String CNIC;
    private static int idcounter = 1;
    private double walletBalance = 0;
    private String password;
    private double lastMonthBill;
    
    public static HashMap<String, RegularUser> cnicUserMap = new HashMap<>();

    RegularUser(String name, String contactNo, String vehicleType, String vehicleNo, String CNIC, String password){
        super(name,contactNo,vehicleType,vehicleNo);
        this.userID = idcounter++;
        this.CNIC = CNIC;
        cnicUserMap.put(CNIC, this);
        this.password = password;
    }

    

    boolean validateLogin(String CNIC, String password){
        RegularUser user = cnicUserMap.get(CNIC);
        return user != null && user.password.equals(password);
    }
 

    void updateCNIC(String CNIC){
        this.CNIC = CNIC;
    }
    void updateVehicleNo(String vehicleNo){
        this.vehicleNo = vehicleNo;
    }
    int getUserID(){
        return userID;
    }
    String getCNIC(){
        return CNIC;
    }
    String getVehicleNo(){
        return vehicleNo;
    }
    double getLastMonthBill(){
        return lastMonthBill;
    }
    void showDetails(){
        System.out.println("User ID : " + userID);
        System.out.println("Name : "+name);
        System.out.println("Contact Number : " + contactNo);     
        System.out.println("CNIC : " + CNIC);
        System.out.println("Vehicle Type : " + vehicleType);
        System.out.println("Vehicle Number : "+ vehicleNo);    
    }
    protected static boolean isValidCNIC(String CNIC){
        return CNIC != null && CNIC.matches("^\\d{13}$");
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit must be positive.");
            return;
        }
        walletBalance += amount;
        System.out.println("Rs " + amount + " deposited. New wallet balance: Rs " + walletBalance);
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > walletBalance) {
            System.out.println("Insufficient wallet balance.");
            return false;
        }
        walletBalance -= amount;
        System.out.println("Rs " + amount + " withdrawn. Remaining wallet balance: Rs " + walletBalance);
        return true;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void payMonthlyBill() {
        double totalDue = 0;
        ArrayList<ParkingRecord> unpaidSessions = new ArrayList<>();
    
        for (ParkingRecord record : parkingHistory) {
            if (!record.isPaid()) {
                totalDue += record.getSessionBill();
                unpaidSessions.add(record);
            }
        }
    
        if (totalDue == 0) {
            System.out.println("All dues are already paid.");
            return;
        }

        lastMonthBill = totalDue;
    
        System.out.println("Total due for this month: Rs " + totalDue);
        System.out.println("Choose payment method:\n1. Cash\n2. App Pay");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
    
        boolean paid = false;
    
        switch (choice) {
            case 1:
                paid = true;
                break;
            case 2:
                if (withdraw(totalDue)) {
                    paid = true;
                } else {
                    System.out.println("Insufficient balance in App Wallet.");
                }
                break;
            default:
                System.out.println("Invalid payment method.");
        }
    
        if (paid) {
            for (ParkingRecord record : unpaidSessions) {
                record.markAsPaid(choice == 1 ? "Cash" : "App Pay");
            }
            System.out.println("Payment successful. All dues cleared.");
        } else {
            System.out.println("Monthly payment failed.");
        }
 
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password; 
    }
}


class WalkInUser extends User
{
    protected String siteId;
    protected double bill;

    protected static HashMap<String,WalkInUser> WalkInUserMap = new HashMap<>();
    
    WalkInUser(String name, String contactNo, String vehicleNo, String vehicleType, String siteId){
        super(name, contactNo, vehicleType,vehicleNo); 
        WalkInUserMap.put(vehicleNo,this);
        this.siteId = siteId;
        startParking(siteId);  // Use parent class's startParking method
    }
    void showDetails(){
        System.out.println("Name : "+name);
        System.out.println("Contact Number : "+contactNo);
    }
    
    void billingCost(){
        if(currentParking != null && currentParking.getParkOut()!=null){
            ParkingSite selectedSite = Admin.getInstance().returnSiteById(siteId);
            bill =  Math.ceil(currentParking.getDurationInMinutes() / 60.0) * selectedSite.getHourlyRate(); 
        }
        else{
            System.out.println("Bill can't be printed until Vehicle is parked out");
        }
    }

    @Override
    void parkOut() {
        if (currentParking != null) {
            isParked = false;
            currentParking.setParkOut(LocalDateTime.now());
            parkingHistory.add(currentParking);
            currentParking = null;
            System.out.println("Park out recorded. Please proceed to payment.");
        } else {
            System.out.println("No active parking session to end.");
        }
    }
} 