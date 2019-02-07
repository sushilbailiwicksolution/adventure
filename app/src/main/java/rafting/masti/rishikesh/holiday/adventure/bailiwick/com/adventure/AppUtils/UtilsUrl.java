package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils;

/**
 * Created by Prince on 28-12-2017.
 */

public class UtilsUrl {
    //    public static final String BASE_URL = "http://bailiwicksolution.com/adventure/apis.php";
   // public static final String BASE_URL = "http://indianadventurepackages.com/adventure_new/apis.php";
   // public static final String IMAGEBASE_URL = "http://indianadventurepackages.com/adventure_new";

    public static final String BASE_URL = "http://indianadventurepackages.com/apis.php";
     public static final String IMAGEBASE_URL = "http://indianadventurepackages.com/";

    public static final String Action_register = "registration";
    public static final String Action_Login = "login";
    public static final String Action_forgetPassword = "forget_password";
    public static final String Action_OTPVerify = "otp_verify";
    public static final String Action_updatePassword = "update_password";


    public static final String Action_userDetail = "getUserDetails";
    public static final String Action_UpdateProfile = "setUserDetails";
    public static final String Action_GETService = "getServices";
    public static final String Action_SubmitQuery = "submitEnquiry";
    public static final String Action_SubmitReview = "submitReview";
    public static final String Action_UpdateProfile_PIC = "upload_image";


    // Vendor list for services
    public static final String Action_VendorList = "getOtherServices";
    // Rafting
    public static final String Action_getRaftingDetail = "getRaftingDetails";
    public static final String Action_getRaftingPoint = "getRaftingPoints";
    public static final String Action_getRaftingSeats = "getRaftingSeats";
    // Camping
    public static final String Action_getCAMPTYPE = "getCampType";
    public static final String Action_getCAMPList = "getCampList";

    public static final String Action_getCAMPDetail = "getCampDetails";
    public static final String Action_getRoomDetail = "getRoomDetails";

    // Camp Booking
    public static final String Action_BOOKING = "booking";
    public static final String Action_CONFIRM_BOOKING = "confirm_booking";

// Vendor Api Listing

    public static final String Action_VendorCampListing = "getVendorCampList";
    public static final String Action_VendorCampDetails = "getVendorCampDetails";
    public static final String Action_VendorCampUpdate = "resetCampingInventory";

    public static final String Action_VendorRaftDetails = "getVendorRaftList";
    public static final String Action_VendorRaftUpdate = "resetRaftingSeat";
    public static final String Action_VendorBooking = "getVendorBookingList";

// Oder Detail

    public static final String Action_orderHistory = "getOrderHistory";
}
