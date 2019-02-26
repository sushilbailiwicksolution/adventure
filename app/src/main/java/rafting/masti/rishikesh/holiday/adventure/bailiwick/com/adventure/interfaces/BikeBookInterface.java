package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.interfaces;

public interface BikeBookInterface {

    void incrementClick(int position,String quantity,String price);

    void decrementClick(int position,String quantity,String price);

    void addNewRow(int position);

    void deleteRow(int position);

}
