package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model_vendor;

public class BungeeInventBeans {

    private String id,title,startPoint,startPointId,time,timeId,availableSeats,selectedDate;

    public BungeeInventBeans(String id, String title, String startPoint, String startPointId,
                             String time,String timeId, String availableSeats, String selectedDate) {
        this.id = id;
        this.title = title;
        this.startPoint = startPoint;
        this.startPointId = startPointId;
        this.time = time;
        this.availableSeats = availableSeats;
        this.selectedDate = selectedDate;
        this.timeId = timeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getStartPointId() {
        return startPointId;
    }

    public void setStartPointId(String startPointId) {
        this.startPointId = startPointId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public String getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(String availabelSeats) {
        this.availableSeats = availabelSeats;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }
}
