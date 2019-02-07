package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal;

/**
 * Created by Prince on 30-01-2018.
 */

public class RoomTypeModel {
    private String Str_room_type_id,Str_room_type_title,Str_description,Str_single_sell_rate,Str_double_sell_rate;

    public RoomTypeModel(String str_room_type_id, String str_room_type_title, String str_description, String str_single_sell_rate, String str_double_sell_rate) {
        Str_room_type_id = str_room_type_id;
        Str_room_type_title = str_room_type_title;
        Str_description = str_description;
        Str_single_sell_rate = str_single_sell_rate;
        Str_double_sell_rate = str_double_sell_rate;
    }

    public String getStr_room_type_id() {
        return Str_room_type_id;
    }

    public void setStr_room_type_id(String str_room_type_id) {
        Str_room_type_id = str_room_type_id;
    }

    public String getStr_room_type_title() {
        return Str_room_type_title;
    }

    public void setStr_room_type_title(String str_room_type_title) {
        Str_room_type_title = str_room_type_title;
    }

    public String getStr_description() {
        return Str_description;
    }

    public void setStr_description(String str_description) {
        Str_description = str_description;
    }

    public String getStr_single_sell_rate() {
        return Str_single_sell_rate;
    }

    public void setStr_single_sell_rate(String str_single_sell_rate) {
        Str_single_sell_rate = str_single_sell_rate;
    }

    public String getStr_double_sell_rate() {
        return Str_double_sell_rate;
    }

    public void setStr_double_sell_rate(String str_double_sell_rate) {
        Str_double_sell_rate = str_double_sell_rate;
    }
}
