package SmartParkingSystem; 

import java.util.ArrayList;

public class Admin 
{
    // Declaring the data fields as private for better encapsulation 
    private String name = "Naseem Ullah";
    private String userName = "admin123";
    private String password = "123"; 
    private static Admin obj = null;
    private ArrayList<ParkingSite> sites;

    //Constructor declared as private because we want a singleton class only one admin can have access 

    //Default private constructor 
    private Admin()
    {
        sites = new ArrayList<>(); 
    }

    //The public methods for the object creation since constructors are private and overloaded 
    public static Admin getInstance()
    {
        if(obj == null)
        {
            obj = new Admin();
        }
        return obj; 
    }

    /*Since we had a list of ParkingSites with same name so to differentiate and locate them in the list 
     we are checking the location using the site ID */
    public int findSiteIndex(String siteId)
    {
        int index = -1;
        for(int i = 0 ; i < sites.size() ; i++)
        {
            if(sites.get(i).getSiteID().equals(siteId))
            {
                index = i;
                break; 
            }
        }
        return index;
    }

    //To access the controlling methods of sites without directly getting them from the list since 
    //list is declared as private for better encapsulation 
    public ParkingSite returnSiteById(String siteId)
    {
        for(int i = 0 ; i < sites.size() ; i++)
        {
            if(sites.get(i).getSiteID().equals(siteId))
            {
                return sites.get(i); 
            }
        }
        return null;
    }

    //Method to add a new site to the admin sites 
    public void addSite(String siteId , int maxSiteCapacity , String siteLocation , double hourlyRate , boolean isOperational)
    {
        if(findSiteIndex(siteId) == -1) 
        {
            ParkingSite site = new ParkingSite(siteId,maxSiteCapacity,siteLocation,hourlyRate,isOperational); 
            sites.add(site);
        }
        else System.out.println("The site already exists!");  
    }

    //Method to remove a parking site 
    public boolean removeParkingSite(String siteId)
    {
        if(findSiteIndex(siteId) != -1)  {
        sites.remove(findSiteIndex(siteId)); 
        return true;
    }
        else System.out.println("No site found against this id!"); 
        return false; 
    }

    public void displayAllSites() 
    {
        for(int i = 0 ; i < sites.size() ; i++)
        {
            System.out.println("Site ID : "+sites.get(i).getSiteID()+"  Location : "+sites.get(i).getSiteLocation()); 
        }
    }

    public void searchVehiclebyLicensePlate(String licensePlate)
    {
        User user = User.getUserByVehicleNo(licensePlate);
        if(user == null)
        {
            System.out.println("Vehicle not found!");
        }
        else{
        System.out.println("License Plate Number : "+licensePlate);
        System.out.println("Owner Name : "+user.getName());
        System.out.println("Vehicle Type : "+user.getVehicleType());
        if(user instanceof RegularUser)
        {
            System.out.println("User Type : Regular User");
        }
        else if(user instanceof WalkInUser)
        {
            System.out.println("User Type : Walk-In User");
        }
        if(user.isParked)
        {
          System.out.println("User is Parked at Site : "+user.currentParking.getsiteId()+"and the slot number is : "+user.bookedSlot);
          System.out.println("User checked in at : "+user.currentParking.getParkIn());
        } 
        else
        {
            System.out.println("User is not currently parked at any parking site!");
        }
    }
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setUsername(String userName)
    {
        this.userName = userName; 
    }

    public boolean hasNoSites()
    {
        return sites == null || sites.isEmpty();
    }

    //get Method
    public String getName()
    {
        return name; 
    }

    public String getUsername()
    {
        return userName;
    }

    public String getPassword()
    {
        return password; 
    }
}