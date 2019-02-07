package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal;

/**
 * Created by Prince on 14-11-2017.
 */

public class newArrivalModal {
    private String Str_id,Str_item_name;

    public newArrivalModal(String str_id, String str_item_name) {
        Str_id = str_id;
        Str_item_name = str_item_name;
    }

    public String getStr_id() {
        return Str_id;
    }

    public void setStr_id(String str_id) {
        Str_id = str_id;
    }

    public String getStr_item_name() {
        return Str_item_name;
    }

    public void setStr_item_name(String str_item_name) {
        Str_item_name = str_item_name;
    }
}
