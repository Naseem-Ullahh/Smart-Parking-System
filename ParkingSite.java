package SmartParkingSystem; 

public class ParkingSite   
{
    private final String siteId; 
    private int maxSiteCapacity;
    private String siteLocation;   
    private double hourlyRate;
    private boolean isOperational;
    private int availableSlots;  
    private String[] slots;  

    public ParkingSite(String siteId , int maxSiteCapacity , String siteLocation , double hourlyRate , boolean isOperational)
    {
        this.siteId = siteId;
        this.maxSiteCapacity = maxSiteCapacity;
        this.siteLocation = siteLocation;
        this.hourlyRate = hourlyRate;
        this.isOperational = isOperational;
        availableSlots = maxSiteCapacity;
        slots = new String[maxSiteCapacity];

        for(int i = 0 ; i < slots.length ; i++)
        {
            slots[i] = "Available"; 
        }
    }

    public ParkingSite()
    {
        this("XYZ",0,"XYZ",0,false);
    }

    public void showSiteStatus()
    {   
        for(int i = 0 ; i < slots.length ; i++)
        {
            System.out.printf("Slot %02d: %-10s | ", (i + 1), slots[i]); 
            if((i+1)% 5 == 0)  System.out.println(); 
        }
        System.out.println("\n"); 
        System.out.println("Available Slots : "+availableSlots);
        System.out.println("Occupied Slots  : "+(maxSiteCapacity-availableSlots));
        System.out.println("Site is " + (isOperational ? "Operational " : "Not Operational ")); 
    }

    public boolean releaseSlot(String licensePlate)
    { 
        for(int i = 0 ; i < slots.length ; i++)
        {
            if(slots[i].equals(licensePlate))
            {
                User user = User.getUserByVehicleNo(licensePlate);
                user.parkOut();
                slots[i] = "Available";
                availableSlots++;
                return true;
            }
        }
        return false;
    }

    public void getAvailableSlots()
    {
        for(int i = 0 ; i < slots.length ; i++)
        {
            if(slots[i] == "Available")
            {
                System.out.print("Slot : "+(i+1)+" | "); 
            }
        }
    }

    public boolean isSlotAvailable(int slotId) 
    {
        return slotId >= 0 && slotId < slots.length && slots[slotId].equals("Available");
    }
 
    public int bookSlotAt(String licensePlate, String siteId, int slotId) 
    {
        if (!isOperational) 
        {
            return -1;
        }
        User user = User.getUserByVehicleNo(licensePlate);
        if (user.isParked || user.currentParking != null) 
        {
            System.out.println("This vehicle is already parked.");
            return 0;
        }
        if (isSlotAvailable(slotId)) 
        {
            slots[slotId] = licensePlate;
            availableSlots--;
            user.startParking(siteId);
            user.bookedSlot = slotId;
            return slotId + 1;
        }
        return 0;
    }
    //get Methods
    public String getSiteID() 
    {
        return siteId;
    }

    public String getSiteLocation()
    {
        return siteLocation; 
    }

    public int getMaxSiteCapacity()
    {
        return maxSiteCapacity;
    }

    public double getHourlyRate()
    {
        return hourlyRate; 
    }

    public void setLocation(String siteLocation)
   {
      if(siteLocation == null || siteLocation.trim().isEmpty())
      throw new IllegalArgumentException("Site location cannot be null or empty!");
      this.siteLocation = siteLocation; 
   }

    public void setMaxSiteCapacity(int maxSiteCapacity)
   {
      if(maxSiteCapacity <= 0)
      throw new IllegalArgumentException("Max site capacity must be positive!");
      this.maxSiteCapacity = maxSiteCapacity; 
   }  

    public void setHourlyRate(double hourlyRate)
   {
      if(hourlyRate < 0)
      throw new IllegalArgumentException("Hourly rate cannot be negative!");
      this.hourlyRate = hourlyRate;
   }

    public void setOperationalStatus(boolean status)
    {
        isOperational = status; 
    }

}
