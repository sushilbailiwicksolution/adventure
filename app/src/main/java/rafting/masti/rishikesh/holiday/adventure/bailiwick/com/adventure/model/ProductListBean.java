package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model;

/**
 * Created by Prince on 15-11-2017.
 */

public class ProductListBean {

    private String Str_package_id, Str_package_name, Str_service_name, Str_service_id,
            Str_short_description, Str_description, Str_country, Str_city, Str_state, Str_price,
            Str_image, Str_status,withMeal;
    private boolean ISNight;

    public ProductListBean(String str_package_id, String str_package_name, String str_service_name,
                           String str_service_id, String str_short_description, String str_description,
                           String str_country, String str_city, String str_state, String str_price,
                           String str_image, String str_status, boolean isNight,String withMeal) {
        Str_package_id = str_package_id;
        Str_package_name = str_package_name;
        Str_service_name = str_service_name;
        Str_service_id = str_service_id;
        Str_short_description = str_short_description;
        Str_description = str_description;
        Str_country = str_country;
        Str_city = str_city;
        Str_state = str_state;
        Str_price = str_price;
        Str_image = str_image;
        Str_status = str_status;
        ISNight = isNight;
        this.withMeal = withMeal;
    }

    public ProductListBean(String str_package_id, String str_package_name, String str_service_name,
                           String str_service_id, String str_short_description, String str_description,
                           String str_country, String str_city, String str_state, String str_price,
                           String str_image, String str_status, boolean isNight) {
        Str_package_id = str_package_id;
        Str_package_name = str_package_name;
        Str_service_name = str_service_name;
        Str_service_id = str_service_id;
        Str_short_description = str_short_description;
        Str_description = str_description;
        Str_country = str_country;
        Str_city = str_city;
        Str_state = str_state;
        Str_price = str_price;
        Str_image = str_image;
        Str_status = str_status;
        ISNight = isNight;
    }

    public String getWithMeal() {
        return withMeal;
    }

    public void setWithMeal(String withMeal) {
        this.withMeal = withMeal;
    }

    public boolean isISNight() {
        return ISNight;
    }

    public void setISNight(boolean ISNight) {
        this.ISNight = ISNight;
    }

    public String getStr_package_id() {
        return Str_package_id;
    }

    public void setStr_package_id(String str_package_id) {
        Str_package_id = str_package_id;
    }

    public String getStr_package_name() {
        return Str_package_name;
    }

    public void setStr_package_name(String str_package_name) {
        Str_package_name = str_package_name;
    }

    public String getStr_service_name() {
        return Str_service_name;
    }

    public void setStr_service_name(String str_service_name) {
        Str_service_name = str_service_name;
    }

    public String getStr_service_id() {
        return Str_service_id;
    }

    public void setStr_service_id(String str_service_id) {
        Str_service_id = str_service_id;
    }

    public String getStr_short_description() {
        return Str_short_description;
    }

    public void setStr_short_description(String str_short_description) {
        Str_short_description = str_short_description;
    }

    public String getStr_description() {
        return Str_description;
    }

    public void setStr_description(String str_description) {
        Str_description = str_description;
    }

    public String getStr_country() {
        return Str_country;
    }

    public void setStr_country(String str_country) {
        Str_country = str_country;
    }

    public String getStr_city() {
        return Str_city;
    }

    public void setStr_city(String str_city) {
        Str_city = str_city;
    }

    public String getStr_state() {
        return Str_state;
    }

    public void setStr_state(String str_state) {
        Str_state = str_state;
    }

    public String getStr_price() {
        return Str_price;
    }

    public void setStr_price(String str_price) {
        Str_price = str_price;
    }

    public String getStr_image() {
        return Str_image;
    }

    public void setStr_image(String str_image) {
        Str_image = str_image;
    }

    public String getStr_status() {
        return Str_status;
    }

    public void setStr_status(String str_status) {
        Str_status = str_status;
    }
}
