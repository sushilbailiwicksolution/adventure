package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model;

public class EnquiryHistory {

    private String userName,userEmail,userMobile,personCount,packageName,serviceName,vendorName,bookingDate;

    public EnquiryHistory(String userName, String userEmail, String userMobile, String personCount, String packageName, String serviceName,
                          String vendorName, String bookingDate) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.personCount = personCount;
        this.packageName = packageName;
        this.serviceName = serviceName;
        this.vendorName = vendorName;
        this.bookingDate = bookingDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getPersonCount() {
        return personCount;
    }

    public void setPersonCount(String personCount) {
        this.personCount = personCount;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
}
