package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal;

/**
 * Created by Prince on 25-01-2018.
 */

public class CampMasterModel {

    private String Str_id, Str_Camp_type;

    public CampMasterModel(String str_id, String str_Camp_type) {
        Str_id = str_id;
        Str_Camp_type = str_Camp_type;
    }

    public String getStr_id() {
        return Str_id;
    }

    public void setStr_id(String str_id) {
        Str_id = str_id;
    }

    public String getStr_Camp_type() {
        return Str_Camp_type;
    }

    public void setStr_Camp_type(String str_Camp_type) {
        Str_Camp_type = str_Camp_type;
    }
}
