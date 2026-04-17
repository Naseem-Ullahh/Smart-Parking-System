package SmartParkingSystem;

public class SalesReport
{
    // Private constructor to prevent instantiation
    private SalesReport() {}

    public static void getRegularUsersSalesReport(String siteId)
    {
        double totalRegularBill = 0;  
        // Reset total before calculation 
        System.out.println("===============================REGULAR USER SALES===============================");
        for (RegularUser user : RegularUser.cnicUserMap.values())
        {
            // Filter by siteId if needed: Check if user has parking history for this site
            boolean hasSiteSession = user.getParkingHistory().stream()
                .anyMatch(record -> record.getsiteId().equals(siteId));

            if (hasSiteSession) {
                System.out.println("User ID : " + user.getUserID() +
                    " | Name : " + user.getName() +
                    " | Vehicle No : " + user.getVehicleNo() +
                    " | Monthly Bill : Rs. " + user.getLastMonthBill());
                totalRegularBill += user.getLastMonthBill();
            }
        }
        System.out.println("Total Regular Users Sale : Rs. " + totalRegularBill);
        System.out.println();
    }

    public static void getWalkinUserSalesReport(String siteId)
    {
        double totalWalkinBill = 0; // Reset total before calculation
        System.out.println("===============================WALK IN USER SALES===============================");
        for (WalkInUser user : WalkInUser.WalkInUserMap.values())
        {
            // Filter by siteId
            if (user.currentParking != null && user.currentParking.getsiteId().equals(siteId))
            {
                // Make sure billing is calculated before printing
                user.billingCost();

                System.out.println(" | Name : " + user.getName() +
                    " | Vehicle No : " + user.getVehiclNo() +
                    " | Contact No : " + user.getContactNo() +
                    " | Check-In : " + user.currentParking.getParkIn() +
                    " | Check-Out : " + user.currentParking.getParkOut() +
                    " | Bill : Rs. " + user.bill);

                totalWalkinBill += user.bill;
            }
        }
        System.out.println("Total Walk-in Users Sale : Rs. " + totalWalkinBill);
        System.out.println();
    }

    public static void getTotalSales(String siteId)
    {
        // Since totals are local now, recalculate and print sum of both
        double totalRegularBill = 0;
        for (RegularUser user : RegularUser.cnicUserMap.values()) {
            boolean hasSiteSession = user.getParkingHistory().stream()
                .anyMatch(record -> record.getsiteId().equals(siteId));
            if (hasSiteSession) {
                totalRegularBill += user.getLastMonthBill();
            }
        }

        double totalWalkinBill = 0;
        for (WalkInUser user : WalkInUser.WalkInUserMap.values()) {
            if (user.currentParking != null && user.currentParking.getsiteId().equals(siteId)) {
                user.billingCost();
                totalWalkinBill += user.bill;
            }
        }

        System.out.println("Total Sales from this site (" + siteId + ") are : Rs. " + (totalRegularBill + totalWalkinBill));
    }
}
