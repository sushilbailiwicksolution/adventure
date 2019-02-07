package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal;

/**
 * Created by Prince on 23-01-2018.
 */

public class CartListModel {

    private String id, tittle, Str_service,
            Str_check_in, Str_check_out, Str_no_nights, Str_adult,
            Str_child, Str_starting_point, Str_booking_date,
            Str__qty, price, total, imageurl, isRafting, str_room_type_id,
            str_name, str_mobile, str_message, str_email;

    public CartListModel(String id, String tittle, String str_service, String str_check_in, String str_check_out, String str_no_nights, String str_adult, String str_child, String str_starting_point, String str_booking_date, String str__qty, String price, String total, String imageurl, String isRafting, String str_room_type_id, String str_name, String str_mobile, String str_message, String str_email) {
        this.id = id;
        this.tittle = tittle;
        Str_service = str_service;
        Str_check_in = str_check_in;
        Str_check_out = str_check_out;
        Str_no_nights = str_no_nights;
        Str_adult = str_adult;
        Str_child = str_child;
        Str_starting_point = str_starting_point;
        Str_booking_date = str_booking_date;
        Str__qty = str__qty;
        this.price = price;
        this.total = total;
        this.imageurl = imageurl;
        this.isRafting = isRafting;
        this.str_room_type_id = str_room_type_id;
        this.str_name = str_name;
        this.str_mobile = str_mobile;
        this.str_message = str_message;
        this.str_email = str_email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getStr_service() {
        return Str_service;
    }

    public void setStr_service(String str_service) {
        Str_service = str_service;
    }

    public String getStr_check_in() {
        return Str_check_in;
    }

    public void setStr_check_in(String str_check_in) {
        Str_check_in = str_check_in;
    }

    public String getStr_check_out() {
        return Str_check_out;
    }

    public void setStr_check_out(String str_check_out) {
        Str_check_out = str_check_out;
    }

    public String getStr_no_nights() {
        return Str_no_nights;
    }

    public void setStr_no_nights(String str_no_nights) {
        Str_no_nights = str_no_nights;
    }

    public String getStr_adult() {
        return Str_adult;
    }

    public void setStr_adult(String str_adult) {
        Str_adult = str_adult;
    }

    public String getStr_child() {
        return Str_child;
    }

    public void setStr_child(String str_child) {
        Str_child = str_child;
    }

    public String getStr_starting_point() {
        return Str_starting_point;
    }

    public void setStr_starting_point(String str_starting_point) {
        Str_starting_point = str_starting_point;
    }

    public String getStr_booking_date() {
        return Str_booking_date;
    }

    public void setStr_booking_date(String str_booking_date) {
        Str_booking_date = str_booking_date;
    }

    public String getStr__qty() {
        return Str__qty;
    }

    public void setStr__qty(String str__qty) {
        Str__qty = str__qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getIsRafting() {
        return isRafting;
    }

    public void setIsRafting(String isRafting) {
        this.isRafting = isRafting;
    }

    public String getStr_room_type_id() {
        return str_room_type_id;
    }

    public void setStr_room_type_id(String str_room_type_id) {
        this.str_room_type_id = str_room_type_id;
    }

    public String getStr_name() {
        return str_name;
    }

    public void setStr_name(String str_name) {
        this.str_name = str_name;
    }

    public String getStr_mobile() {
        return str_mobile;
    }

    public void setStr_mobile(String str_mobile) {
        this.str_mobile = str_mobile;
    }

    public String getStr_message() {
        return str_message;
    }

    public void setStr_message(String str_message) {
        this.str_message = str_message;
    }

    public String getStr_email() {
        return str_email;
    }

    public void setStr_email(String str_email) {
        this.str_email = str_email;
    }
}
