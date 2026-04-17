package SmartParkingSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class ParkingRecord {
    private LocalDateTime parkIn;
    private LocalDateTime parkOut;
    private boolean isPaid = false;
    private String siteId;
    private String paymentMethod = "Unpaid"; 

    public ParkingRecord(LocalDateTime parkIn , String siteId) { 
        this.parkIn = parkIn;
        this.siteId = siteId; 
    }

    public void setParkOut(LocalDateTime parkOut) {
        this.parkOut = parkOut;
    }

    public LocalDateTime getParkIn() {
        return parkIn;
    }

    public LocalDateTime getParkOut() {
        return parkOut;
    }

    public long getDurationInMinutes() {
        if (parkOut != null) {
            return java.time.Duration.between(parkIn, parkOut).toMinutes();
        }
        return 0;
    }

    public void markAsPaid(String method) {
        isPaid = true;
        paymentMethod = method;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getSessionBill() {
        ParkingSite selectedSite = Admin.getInstance().returnSiteById(siteId);
        return Math.ceil(getDurationInMinutes() / 60.0) * selectedSite.getHourlyRate();
    }

    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "Park-In: " + parkIn.format(fmt) +
               ", Park-Out: " + (parkOut != null ? parkOut.format(fmt) : "Still Parked") +
               ", Duration: " + (parkOut != null ? getDurationInMinutes() + " minutes" : "N/A");
    }

    public String getsiteId()
    {
        return siteId;
    }

}
