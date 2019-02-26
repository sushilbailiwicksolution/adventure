package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model;

/**
 * Created by Prince on 05-10-2018.
 */

public class Order_Beans {

    private String sports,userName,userEmail,userMobile,userPackage,personCount,vendorName,bookingDate,orderDate,paymentStatus,
            startingPoint,campName,adultCount,childAboveFiveCount,childBelowFiveCount,checkIn,checkOut;


    /*//constructor for rafting
    public Order_Beans(String userName, String userEmail, String userMobile, String userPackage,
                       String personCount, String vendorName, String bookingDate, String orderDate,
                       String paymentStatus, String startingPoint, String sports) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.userPackage = userPackage;
        this.personCount = personCount;
        this.vendorName = vendorName;
        this.bookingDate = bookingDate;
        this.orderDate = orderDate;
        this.paymentStatus = paymentStatus;
        this.startingPoint = startingPoint;
        this.sports = sports;
    }

    //constructor for camping
    public Order_Beans(String userName, String userEmail, String userMobile, String vendorName,
                       String paymentStatus, String campName, String adultCount, String childAboveFiveCount,
                       String childBelowFiveCount, String checkIn, String checkOut, String sports) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.vendorName = vendorName;
        this.paymentStatus = paymentStatus;
        this.campName = campName;
        this.adultCount = adultCount;
        this.childAboveFiveCount = childAboveFiveCount;
        this.childBelowFiveCount = childBelowFiveCount;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.sports = sports;
    }*/

    public Order_Beans(String sports, String userName, String userEmail, String userMobile,
                       String userPackage, String personCount, String vendorName, String bookingDate,
                       String orderDate, String paymentStatus, String startingPoint, String campName,
                       String adultCount, String childAboveFiveCount, String childBelowFiveCount, String checkIn, String checkOut) {
        this.sports = sports;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.userPackage = userPackage;
        this.personCount = personCount;
        this.vendorName = vendorName;
        this.bookingDate = bookingDate;
        this.orderDate = orderDate;
        this.paymentStatus = paymentStatus;
        this.startingPoint = startingPoint;
        this.campName = campName;
        this.adultCount = adultCount;
        this.childAboveFiveCount = childAboveFiveCount;
        this.childBelowFiveCount = childBelowFiveCount;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
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

    public String getUserPackage() {
        return userPackage;
    }

    public void setUserPackage(String userPackage) {
        this.userPackage = userPackage;
    }

    public String getPersonCount() {
        return personCount;
    }

    public void setPersonCount(String personCount) {
        this.personCount = personCount;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(String adultCount) {
        this.adultCount = adultCount;
    }

    public String getChildAboveFiveCount() {
        return childAboveFiveCount;
    }

    public void setChildAboveFiveCount(String childAboveFiveCount) {
        this.childAboveFiveCount = childAboveFiveCount;
    }

    public String getChildBelowFiveCount() {
        return childBelowFiveCount;
    }

    public void setChildBelowFiveCount(String childBelowFiveCount) {
        this.childBelowFiveCount = childBelowFiveCount;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getSports() {
        return sports;
    }

    public void setSports(String sports) {
        this.sports = sports;
    }
}
